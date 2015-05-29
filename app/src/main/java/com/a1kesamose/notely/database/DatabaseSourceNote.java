package com.a1kesamose.notely.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.a1kesamose.notely.objects.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSourceNote
{
    private DatabaseHelperNote databaseHelperNote;
    private SQLiteDatabase sqLiteDatabase;
    private final String allColumns[] = {
                                            DatabaseHelperNote.COLUMN_ID,
                                            DatabaseHelperNote.COLUMN_TITLE,
                                            DatabaseHelperNote.COLUMN_CONTENT,
                                            DatabaseHelperNote.COLUMN_TIMESTAMP
                                        };
    public DatabaseSourceNote(Context context)
    {
        databaseHelperNote = new DatabaseHelperNote(context, DatabaseHelperNote.DATABASE_NAME, null, DatabaseHelperNote.DATABASE_VERSION);
    }

    public void open()
    {
        sqLiteDatabase = databaseHelperNote.getWritableDatabase();
    }

    public void close()
    {
        sqLiteDatabase.close();
        databaseHelperNote.close();
    }

    public void createNote(Note note)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperNote.COLUMN_TITLE, note.NOTE_TITLE);
        values.put(DatabaseHelperNote.COLUMN_CONTENT, note.NOTE_CONTENT);
        values.put(DatabaseHelperNote.COLUMN_TIMESTAMP, note.NOTE_TIMESTAMP);

        sqLiteDatabase.insert(DatabaseHelperNote.TABLE_NAME, null, values);
    }

    public void editNote(Note newNote)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelperNote.COLUMN_TITLE, newNote.NOTE_TITLE);
        values.put(DatabaseHelperNote.COLUMN_CONTENT, newNote.NOTE_CONTENT);
        values.put(DatabaseHelperNote.COLUMN_TIMESTAMP, newNote.NOTE_TIMESTAMP);

        sqLiteDatabase.update(DatabaseHelperNote.TABLE_NAME, values, DatabaseHelperNote.COLUMN_ID + " = " + newNote.NOTE_ID, null);
    }

    public void deleteNote(long id)
    {
        sqLiteDatabase.delete(DatabaseHelperNote.TABLE_NAME, DatabaseHelperNote.COLUMN_ID + " = " + id, null);
    }

    public Note getNote(long id)
    {
        Cursor cursor = sqLiteDatabase.query(DatabaseHelperNote.TABLE_NAME, allColumns, DatabaseHelperNote.COLUMN_ID + " = " + id, null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            Note note = cursorToNote(cursor);
            cursor.close();

            return note;
        }
        else
        {
            cursor.close();

            return new Note();
        }
    }

    public List<Note> getNoteList()
    {
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelperNote.TABLE_NAME, allColumns, null, null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                list.add(cursorToNote(cursor));
                cursor.moveToNext();
            }
            cursor.close();

            return list;
        }
        else
        {
            cursor.close();

            return list;
        }
    }

    private Note cursorToNote(Cursor cursor)
    {
        Note note = new Note();
        note.NOTE_ID = cursor.getLong(0);
        note.NOTE_TITLE = cursor.getString(1);
        note.NOTE_CONTENT = cursor.getString(2);
        note.NOTE_TIMESTAMP = cursor.getString(3);

        return note;
    }
}
