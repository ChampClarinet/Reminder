package com.example.clarinetmaster.reminder.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clarinetmaster.reminder.Databases.DatabaseHelper;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by computer on 20/11/2559.
 */

public class EventList {

    private static Context mContext;

    private static DatabaseHelper mHelper;
    private static SQLiteDatabase mDb;

    private static ArrayList<Event> mEventList = new ArrayList<>();

    private static EventList mInstance;

    public static EventList getInstance(Context context){
        if( mInstance == null )
            mInstance = new EventList(context);
        loadFromDatabase();
        return mInstance;
    }

    private EventList(Context context){
        this.mContext = context;
    }

    public static void loadFromDatabase(){
        if(mEventList == null) mEventList = new ArrayList<>();
        else mEventList.clear();

        mHelper = new DatabaseHelper(mContext);
        mDb = mHelper.getWritableDatabase();

        Cursor cursor = mDb.query(mHelper.TABLE_NAME, null, null, null, null, null, mHelper.COL_DATE);

        while( cursor.moveToNext() ){
            int id = cursor.getInt(cursor.getColumnIndex(mHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(mHelper.COL_TITLE));
            String detail = cursor.getString(cursor.getColumnIndex(mHelper.COL_DETAIL));
            Long dateLong = cursor.getLong(cursor.getColumnIndex(mHelper.COL_DATE));
            Calendar datetime = Calendar.getInstance();
            datetime.setTimeInMillis(dateLong);

            Event event = new Event(id, title, detail, datetime);

            mEventList.add(event);
        }
        sortList();
        cursor.close();
    }

    private static void sortList(){
        ArrayList<Event> sortArray = new ArrayList<>();
        CardsColour c = CardsColour.getInstance();
        for(Event e: mEventList){
            if(c.getColour(e.getDate()) == R.color.codeRed) sortArray.add(e);
        }
        for(Event e: mEventList){
            if(c.getColour(e.getDate()) == R.color.codeOrange) sortArray.add(e);
        }
        for(Event e: mEventList){
            if(c.getColour(e.getDate()) == R.color.codeYellow) sortArray.add(e);
        }
        for(Event e: mEventList){
            if(c.getColour(e.getDate()) == R.color.codeGreen) sortArray.add(e);
        }
        for(Event e: mEventList){
            if(c.getColour(e.getDate()) == R.color.codeGrey) sortArray.add(e);
        }
        mEventList = sortArray;
    }

    public static ArrayList<Event> getEventList() {
        return mEventList;
    }

    public static void insertData(Event event){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_DATE, event.getDate().getTimeInMillis());
        cv.put(DatabaseHelper.COL_DETAIL, event.getDetial());
        cv.put(DatabaseHelper.COL_TITLE, event.getTitle());
        mDb.insert(DatabaseHelper.TABLE_NAME, null, cv);
        loadFromDatabase();
    }

    public static void updateData(int id, Event event){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_DATE, event.getDate().getTimeInMillis());
        cv.put(DatabaseHelper.COL_DETAIL, event.getDetial());
        cv.put(DatabaseHelper.COL_TITLE, event.getTitle());
        mDb.insert(DatabaseHelper.TABLE_NAME, null, cv);
        mDb.update(DatabaseHelper.TABLE_NAME, cv, DatabaseHelper.COL_ID + " = " + id, null);
        loadFromDatabase();
    }

    public static void deleteData(int id){
        mDb.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_ID + " = " + id, null);
        loadFromDatabase();
    }

}
