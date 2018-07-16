package com.vaibhav.mynote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<NotesContent> {
    private SparseBooleanArray mSelectedItemsIds;
    public ArrayList<NotesContent> subject = new ArrayList<NotesContent>();
    public CustomArrayAdapter(Context context, int resourceid ,  ArrayList<NotesContent> objects) {
        super(context, resourceid ,objects);
        mSelectedItemsIds = new SparseBooleanArray();
        subject = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.edit_content, null);
        TextView content = (TextView)v.findViewById(R.id.content);
        TextView date1 = (TextView)v.findViewById(R.id.date1);
        content.setText(subject.get(position).details);
        date1.setText(subject.get(position).date);
        return  v;
    }
    @Override
    public void remove(NotesContent object) {
        subject.remove(object);
        notifyDataSetChanged();
    }

    public ArrayList<NotesContent> getWorldPopulation() {
        return subject;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
