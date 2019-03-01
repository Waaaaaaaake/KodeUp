package com.example.stanislavlukanov.kodeup.Auth.SQL;

public class SQL_Tab1 {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String note;
    private String timestamp;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";

    public SQL_Tab1() {

    }
    public SQL_Tab1(int id, String note, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }

    public String getNote() { return note; }

    public String getTimestamp() { return timestamp; }

    public void setId(int id) { this.id = id; }

    public void setNote(String note) { this.note = note; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
