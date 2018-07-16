package com.vaibhav.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class upDateAct extends AppCompatActivity {
    public EditText editText;
    public TextView date;

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
            Intent myIntent = new Intent(upDateAct.this , MainActivity.class);
            myIntent.putExtra("task" , editText.getText().toString());
            myIntent.putExtra("date" , date.getText().toString());
            Intent i = getIntent();
            int id_data = i.getIntExtra("id_data" , 0);
            Log.d("idValue" , String.valueOf(id_data));
            myIntent.putExtra("_id" , id_data );
            setResult(RESULT_OK , myIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date);
        editText = (EditText)findViewById(R.id.editText);
        date = (TextView)findViewById(R.id.date);
        Intent j = getIntent();
        editText.setText(j.getStringExtra("content"));
        long dates = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM  dd, yyyy  h:mm a");
        String dateString = sdf.format(dates);
        date.setText(dateString);
    }
}
