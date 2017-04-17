package com.example.sangita.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sangita on 2017-04-12.
 */

public class TempDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "TempDatabaseHelper";
    private static final int VERSION_NUM = 1;

    public static final String DATABASE_NAME = "Temperature.db";
    public static final String TABLE_NAME = "TempSchedule";
    protected static final String KEY_ID = "KEY_ID";
    protected static final String DATE_TIME = "Date_Time";
    protected static final String TEMPERATURE = "Temperature";

    public static String CREATE_TEMPERATURE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DATE_TIME + " TEXT NOT NULL," + TEMPERATURE + " TEXT NOT NULL)";

    public TempDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        db.execSQL(CREATE_TEMPERATURE_TABLE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion+ " newVersion=" + newVersion);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

}