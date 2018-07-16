package com.vaibhav.mynote;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ListView list;
    public CustomArrayAdapter adapter;
    public ArrayList<NotesContent> object;
    public BackgroundTask mytask;
    public DatabaseHelper db;
    public int objectPos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buttonicon , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_note){
            Intent intent = new Intent(MainActivity.this , Main2Activity.class);
            MainActivity.this.startActivityForResult(intent , 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String title = data.getStringExtra("task");
                String date = data.getStringExtra("date");
                int id = data.getIntExtra("_id" , 0);
                mytask = new BackgroundTask(MainActivity.this);
                mytask.execute(String.valueOf(id) , title , date );
                adapter.add(new NotesContent(id , title , date));
                list.setAdapter(adapter);
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String title1 = data.getStringExtra("task");
                String date1 = data.getStringExtra("date");
                int id1 = data.getIntExtra("_id" , 0);
                object.remove(objectPos);
                db.deleteData(id1);
                mytask = new BackgroundTask(MainActivity.this);
                mytask.execute(String.valueOf(id1) , title1 , date1 );
                object.add(objectPos , new NotesContent(id1 , title1 , date1));
                list.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        object = new ArrayList<NotesContent>();
        adapter = new CustomArrayAdapter(MainActivity.this ,R.layout.edit_content , object);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        db = new DatabaseHelper(MainActivity.this);
        retriveData();
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount +" Items Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.multiple_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete!!");
                        builder.setMessage("Are you sure want to delete the selected items?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Calls getSelectedIds method from ListViewAdapter Class
                               SparseBooleanArray selected = adapter.getSelectedIds();
                               Log.d("Selectedids" , String.valueOf(selected));
                                // Captures all selected ids with a loop
                                for (int i = (selected.size() - 1); i >= 0; i--) {
                                    if (selected.valueAt(i)) {
                                        NotesContent selected_item = adapter
                                                .getItem(selected.keyAt(i));
                                        // Remove selected items following the ids
                                       adapter.remove(selected_item);
                                       db.deleteData(selected_item.id);
                                    }
                                }
                                // Close CAB
                                mode.finish();
                                selected.clear();
                                object = adapter.getWorldPopulation();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objectPos = position;
                int temp = object.get(position).id;
                Intent updateIntent = new Intent(MainActivity.this , upDateAct.class);
                updateIntent.putExtra("content" , object.get(position).details);
                updateIntent.putExtra("id_data" ,temp);
                MainActivity.this.startActivityForResult(updateIntent , 2);
            }
        });
    }

    public void retriveData(){
        Cursor res = db.getALLdata();
        if(res.getCount()!=0) {
            while (res.moveToNext()) {
                String id = res.getString(0);
                Log.d("Id_data" , id);
                String task = res.getString(1);
                String date = res.getString(2);
                adapter.add(new NotesContent(Integer.parseInt(id) , task , date));
                list.setAdapter(adapter);
            }
        }
    }






}
