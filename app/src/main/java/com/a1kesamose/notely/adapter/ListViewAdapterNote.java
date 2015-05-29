package com.a1kesamose.notely.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a1kesamose.notely.R;
import com.a1kesamose.notely.objects.Note;

import java.util.List;

public class ListViewAdapterNote extends BaseAdapter
{
    private List<Note> list;
    private LayoutInflater layoutInflater;

    public ListViewAdapterNote(Context context, List<Note> list)
    {
        this.list = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        view = layoutInflater.inflate(R.layout.list_item_note, viewGroup, false);
        final TextView textViewTitle = (TextView)view.findViewById(R.id.textView_title_list_item_note);
        final TextView textViewContent = (TextView)view.findViewById(R.id.textView_content_list_item_note);
        final TextView textViewTimestamp = (TextView)view.findViewById(R.id.textView_timestamp_list_item_note);
        textViewTitle.setText(list.get(position).NOTE_TITLE);
        textViewContent.setText(list.get(position).NOTE_CONTENT);
        textViewTimestamp.setText(list.get(position).NOTE_TIMESTAMP);

        return view;
    }
}
