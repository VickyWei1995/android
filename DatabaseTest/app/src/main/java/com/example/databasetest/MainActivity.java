package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private  MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createButton = (Button) findViewById(R.id.create_database);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // begin to set value for the first data
                values.put("name", "The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                // insert the first data
                db.insert("book",null, values);
                values.clear();
                //
                values.put("name", "The test of coding");
                values.put("author","Ivien Wei");
                values.put("pages",600);
                values.put("price",19.99);
                // insert the first data
                db.insert("book",null, values);
            }
        });

        Button updateButton = (Button) findViewById(R.id.update_data);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("book", values, "name=?", new String[]
                        {"The Da Vinci Code"});
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]
                        {"500"});
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // query all data from database
                Cursor sursor = db.query("Book", null, null,null,
                        null,null,null);
                if (sursor.moveToFirst()) {
                    do {
                        // loop all cursor instance, then print
                        String name = sursor.getString(sursor.getColumnIndex("name"));
                        String author = sursor.getString(sursor.getColumnIndex("author"));
                        Double price = sursor.getDouble(sursor.getColumnIndex("price"));
                        int pages = sursor.getInt(sursor.getColumnIndex("pages"));
                        Log.d("test", "book name is: " + name);
                        Log.d("test","Author is; " + author);
                        Log.d("tets", "Price is: " + price);
                        Log.d("test", "The number of pages is: " + pages);
                    }while (sursor.moveToNext());
                }
                sursor.close();
            }
        });
    }
}
