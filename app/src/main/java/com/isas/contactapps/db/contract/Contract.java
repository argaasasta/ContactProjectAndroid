package com.isas.contactapps.db.contract;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by harits on 28/10/2015.
 */
public interface Contract extends SqliteSyntax{

    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

}
