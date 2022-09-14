package com.example.loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {
    private static final int VERSION_NUMBER = 1;
    private static final String DB_NAME = "login_app";
    private static final String TABLE_NAME = "login";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ADDRESS = "address";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private static final String ID = "id";
    public DBConnect(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERNAME + " TEXT UNIQUE," + PASSWORD + " TEXT," + FIRST_NAME + " TEXT," + LAST_NAME + " TEXT," + ADDRESS + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        onCreate(db);
    }

    public User findUserByUserName (String username) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[] {ID, USERNAME, PASSWORD, ADDRESS, FIRST_NAME, LAST_NAME}, USERNAME + "=? ", new String[]{username}, null, null, null);

        User user;
        if(cursor != null) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setAddress(cursor.getString(5));

            return user;
        }
        return null;
    }

    public void registerUser (User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, user.getUsername());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(FIRST_NAME, user.getFirstName());
        contentValues.put(LAST_NAME, user.getLastName());
        contentValues.put(ADDRESS, user.getAddress());

        long status = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        System.out.println(status);
    }
}
