package com.example.naokihonda.calculator;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

        //DBの中身を一覧表示させる
        //DBの作成などを行うクラスをインスタンス化
        UserOpenHelper dbHelper = new UserOpenHelper(this);
        //読み込みを行うのでgetReadableDatabase()メソッドを使用
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db != null) {
            try {
                //データの一覧をリストなどのビューに渡すために使用されるクラス
                //ArrayAdapter<String>型の変数adapterにandroidにデフォルトで入っているViewをセット
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
                //変数sqlにSQL文を代入
                String sql = "SELECT * FROM MyTable";
                //Cursor(カーソル）クラスを使って、DB内のデータを取得
                Cursor csr = db.rawQuery(sql, null);
                //カーソルをレコードの先頭行へ移動
                csr.moveToFirst();
                //DB内では[0]=id [1]=date [2]=title [3]=priceが入っている
                //[1]→[2]→[3]と取得をして、adapterにセット
                for (int i = 0; i < csr.getCount(); i++) {
                    String str1 = csr.getString(1);
                    String str2 = csr.getString(2);
                    String str3 = csr.getString(3);
                    adapter.add(str1);
                    adapter.add(str2);
                    adapter.add(str3);
                    //[3]=priceまで取得したら、次の行へ移動
                    csr.moveToNext();
                }
                //カーソルを終了
                csr.close();
                //DBを閉じる
                db.close();
                //ListViewのIDを取得
                ListView listView = (ListView) findViewById(R.id.list);
                //ListViewのsetAdapterメソッドを使って、取得したDB内のデータをセット、表示
                listView.setAdapter(adapter);
            } catch (SQLException e) {
                //例外発生時にエラーログを出力
                Log.e("SQL","error",e);
            }
        }
    }

    public void searchList(View view) {

        try {
            //DBの作成などを行うクラスをインスタンス化
            UserOpenHelper dbhelper = new UserOpenHelper(this);
            //読み込みを行うのでgetReadableDatabase()メソッドを使用
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            if (db != null) {
                //ArrayAdapter<String>型の変数adapterにandroidにデフォルトで入っているViewをセット
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

                //TextViewを取得
                TextView date = (TextView) findViewById(R.id.search_day);
                //String型に変換
                String Date = date.getText().toString().trim();

                //TextViewを取得
                EditText title = (EditText) findViewById(R.id.search_word);
                //String型に変換
                String Title = title.getText().toString().trim();

                //日付かタイトルの条件にあったものを抽出
                String sql = "SELECT * FROM MyTable WHERE title like '%"+ Title +"%' OR strftime('%Y/ %tm/ %td',date) like '%"+ Date+"%'";

                //Cursor(カーソル）クラスを使って、抽出したDB内のデータを取得
                Cursor csr = db.rawQuery(sql, null);
                //カーソルをレコードの先頭行へ移動
                csr.moveToFirst();
                //[1]→[2]→[3]と取得をして、adapterにセット
                for (int i = 0; i < csr.getCount(); i++) {
                    String str1 = csr.getString(1);
                    String str2 = csr.getString(2);
                    String str3 = csr.getString(3);
                    adapter.add(str1);
                    adapter.add(str2);
                    adapter.add(str3);
                    csr.moveToNext();
                }
                //カーソルを終了
                csr.close();
                //DBを閉じる
                db.close();
                //ListViewのIDを取得
                ListView listView = (ListView) findViewById(R.id.list);
                //ListViewのsetAdapterメソッドを使って、取得したDB内のデータをセット、表示
                listView.setAdapter(adapter);
            }
        }catch(SQLException e){
            //例外発生時にエラーログを出力
            Log.e("List","error",e);
        }
    }

    //Dialogの中のOK押下で呼ばれるメソッド
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //TextViewを取得
        TextView textView = (TextView) findViewById(R.id.search_day);
        //デイトピッカーで選択した日付をテキストにセット
        textView.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
    }

    public void showDatePickerDialog(View v) {
        //日選択ボタン押下時にカレンダー型デイトピッカーを表示
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}