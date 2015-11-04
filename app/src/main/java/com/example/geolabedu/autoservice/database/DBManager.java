package com.example.geolabedu.autoservice.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geolabedu.autoservice.FirstActivity;

import java.util.ArrayList;

/**
 * Created by GeoLab on 10/22/15.
 */
public class DBManager {

    static ArrayList<String> arrayList, list;

    public static ArrayList testarray() {
        arrayList = new ArrayList();
        arrayList.add("მწარმოებელი");
        arrayList.add("ACURA");
        arrayList.add("AUDI");
        arrayList.add("BMW");
        arrayList.add("MERCEDES-BENZ");

        return arrayList;
    }

    public static ArrayList testmodelfill() {
        list = new ArrayList<>();
        list.add("CL");
        list.add("CSX");
        list.add("null");
        list.add("A4");
        list.add("R8");
        list.add("Q7");
        list.add("null");
        list.add("M3");
        list.add("M4");
        list.add("M6");
        list.add("null");
        list.add("A140");
        list.add("SLR");

        return list;
    }

    public static void insertdata() {
        ContentValues values = new ContentValues();
        testarray();
        testmodelfill();
        long id;
        int counter = 0;
        if (selectdata().isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                values.put(VehiclContracts.VEHICLE_BUILDER, arrayList.get(i));
                id = FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_BUILDER_TABLE, null, values);

                while ( true ) {
                    if (  list.size()> counter && list.get(counter) != "null") {
                        ContentValues values1 = new ContentValues();
                        values1.put(VehiclContracts.VEHICLE_MODEL_PARENT_ID, id);
                        values1.put(VehiclContracts.VEHICLE_BASIC_MODEL, list.get(counter));
                        FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_MODEL_TABLE, null, values1);
                        counter++;
                    }else {
                        counter++;
                        break;
                    }
                }
            }
        }
    }


    public static ArrayList selectdata() {
        ArrayList list = new ArrayList();
        Cursor cursor = FirstActivity.sqLiteDatabase.query(VehiclContracts.VEHICLE_BUILDER_TABLE, null, null, null, null, null, null);


        if (cursor.moveToFirst()) {
            do {

                String categ = cursor.getString(cursor.getColumnIndex(VehiclContracts.VEHICLE_BUILDER));
                list.add(categ);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public static ArrayList selectdatamodel(int id) {
        ArrayList list = new ArrayList();

        Cursor c = FirstActivity.sqLiteDatabase.query(VehiclContracts.VEHICLE_MODEL_TABLE, null, VehiclContracts.VEHICLE_MODEL_PARENT_ID + " =" + id, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String modeli=c.getString(c.getColumnIndex(VehiclContracts.VEHICLE_BASIC_MODEL));
                list.add(modeli);
            } while (c.moveToNext());
        }

        return list;
    }

    public static void delete(int id){
        SQLiteDatabase db=FirstActivity.sqLiteDatabase;
        db.delete(VehiclContracts.VEHICLE_TABLE_NAME,VehiclContracts.VEHICLE_ID + " =" +id,null);

    }


}
