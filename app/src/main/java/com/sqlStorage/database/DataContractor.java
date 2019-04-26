package com.sqlStorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataContractor {
    private static DataContractor instance;
    private final String TAG = "flow";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;


    private DataContractor(Context context) {
        databaseHelper = new DatabaseHelper(context, SqlConstants.DATABASE_NAME, null, SqlConstants.DATABASE_VERSION);
        openDatabase();
    }

    public static void inItDatabase(Context context) {

        if (instance == null) {
            instance = new DataContractor(context);
        }
    }

    public static DataContractor getInstance() {
        return instance;
    }

    private void openDatabase() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    /*insert the record in the sqLite database*/
    public boolean insertUserInput(String fName, String lName, String Email, String password, String phoneNumber, String confirmPassword) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put(COL_1, fName);
        contentValues.put(SqlConstants.COL_2, fName);
        contentValues.put(SqlConstants.COL_3, lName);
        contentValues.put(SqlConstants.COL_4, Email);
        contentValues.put(SqlConstants.COL_5, password);
        contentValues.put(SqlConstants.COL_6, phoneNumber);
        contentValues.put(SqlConstants.COL_7, confirmPassword);
        long result = sqLiteDatabase.insertOrThrow(SqlConstants.TABLE_NAME, null, contentValues);
        if (result != -1) {

            Log.i(TAG, "insertUserInput: " + result);
            return true;
        } else {
            return false;
        }
    }

    /*for updating the data in the database */
    public boolean updateTableData(String formalFName, String formalLName, String formalEmail, String formalPassword, String formalPhoneNumber, String formalConfirmPassword) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put(COL_1, fName);
        contentValues.put(SqlConstants.COL_2, formalFName);
        contentValues.put(SqlConstants.COL_3, formalLName);
        contentValues.put(SqlConstants.COL_4, formalEmail);
        contentValues.put(SqlConstants.COL_5, formalPassword);
        contentValues.put(SqlConstants.COL_6, formalPhoneNumber);
        contentValues.put(SqlConstants.COL_7, formalConfirmPassword);
        long updatedRowNumber = sqLiteDatabase.update(SqlConstants.TABLE_NAME, contentValues, null, null);
        Log.i(TAG, "updateTableData: " + updatedRowNumber);
        return updatedRowNumber > 0;

    }

    /*for deleting the entire detail of particular person*/
    public void deleteRecordFromDatabase() {
        long deletedNumberRows = sqLiteDatabase.delete(SqlConstants.TABLE_NAME, null, null);
        if (deletedNumberRows > 0) {
            Log.i(TAG, "deleteRecordFromDatabase: " + deletedNumberRows);
        }
    }
    /*for selecting the data from the database*/

    public Cursor getValueFromTheDatabase(String email, String password) {
        return sqLiteDatabase.rawQuery("select * from student where Email='" + "" + email + "'" + "And password='" + password + "'", null);
    }


}
