package com.example.naokihonda.calculator;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.example.naokihonda.calculator.Form.warikan;


public class Result extends AppCompatActivity {

    // InsertボタンのClickリスナー
    private View.OnClickListener buttonInsert_ClickListener = new View.OnClickListener(){
        public void onClick(View v) {buttonInsert_Click(v);}};
    /* UpdateボタンのClickリスナー */
    private View.OnClickListener buttonUpdate_ClickListener = new View.OnClickListener(){
        public void onClick(View v) {buttonUpdate_Click(v);}};
    /* DeleteボタンのClickリスナー */
    private View.OnClickListener buttonDelete_ClickListener = new View.OnClickListener(){
        public void onClick(View v) {buttonDelete_Click(v);}};
    private String title_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int w = intent.getIntExtra("EXTRA_WARIKAN", warikan);
        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        resultLabel.setText(String.valueOf(w) + "円");

        //ボタンにClickリスナーを設定する。
        Button buttonInsert = (Button)this.findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(buttonInsert_ClickListener);
        Button buttonUpdate = (Button)this.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(buttonUpdate_ClickListener);
        Button buttonDelete = (Button)this.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(buttonDelete_ClickListener);
    }

    /**
     * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
     */

    /*
     * InsertボタンClick処理
     */
    public void buttonInsert_Click(View v){

        //カスタムビューの設定
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View layout = inflater.inflate(R.layout.title_input,null);

        //AlertDialog生成
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //レイアウト設定
        builder.setView(layout);

        //ＯＫボタン設定
        builder.setPositiveButton("ok",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){

                ContentValues values = new ContentValues();

                /**
                 * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
                 */

                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date(System.currentTimeMillis());

                EditText Title = (EditText)layout.findViewById(R.id.title);
                String title = Title.getText().toString().trim();

                values.put("Date", String.valueOf(date));
                values.put("Title", title);
                values.put("Result", warikan);

                UserOpenHelper dbHelper = new UserOpenHelper(Result.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                long ret;
                try {
                    ret = db.insert("MyTable", null, values);
                } finally {
                    db.close();
                }
                if (ret == -1) {
                    Toast.makeText(Result.this,"Insert失敗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Result.this,"Insert成功", Toast.LENGTH_SHORT).show();
                }

            }

        });

        //Cancelボタン設定
        builder.setNegativeButton("cancel",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //キャンセルなので何もしない
            }
        });

        //ダイアログの表示
        builder.create().show();
    }

    /*
      UpdateボタンClick処理*/

    private void buttonUpdate_Click(View v){
        ContentValues values = new ContentValues();
        values.put("Age",24);
        String whereClause = "No = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = "1";

        UserOpenHelper dbHelper = new UserOpenHelper(Result.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret;
        try {
            ret = db.update("MyTable", values, whereClause, whereArgs);
        } finally {
            db.close();
        }
        if (ret == -1){
            Toast.makeText(this, "Update失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update成功", Toast.LENGTH_SHORT).show();
        }
    }

    /* DeleteボタンClick処理 */
    private void buttonDelete_Click(View v){

        deleteDatabase("cal.db");
        Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();

        /*String whereClause = "No = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = "1";

        UserOpenHelper dbHelper = new UserOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret;
        try {
            ret = db.delete("MyTable", whereClause, whereArgs);
        } finally {
            db.close();
        }
        if (ret == -1){
            Toast.makeText(this, "Delete失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();
        }*/
    }
}



    /*
        //Realmインスタンス取得
        Realm realm = Realm.getInstance(this);

        //トランザクション開始
        realm.beginTransaction();

        //Realmインスタンス化
        User user = new User();

        //値をセット
        user.setSum_price(sum_price);
        user.setPersons(persons);
        user.setResult(warikan);

        realm.commitTransaction();

        realm.close();
        */








