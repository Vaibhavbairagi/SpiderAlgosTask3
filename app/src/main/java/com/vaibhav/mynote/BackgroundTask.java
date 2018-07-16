package com.vaibhav.mynote;

import android.content.Context;
import android.os.AsyncTask;

public class BackgroundTask extends AsyncTask<String , Void , Void> {
    public Context ctx;
    public BackgroundTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
     DatabaseHelper db = new DatabaseHelper(ctx);
     boolean PutData = db.insertData(strings[0] , strings[1] , strings[2]);
     return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
