package com.example.stanislavlukanov.kodeup.Auth.SQL;

public class SQL_Tab2 {

    public static final String TABLE_NAME = "catalog";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PHONE = "phone";


    private int id;
    private String contact;
    private String description;
    private String mail;
    private String phone;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CONTACT + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_MAIL + " MAIL,"
                    + COLUMN_PHONE + " NUMBER"
                    + ")";

    public SQL_Tab2() {

    }
    public SQL_Tab2(int id, String contact, String description, String mail, String phone) {
        this.id = id;
        this.contact = contact;
        this.description = description;
        this.mail = mail;
        this.phone = phone;
    }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

}



