package com.example.naokihonda.calculator;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.DialogFragment;

/**
 * Created by naokihonda on 2017/09/29.
 */

public class List extends AppCompatActivity implements OnDateSetListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        UserOpenHelper dbHelper = new UserOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db != null) {
            try {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
                String sql = "SELECT * FROM MyTable ORDER BY date DESC";
                Cursor csr = db.rawQuery(sql, null);
                csr.moveToFirst();
                for (int i = 0; i < csr.getCount(); i++) {
                    String str1 = csr.getString(1);
                    String str2 = csr.getString(2);
                    String str3 = csr.getString(3);
                    adapter.add(str1);
                    adapter.add(str2);
                    adapter.add(str3);
                    csr.moveToNext();
                }
                csr.close();
                db.close();
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            } catch (SQLException e) {

            }
        }
    }

    public void searchList(View view) {

        try {
            UserOpenHelper dbhelper = new UserOpenHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            if (db != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

                TextView date = (TextView) findViewById(R.id.search_day);
                String Date = date.getText().toString().trim();

                EditText title = (EditText) findViewById(R.id.search_word);
                String Title = title.getText().toString().trim();

                //String sql = "SELECT * " + "FROM MyTable " + "WHERE date like '%" + Date + "%' AND title like '%" + Title +"%'";

                String sql = "SELECT * FROM MyTable WHERE title like '%"+ Title +"%' OR strftime('%Y/ %tm/ %td',date) like '%"+ Date+"%'";

                //select * from MyTable where strftime('%Y/ %m/ %d',date) like '%2017/ 10/ 07%';

                Cursor csr = db.rawQuery(sql, null);
                csr.moveToFirst();
                for (int i = 0; i < csr.getCount(); i++) {
                    String str1 = csr.getString(1);
                    String str2 = csr.getString(2);
                    String str3 = csr.getString(3);
                    adapter.add(str1);
                    adapter.add(str2);
                    adapter.add(str3);
                    csr.moveToNext();
                }
                csr.close();
                db.close();
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }
        }catch(SQLException e){
            Log.e("List","error",e);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView textView = (TextView) findViewById(R.id.search_day);
        textView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}