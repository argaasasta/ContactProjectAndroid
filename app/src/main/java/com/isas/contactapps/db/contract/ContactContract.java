package com.isas.contactapps.db.contract;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class ContactContract implements Contract {

    private static final String SQL_CREATE_ACTIVITY =
            CREATE_TABLE_IF_NOT_EXISTS + _SPC_ + Contact.TABLE_NAME + _SPC_ + OPENING_PARENTHESIS
                    + _SPC_ + Contact._ID + _SPC_ + INTEGER + _SPC_ + PRIMARY_KEY + COMMA +
                    Contact.COLUMN_NAME_CONTACT_ID + _SPC_ + TEXT + _SPC_ + COMMA +
                    Contact.COLUMN_NAME_CONTACT_FIRST_NAME+ _SPC_ + TEXT + _SPC_ + COMMA +
                    Contact.COLUMN_NAME_CONTACT_LAST_NAME+ _SPC_ + TEXT + _SPC_ + COMMA +
                    Contact.COLUMN_NAME_CONTACT_PICTURE+ _SPC_ + TEXT + _SPC_ + COMMA +
                    Contact.COLUMN_NAME_CONTACT_URL+ _SPC_ + TEXT + _SPC_ + COMMA +
                    Contact.COLUMN_NAME_CONTACT_FAVOURITE + _SPC_ + TEXT + _SPC_
                    // Any other options for the CREATE command
                    + CLOSING_PARENTHESIS;


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contact.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class Contact implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_CONTACT_FIRST_NAME = "contact_first_name";
        public static final String COLUMN_NAME_CONTACT_LAST_NAME = "contact_last_name";
        public static final String COLUMN_NAME_CONTACT_PICTURE = "contact_picture";
        public static final String COLUMN_NAME_CONTACT_FAVOURITE = "contact_favourite";
        public static final String COLUMN_NAME_CONTACT_URL = "contact_url";

    }


    private static final String TAG = ContactContract.class.getSimpleName();


    // program saat pertama kali dijalankan onCreate = membuka koneksi ke
    // database
    @Override
    public void onCreate(SQLiteDatabase db) {

        // membuat tabel jika sebelumnya belum ada
        db.execSQL(SQL_CREATE_ACTIVITY);
    }

    // cek versi database device
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Cursor getListContactFromDB(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            String sql = SELECT_ALL_FROM+
                     _SPC_ + Contact.TABLE_NAME + _SPC_+
                     ORDER_BY + _SPC_ + Contact.COLUMN_NAME_CONTACT_FIRST_NAME;
            cursor = db.rawQuery(sql, null);
            Log.d(TAG, "sql: " + sql + "jumlah cursor: " + cursor.getCount());
            return cursor;


        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return cursor;
        }
    }

    // insert data pada tabel contact
    public void insert(SQLiteDatabase db, String contact_id, String contact_first_name,String contact_last_name,String contact_picture,String contact_url,String contact_fav) {
        ContentValues data = new ContentValues();

        data.put(Contact.COLUMN_NAME_CONTACT_ID, contact_id);
        data.put(Contact.COLUMN_NAME_CONTACT_FIRST_NAME, contact_first_name);
        data.put(Contact.COLUMN_NAME_CONTACT_LAST_NAME, contact_last_name);
        data.put(Contact.COLUMN_NAME_CONTACT_PICTURE, contact_picture);
        data.put(Contact.COLUMN_NAME_CONTACT_URL, contact_url);
        data.put(Contact.COLUMN_NAME_CONTACT_FAVOURITE, contact_fav);

        db.insert(Contact.TABLE_NAME, null, data);// syntaxinsert
    }

    public int deleteAll(SQLiteDatabase db) {

        return db.delete(Contact.TABLE_NAME, null, null);
    }



    // update data pada tabel session
    public boolean update(SQLiteDatabase db, String contact_id,String fav) {
        ContentValues data = new ContentValues();

        data.put(Contact.COLUMN_NAME_CONTACT_ID, contact_id);
        if (fav != null) {
            data.put(Contact.COLUMN_NAME_CONTACT_FAVOURITE, fav);
        }


        return db.update(Contact.TABLE_NAME, data,
                Contact.COLUMN_NAME_CONTACT_ID + EQUAL + "'" + contact_id + "'", null) > 0;// syntax

    }


}
