package com.example.arvindhan.qr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Arvindhan on 8/8/2017.
 */

public class sqlconnect {
    private static DataBaseWrapper dbHelper;
    static SQLiteDatabase database;

    public sqlconnect(Context context) {

        dbHelper=new DataBaseWrapper(context);

    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();

    }
    public void close() {

        dbHelper.close();
    }

    public void insertDeviceName(String module){
        try
        {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("module",module);
            database.insert(DataBaseWrapper.UploadImages, null, values);
            database.close();
        }
        catch(Exception e){

        }
    }

    public void insertDueList(String maintenancecode,String Componentcode,String maintenancename,String equipmentname,String maintenanceid,String frequency,String nextduedate){
        try
        {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("MAINTENANCECODE",maintenancecode);
            values.put("EQUIPMENTCODE",Componentcode);
            values.put("MAINTENANCENAME",maintenancename);
            values.put("EQUIPMENTNAME",equipmentname);
            values.put("MAINTENANCEID",maintenanceid);
            values.put("FREQUENCY",frequency);
            values.put("NEXTDUEDATE",nextduedate);

            database.insert(DataBaseWrapper.TABLE_DUEOVERDUE, null, values);
            database.close();
        }
        catch(Exception e){

        }
    }

    public void insertPORecievedqty(String Storecode,String Recievedqty,String Pocode){
        try
        {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("POCODE",Pocode);
            values.put("STORECODE",Storecode);
            values.put("RECIEVEDQUANTITY",Recievedqty);

            database.insert(DataBaseWrapper.TABLE_POCUSTOM, null, values);
            database.close();
        }
        catch(Exception e){

        }
    }

    public void UpdatePORecievedqty(String Storecode,String Recievedqty,String Pocode){
        @SuppressWarnings("unused")
        String g = null;
        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String Sql2 = "Update " + dbHelper.TABLE_POCUSTOM + " set recievedquantity='" + Recievedqty + "' where storecode='" +Storecode + "'";
        database.execSQL(Sql2);

    }

    public ArrayList<String> Getid()
    {
        ArrayList<String> l1=new ArrayList<String>();
        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String sql = "select module from " + dbHelper.UploadImages;
        Cursor c = database.rawQuery(sql, new String[0]);
        if (c.moveToFirst()) {

            do {

                l1.add(c.getString(0));

            } while (c.moveToNext());
        }
        c.close();
        return l1;
    }
    public ArrayList<String> Getids()
    {
        ArrayList<String> lnew=new ArrayList<String>();
        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String sql = "select module from " + dbHelper.UploadImages;
        Cursor c = database.rawQuery(sql, new String[0]);
        if (c.moveToFirst()) {

            do {

                lnew.add(c.getString(0));

            } while (c.moveToNext());
        }
        c.close();
        return lnew;
    }

    public String getImageId() {
        database = dbHelper.getWritableDatabase();

        String s="select count(*) from " + dbHelper.TABLE_IMAGE;
        Cursor cursor2 = database.rawQuery(s,null);
//        ImageHelper imageHelper = new ImageHelper();
        String value=null;

        if (cursor2.moveToFirst()) {
            do {
//                imageHelper.setImageId(cursor2.getString(0));
                value=cursor2.getString(0);
//                imageHelper.setImageByteArray(cursor2.getBlob(0));
            } while (cursor2.moveToNext());
        }

        cursor2.close();
        database.close();
        return value;
    }

    public String Getrecqty(String Storecode)
    {

        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String sql = "select recievedquantity from " + dbHelper.TABLE_POCUSTOM +" WHERE storecode = '" + Storecode + "'";
        Cursor cursor2 = database.rawQuery(sql,null);
//        ImageHelper imageHelper = new ImageHelper();
        String value=null;

        if (cursor2.moveToFirst()) {
            do {
//                imageHelper.setImageId(cursor2.getString(0));
                value=cursor2.getString(0);
//                imageHelper.setImageByteArray(cursor2.getBlob(0));
            } while (cursor2.moveToNext());
        }

        cursor2.close();
        database.close();
        return value;
    }

    public ArrayList<String> Getstrrecqty(String Pocode)
    {
        ArrayList<String> Storecode = new ArrayList<String>();
        ArrayList<String> Recievedqty = new ArrayList<String>();
        ArrayList<String> Result = new ArrayList<String>();

        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String sql = "select storecode,recievedquantity from " + dbHelper.TABLE_POCUSTOM+" WHERE pocode = '" + Pocode + "'";;
        Cursor cursor2 = database.rawQuery(sql,null);
//        ImageHelper imageHelper = new ImageHelper();
        String value=null;

        if (cursor2.moveToFirst()) {
            do {

                Result.add(cursor2.getString(0)+'~'+cursor2.getString(1));

            } while (cursor2.moveToNext());
        }

        cursor2.close();
        database.close();
        return Result;
    }

    public void DeletePO()
    {
        database = dbHelper.getWritableDatabase();
        @SuppressWarnings("static-access")
        String Sql1="Delete from "+ dbHelper.TABLE_POCUSTOM;
        database.execSQL(Sql1);
    }
}
