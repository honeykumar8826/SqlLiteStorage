package com.sqlStorage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String COL_2 = "FirstNAME Text,";
        String COL_3 = "LastName Text,";
        String COL_4 = "Email varchar,";
        String COL_5 = "Password varchar,";
        String COL_6 = "PhoneNumber varchar,";
        String COL_7 = "ConfirmPassword varchar";
        // Log.i(TAG, "onCreate: ");
        db.execSQL(SqlConstants.CREATE_TABLE + SqlConstants.TABLE_NAME + "(" + COL_2 + COL_3 + COL_4 + COL_5 + COL_6 + COL_7 + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
