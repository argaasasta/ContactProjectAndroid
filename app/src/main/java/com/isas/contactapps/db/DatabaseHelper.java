package com.isas.contactapps.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.isas.contactapps.R;
import com.isas.contactapps.db.contract.Contract;
import com.isas.contactapps.db.contract.Contracts;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Contracts mContracts;
    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context, Contracts contracts) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext(), contracts);
        }
        return sInstance;
    }


    private DatabaseHelper(Context context, Contracts contracts) {
        super(context, context.getString(R.string.database_name), null,
                context.getResources().getInteger(R.integer.database_version));
        mContracts = contracts;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Contract contract : mContracts) {
            contract.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Contract contract : mContracts) {
            contract.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
