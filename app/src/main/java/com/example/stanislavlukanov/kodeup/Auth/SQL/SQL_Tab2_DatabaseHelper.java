package com.example.stanislavlukanov.kodeup.Auth.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQL_Tab2_DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "catalog.db";

    public SQL_Tab2_DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Tab2.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_Tab2.TABLE_NAME);
    }

    public long insertCatalog(String contact, String description, String mail, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Tab2.COLUMN_CONTACT, contact);
        values.put(SQL_Tab2.COLUMN_DESCRIPTION, description);
        values.put(SQL_Tab2.COLUMN_MAIL, mail);
        values.put(SQL_Tab2.COLUMN_PHONE, phone);

        long id = db.insert(SQL_Tab2.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public SQL_Tab2 getCatalog(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SQL_Tab2.TABLE_NAME,
                new String[]{SQL_Tab2.COLUMN_ID, SQL_Tab2.COLUMN_CONTACT, SQL_Tab2.COLUMN_DESCRIPTION, SQL_Tab2.COLUMN_MAIL,
                SQL_Tab2.COLUMN_PHONE},
                SQL_Tab2.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        SQL_Tab2 contact = new SQL_Tab2(
                cursor.getInt(cursor.getColumnIndex(SQL_Tab2.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_CONTACT)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_MAIL)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_PHONE)));

        cursor.close();

        return contact;
    }

    public List<SQL_Tab2> getAllContacts() {
        List<SQL_Tab2> catalogs = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SQL_Tab2.TABLE_NAME + " ORDER BY " +
                SQL_Tab2.COLUMN_CONTACT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SQL_Tab2 contact = new SQL_Tab2();

                contact.setId(cursor.getInt(cursor.getColumnIndex(SQL_Tab2.COLUMN_ID)));
                contact.setContact(cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_CONTACT)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_DESCRIPTION)));
                contact.setMail(cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_MAIL)));
                contact.setPhone(cursor.getString(cursor.getColumnIndex(SQL_Tab2.COLUMN_PHONE)));

                catalogs.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();

        return catalogs;
    }

    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + SQL_Tab2.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateContact(SQL_Tab2 contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Tab2.COLUMN_CONTACT, contact.getContact());
        values.put(SQL_Tab2.COLUMN_DESCRIPTION, contact.getDescription());
        values.put(SQL_Tab2.COLUMN_MAIL, contact.getMail());
        values.put(SQL_Tab2.COLUMN_PHONE, contact.getPhone());

        return db.update(SQL_Tab2.TABLE_NAME, values, SQL_Tab2.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(SQL_Tab2 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SQL_Tab2.TABLE_NAME, SQL_Tab2.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
}
