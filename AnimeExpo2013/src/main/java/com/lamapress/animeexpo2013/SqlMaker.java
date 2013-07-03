package com.lamapress.animeexpo2013;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Anthony on 6/25/13.
 */
public class SqlMaker extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "panelSchedule";
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title"; //0
    public static final String COLUMN_DAY = "day";    // 1
    public static final String COLUMN_TIME_BEGIN = "begin"; // 2
    public static final String COLUMN_TIME_END = "end"; // 3
    public static final String COLUMN_LOCATION = "location"; // 4

    public SqlMaker(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static final String DATABASE_CREATE = "create table " +
            TABLE_SCHEDULE
            + "("
            + COLUMN_ID +" integer primary key autoincrement,"
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DAY + " integer, "
            + COLUMN_TIME_BEGIN + " text,"
            + COLUMN_TIME_END + " text, "
            + COLUMN_LOCATION + " text"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(database);
    }

    public void addContent( final Panels id){
        final SQLiteDatabase db = this.getWritableDatabase();


        new Thread(new Runnable(){
            public void run(){

                ContentValues values = new ContentValues();
                values.put(COLUMN_TITLE, id.title);
                values.put(COLUMN_DAY,id.day);
                values.put(COLUMN_TIME_BEGIN,Long.toString(id.begin.getTimeInMillis()));
                values.put(COLUMN_TIME_END,Long.toString(id.end.getTimeInMillis()));
                values.put(COLUMN_LOCATION,id.location);

                db.insert(TABLE_SCHEDULE,null,values);
                db.close();
            }
        }).start();

    }



    public void removeContent(String column, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE,column + "=\'" + item + '\'',null);
    }

    public boolean findContent(String column,String search){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int id;
        String findQuery = "SELECT " + COLUMN_ID +  " FROM " + TABLE_SCHEDULE + " WHERE "
                + column + " =  \'" + search.replaceAll("'","''") + "\'";
        try{
            cursor = db.rawQuery(findQuery,null);
            cursor.moveToFirst();
            id = Integer.parseInt(cursor.getString(0));
        }
        catch(CursorIndexOutOfBoundsException e)
        {
            e.printStackTrace();
            return false;
        }
            cursor.close();
        return id >= 0;
    }

    public List<Panels> getContent(){

        final List<Panels> panelsList = new ArrayList<Panels>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;

                SQLiteDatabase db = getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                    do{
                        Panels panel = new Panels();
                        panel.title = cursor.getString(1);
                        panel.day = Integer.parseInt(cursor.getString(2));
                        panel.begin.setTimeInMillis(Long.parseLong(cursor.getString(3)));
                        panel.end.setTimeInMillis(Long.parseLong(cursor.getString(4)));
                        panel.location = cursor.getString(5);
                        panelsList.add(panel);
                    } while(cursor.moveToNext());
                }

                cursor.close();
            }
        }).start();

        return panelsList;
    }

    public List<Panels> getAfterContent(){
        final List<Panels> panelsList = new ArrayList<Panels>();

                String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;

                SQLiteDatabase db = getWritableDatabase();
                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                    do{
                        Panels panel = new Panels();
                        panel.title = cursor.getString(1);
                        panel.day = Integer.parseInt(cursor.getString(2));
                        panel.begin.setTimeInMillis(Long.parseLong(cursor.getString(3)));
                        panel.end.setTimeInMillis(Long.parseLong(cursor.getString(4)));
                        panel.location = cursor.getString(5);
                        panelsList.add(panel);
                    } while(cursor.moveToNext());

                cursor.close();
                }

        return panelsList;
    }
}
