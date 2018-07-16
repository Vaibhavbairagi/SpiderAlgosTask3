package com.vaibhav.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Main2Activity extends AppCompatActivity {
    public EditText editText;
    public TextView date;
    public int element_id = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act2 , menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save){
            element_id++;
            SharedPreferences.Editor editor = getSharedPreferences("ELEMENT_ID", MODE_PRIVATE).edit();
            editor.putInt("idName", element_id);
            editor.apply();
            Intent myIntent = new Intent(Main2Activity.this , MainActivity.class);
            myIntent.putExtra("task" , editText.getText().toString());
            myIntent.putExtra("date" , date.getText().toString());
            myIntent.putExtra("_id" , element_id);
            setResult(RESULT_OK , myIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText = (EditText)findViewById(R.id.editText);
        date = (TextView)findViewById(R.id.date);
        long dates = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM  dd, yyyy  h:mm a");
        String dateString = sdf.format(dates);
        date.setText(dateString);
        SharedPreferences prefs = getSharedPreferences("ELEMENT_ID", MODE_PRIVATE);
        element_id = prefs.getInt("idName" , 0);
    }
}
