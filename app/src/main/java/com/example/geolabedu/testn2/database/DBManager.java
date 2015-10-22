package com.example.geolabedu.testn2.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.geolabedu.testn2.FirstActivity;

import java.util.ArrayList;

/**
 * Created by GeoLab on 10/22/15.
 */
public class DBManager {

    static ArrayList<String> arrayList;

    public static ArrayList testarray() {
        arrayList = new ArrayList();
        arrayList.add("მწარმოებელი'");
        arrayList.add("ACURA");
        arrayList.add("AUDI");
        arrayList.add("BMW");
        arrayList.add("MERCEDES-BENZ");

        return arrayList;
    }

    public static void insertdata(){
        ContentValues values=new ContentValues();
        testarray();
        if (!selectdata().isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                values.put(VehiclContracts.VEHICLE_BUILDER, arrayList.get(i));
                FirstActivity.sqLiteDatabase.insert(VehiclContracts.VEHICLE_BUILDER_TABLE, null, values);
            }
        }
    }

    public static ArrayList selectdata(){
        ArrayList list=new ArrayList();
        Cursor cursor=FirstActivity.sqLiteDatabase.query(VehiclContracts.VEHICLE_BUILDER_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {

                String modeli=cursor.getString(cursor.getColumnIndex(VehiclContracts.VEHICLE_BUILDER));
                list.add(modeli);

            }while (cursor.moveToNext());
        }
        return list;
    }

}
