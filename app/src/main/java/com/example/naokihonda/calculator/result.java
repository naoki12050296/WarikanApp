package com.example.naokihonda.calculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.example.naokihonda.calculator.Form.warikan;
import static com.example.naokihonda.calculator.R.string.persons;
import static com.example.naokihonda.calculator.R.string.sum_price;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int w = intent.getIntExtra("EXTRA_WARIKAN", warikan);
        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        resultLabel.setText(String.valueOf(w) + "円");
    }

    public void DatabaseInsert(View view) {

        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        //処理 insert
        ContentValues newUser = new ContentValues();
        newUser.put(String.valueOf(UserContract.Users.COL_RESULT), warikan);
        long newId = db.insert(
                UserContract.Users.TABLE_NAME,
                null,
                newUser
        );

        //        ContentValues newScore = new ContentValues();
//        newScore.put(UserContract.Users.COL_SCORE, 100);
//        int updatedCount = db.update(
//                UserContract.Users.TABLE_NAME,
//                newScore,
//                UserContract.Users.COL_NAME + " = ?",
//                new String[] { "" }
//        );
//        int deletedCount = db.delete(
//                UserContract.Users.TABLE_NAME,
//                UserContract.Users.COL_NAME + " = ?",
//                new String[] { "" }
//        );

        Cursor c = null;
        c = db.query(
                UserContract.Users.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Log.v("DB_TEST", "Count: " + c.getCount());
        while(c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(UserContract.Users._ID));
            String name = c.getString(c.getColumnIndex(UserContract.Users.COL_NAME));
            int score = c.getInt(c.getColumnIndex(UserContract.Users.COL_SCORE));
            Log.v("DB_TEST", "id: " + id + " name: " + name + " score: " + score);
        }
        c.close();

        // close db
        db.close();
    }







       /* //Realmインスタンス取得
        Realm realm = Realm.getDefaultInstance();

        //トランザクション開始
        realm.beginTransaction();

        //Realmインスタンス化
        User user = new User();

        //値をセット
        user.setSum_price(sum_price);
        user.setPersons(persons);
        user.setResult(warikan);

        realm.commitTransaction();

        realm.close();*/


    }





