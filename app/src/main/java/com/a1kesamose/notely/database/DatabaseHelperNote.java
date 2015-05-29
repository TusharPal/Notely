package com.a1kesamose.notely.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperNote extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "note.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "note";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public static final String DATABASE_CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "("
                                                                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                                + COLUMN_TITLE + " TEXT NOT NULL, "
                                                                + COLUMN_CONTENT + " TEXT NOT NULL, "
                                                                + COLUMN_TIMESTAMP + " TEXT NOT NULL);";

    public DatabaseHelperNote(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(DATABASE_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
