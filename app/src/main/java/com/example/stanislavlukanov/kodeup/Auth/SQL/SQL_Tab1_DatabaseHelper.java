package com.example.stanislavlukanov.kodeup.Auth.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQL_Tab1_DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "notes_db";

    public SQL_Tab1_DatabaseHelper (Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Tab1.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_Tab1.TABLE_NAME);

        onCreate(db);
    }

    public long insertNote(String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQL_Tab1.COLUMN_NOTE, note);

        long id = db.insert(SQL_Tab1.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public SQL_Tab1 getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SQL_Tab1.TABLE_NAME,
                new String[]{SQL_Tab1.COLUMN_ID, SQL_Tab1.COLUMN_NOTE, SQL_Tab1.COLUMN_TIMESTAMP},
                SQL_Tab1.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        SQL_Tab1 note = new SQL_Tab1(
                cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_TIMESTAMP)));

        cursor.close();
        return note;
    }

    public List<SQL_Tab1> getAllNotes() {
        List<SQL_Tab1> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SQL_Tab1.TABLE_NAME + " ORDER BY " +
                SQL_Tab1.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                SQL_Tab1 note = new SQL_Tab1();
                note.setId(cursor.getInt(cursor.getColumnIndex(SQL_Tab1.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(SQL_Tab1.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;

    }

    public int getNotesCount() {
        String countQuery = "SELECT * FROM " + SQL_Tab1.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(SQL_Tab1 note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SQL_Tab1.COLUMN_NOTE, note.getNote());

        return db.update(SQL_Tab1.TABLE_NAME, values, SQL_Tab1.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(SQL_Tab1 note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SQL_Tab1.TABLE_NAME, SQL_Tab1.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}

