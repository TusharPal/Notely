package com.a1kesamose.notely.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.a1kesamose.notely.R;
import com.a1kesamose.notely.adapter.ListViewAdapterNote;
import com.a1kesamose.notely.database.DatabaseSourceNote;
import com.a1kesamose.notely.objects.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ActivityMain extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    private ListView listView;

    private DatabaseSourceNote databaseSourceNote;
    private ListViewAdapterNote listViewAdapterNote;
    private List<Note> noteList;
    private long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            setTheme(android.R.style.Theme_Material);
        }
        setContentView(R.layout.activity_main);

        databaseSourceNote = new DatabaseSourceNote(this);
        databaseSourceNote.open();

        listView = (ListView)findViewById(R.id.listView_activity_main);

        refreshListView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        databaseSourceNote.open();
        refreshListView();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        databaseSourceNote.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add_note_activity_main:
            {
                alertDialogEditor(true, 0);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        alertDialogEditor(false, noteList.get(position).NOTE_ID);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        noteId = noteList.get(position).NOTE_ID;
        alertDialogDelete();

        return true;
    }

    private void alertDialogDelete()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this note?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                databaseSourceNote.deleteNote(noteId);
                refreshListView();

                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private void alertDialogEditor(final boolean FLAG_NEW_NOTE, long id)
    {
        final Note note;
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.alert_dialog_note_editor, null, false);
        final EditText editTextTitle = (EditText)view.findViewById(R.id.editText_title_alert_dialog_note_editor);
        final EditText editTextContent = (EditText)view.findViewById(R.id.editText_content_alert_dialog_note_editor);

        if(FLAG_NEW_NOTE)
        {
            note = new Note();
        }
        else
        {
            note = databaseSourceNote.getNote(id);
            editTextTitle.setText(note.NOTE_TITLE);
            editTextContent.setText(note.NOTE_CONTENT);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(editTextTitle.getText().toString().matches("") || editTextContent.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Empty note discarded", Toast.LENGTH_SHORT).show();
                }
                else if(FLAG_NEW_NOTE)
                {
                    note.NOTE_TITLE = editTextTitle.getText().toString();
                    note.NOTE_CONTENT = editTextContent.getText().toString();
                    note.NOTE_TIMESTAMP = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    databaseSourceNote.createNote(note);
                    refreshListView();

                    dialogInterface.dismiss();
                }
                else
                {
                    note.NOTE_TITLE = editTextTitle.getText().toString();
                    note.NOTE_CONTENT = editTextContent.getText().toString();
                    note.NOTE_TIMESTAMP = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    databaseSourceNote.editNote(note);
                    refreshListView();

                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(editTextTitle.getText().toString().matches("") || editTextContent.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Empty note discarded", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Note discarded", Toast.LENGTH_SHORT).show();
                }

                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private void refreshListView()
    {
        noteList = databaseSourceNote.getNoteList();
        listViewAdapterNote = new ListViewAdapterNote(this, noteList);

        listView.setAdapter(listViewAdapterNote);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }
}
