package com.example.clarinetmaster.reminder.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by computer on 20/11/2559.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "event.db";

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "event";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DETAIL = "detail";
    public static final String COL_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT, "
                + COL_DETAIL + " TEXT, "
                + COL_DATE + " INTEGER"
                + " )";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
