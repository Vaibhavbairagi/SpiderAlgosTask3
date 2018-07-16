package com.vaibhav.mynote;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final int ID_CONSTANT = 0x0101010;
    private ArrayList<NotesContent> Task;
    private Context mContext;
    private Cursor res;

    public WidgetFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        Task = new ArrayList<NotesContent>();
        DatabaseHelper db = new DatabaseHelper(mContext);
        res = db.getALLdata();
        if(res.getCount()!=0) {
            while (res.moveToNext()) {
                String id = res.getString(0);
                Log.d("Id_data" , id);
                String task = res.getString(1);
                String date = res.getString(2);
               Task.add(new NotesContent(Integer.parseInt(id) , task , date));
            }
        }
        Log.d("size" , String.valueOf(Task.size()));
    }

    @Override
    public void onDataSetChanged() {
    Log.d("check" , "onDataSetChanged");
   }

    @Override
    public void onDestroy() {
      Task.clear();
    }

    @Override
    public int getCount() {
        return Task.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        NotesContent singleTask = Task.get(position);
        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        itemView.setTextViewText(R.id.content, singleTask.details);
        itemView.setTextViewText(R.id.date1, singleTask.date);
        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
