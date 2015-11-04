package com.example.geolabedu.autoservice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GeoLabOwl on 04.08.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Caucasus_Auto_ServiceDB";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    private static final String CREATE_VEHICLE_TABLE =
            "CREATE TABLE " + VehiclContracts.VEHICLE_TABLE_NAME + "("
                    + VehiclContracts.VEHICLE_ID + " integer primary key autoincrement,"
                    + VehiclContracts.VEHICLE_PERSON_EMAIL + " text not null, "
                    + VehiclContracts.VEHICLE_PERSON_PHONE + " text not null, "
                    + VehiclContracts.VEHICLE_MAIN_IMAGE + " text not null, "
                    + VehiclContracts.VEHICLE_CATEGORY + " text not null, "
                    + VehiclContracts.VEHICLE_MODEL + " text not null, "
                    + VehiclContracts.VEHICLE_AGE + " text not null, "
                    + VehiclContracts.VEHICLE_DESCRIPTION + " text not null, "
                    + VehiclContracts.VEHICLE_DATE_ADD + " text not null ); ";


    private static final String CREATE_VEHICLE_IMAGE =
            "CREATE TABLE " + VehiclContracts.VEHICLE_IMAGE_TABLE + "("
                    + VehiclContracts.VEHICLE_IMAGE_ID + " integer primary key autoincrement,"
                    + VehiclContracts.VEHICLE_PARENT_ID + " integer, "
                    + VehiclContracts.VEHICLE_IMAGE + " text not null, "
                    + " FOREIGN KEY(" + VehiclContracts.VEHICLE_PARENT_ID + ") REFERENCES " + VehiclContracts.VEHICLE_TABLE_NAME
                    + "(" + VehiclContracts.VEHICLE_ID + "));";

    private static final String CREATE_BUILDER_TABLE =
            "CREATE TABLE " + VehiclContracts.VEHICLE_BUILDER_TABLE + "("
                    + VehiclContracts.VEHICLE_BUILDER_ID + " integer primary key autoincrement,"
                    + VehiclContracts.VEHICLE_BUILDER + " text not null );";

    private static final String CREATE_MODEL_TABLE =
            "CREATE TABLE " + VehiclContracts.VEHICLE_MODEL_TABLE + "("
                    + VehiclContracts.VEHICLE_MODELI_ID + " integer primary key autoincrement,"
                    + VehiclContracts.VEHICLE_MODEL_PARENT_ID + " integer,"
                    + VehiclContracts.VEHICLE_BASIC_MODEL + " text not null,"
                    + " FOREIGN KEY(" + VehiclContracts.VEHICLE_MODEL_PARENT_ID + ") REFERENCES " + VehiclContracts.VEHICLE_BUILDER_TABLE
                    + "(" + VehiclContracts.VEHICLE_BUILDER_ID + "));";

    /*
    sheiqmneba sheni teiblebi es xdeba ertxel tu table damateba mogvinda database_version shecvlac gviwevs
    */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_VEHICLE_TABLE);
        sqLiteDatabase.execSQL(CREATE_VEHICLE_IMAGE);
        sqLiteDatabase.execSQL(CREATE_BUILDER_TABLE);
        sqLiteDatabase.execSQL(CREATE_MODEL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
