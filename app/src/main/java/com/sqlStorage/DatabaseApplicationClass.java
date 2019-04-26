package com.sqlStorage;

import android.app.Application;

import com.sqlStorage.database.DataContractor;

public  class DatabaseApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataContractor.inItDatabase(this);
    }

}
