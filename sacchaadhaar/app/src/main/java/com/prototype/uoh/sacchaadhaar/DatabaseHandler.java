package com.prototype.uoh.sacchaadhaar;

/**
 * Created by kc on 30/3/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashu on 30/3/17.
 */


public class DatabaseHandler extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StudentsDB";

    // Contacts table name
    private static final String TABLE_CONTACTS = "students";
    private  String CREATE_CONTACTS_TABLE="ll";
    // Contacts Table Columns names
    private static final String KEY_SNO = "sno";
    private static final String KEY_ID = "id";
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email2";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//         CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
        //              + KEY_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID + " INTEGER UNIQUE,"+KEY_UID + " TEXT  UNIQUE,"+ KEY_NAME +
        //            " TEXT ,"  + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT )";




        CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID + " INTEGER UNIQUE,"+KEY_UID + " TEXT  UNIQUE,"+ KEY_NAME +
                " TEXT )";




        db.execSQL(CREATE_CONTACTS_TABLE);

        System.out.println(CREATE_CONTACTS_TABLE);


//Toast.makeText((Context) getAllContacts(),CREATE_CONTACTS_TABLE,Toast.LENGTH_LONG).show();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }



    int  addContact(Student contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SNO, contact.getID()); // Contact Name

        values.put(KEY_ID, contact.getSID()); // Contact Name
        values.put(KEY_UID, contact.getUID()); // Contact Phone

        values.put(KEY_NAME, contact.getName()); // Contact Name
        // values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        //  values.put(KEY_EMAIL, contact.getEmail()); // Contact Name

        // Inserting Row
        int k= (int) db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return k;
        // return CREATE_CONTACTS_TABLE;
    }



    Student getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,KEY_UID,
        //            KEY_NAME, KEY_PH_NO,KEY_EMAIL }, KEY_ID + "=?",
        //      new String[] { String.valueOf(id) }, null, null, null, null);

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,KEY_UID,
                        KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null,null);



        if (cursor != null)
            cursor.moveToFirst();

        Student contact = new Student(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }



    public List<Student> getAllContacts() {
        List<Student> contactList = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student contact = new Student();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                //   contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public int updateContact(Student contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Student contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}






