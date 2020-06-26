package com.example.arvindhan.qr;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arvindhan on 8/8/2017.
 */

public class DataBaseWrapper extends SQLiteOpenHelper {

    private DataBaseWrapper dbHelper1;
    SQLiteDatabase database;

    private static final String DATABASE_NAME = "image.db";
    private static final int DATABASE_VERSION = 1;

    public static final String UploadImages="UploadImage";

    public static final String id1="id";
    public static final String sno="sno";
    public static final String module="module";

    public static final String databaseName = "dbTest";
    public static final String TABLE_IMAGE = "ImageTable";
    public static final String TABLEIMAGE1 = "tableimage1";
    public static final String TABLE_DUEOVERDUE = "dueoverduetable";
    public static final String TABLE_POCUSTOM = "pocustomtable";

    public static final String S_NO = "s_no";
    public static final String IMAGE_ID = "image_id";
    public static final String IMAGE_TEXT = "image_text";

    public static final String S_NO1 = "sno1";
    public static final String IMAGEID = "imageid";
    public static final String IMAGETEXT = "imagetext";

    public static final String S_NO2 = "sno2";
    public static final String MAINTENANCECODE = "maintenancecode";
    public static final String EQUIPMENTCODE = "Equipmentcode";
    public static final String MAINTENANCENAME = "maintenancename";
    public static final String EQUIPMENTNAME = "equipmentname";
    public static final String MAINTENANCEID = "maintenanceid";
    public static final String FREQUENCY = "frequency";
    public static final String NEXTDUEDATE = "nextduedate";

    public static final String S_NO3 = "sno3";
    public static final String POCODE = "pocode";
    public static final String STORECODE = "storecode";
    public static final String RECIEVEDQUANTITY = "recievedquantity";



    public static final String TABLE_CREATE_1 = "create table if not exists  " + UploadImages
            + "("+id1+" integer primary key autoincrement," +module+" TEXT);";

    public static final String CREATE_IMAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IMAGE + "("
            + S_NO + " integer primary key autoincrement ,"
            + IMAGE_ID + " INT,"
            + IMAGE_TEXT + " TEXT )";

    public static final String CREATE_DUEOVERDUE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DUEOVERDUE + "("
            + S_NO2 + " integer primary key autoincrement ,"
            + MAINTENANCECODE + " TEXT,"
            + EQUIPMENTCODE + " TEXT,"
            + MAINTENANCENAME + " TEXT,"
            + EQUIPMENTNAME + " TEXT,"
            + MAINTENANCEID + " TEXT,"
            + FREQUENCY + " TEXT,"
            + NEXTDUEDATE + " TEXT )";

    public static final String CREATE_POCUSTOM_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POCUSTOM + "("
            + S_NO3 + " integer primary key autoincrement ,"
            + POCODE + " TEXT,"
            + STORECODE + " TEXT,"
            + RECIEVEDQUANTITY + " TEXT )";

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_1);
        sqLiteDatabase.execSQL(CREATE_IMAGE_TABLE);
        sqLiteDatabase.execSQL(CREATE_DUEOVERDUE_TABLE);
        sqLiteDatabase.execSQL(CREATE_POCUSTOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
