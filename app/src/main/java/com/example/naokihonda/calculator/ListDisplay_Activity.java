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

//onDateSetListener = ユーザーが日付の選択を終了したことを示すために使用されるリスナー
public class ListDisplay_Activity extends AppCompatActivity implements OnDateSetListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //DBの中身を一覧表示させる
        //DBの作成などを行うクラスをインスタンス化
        UserOpenHelper dbHelper = new UserOpenHelper(this);
        //SQLiteDatabase = SQLコマンドの作成、削除、実行、その他の一般的なデータベース管理タスクを実行するためのメソッドを
        //読み込みを行うのでgetReadableDatabase()メソッドを使用
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db != null) {
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
                    adapter.add(str1 + "        " + str2 + "        " + str3 +"円");
                    //adapter.add(str2);
                    //adapter.add(str3);
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
        }
    }

    public void searchList(View view) {
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
                String sql = null;
                if (Date == "" && Title.equals("")) {
                    sql = "SELECT * FROM MyTable";
                } else if (Title.equals("") && Date != "") {
                    sql = "SELECT * FROM MyTable WHERE strftime('%Y/ %m/ %d',date) like '%"+Date+"%'";
                } else if (Date == "") {
                    sql = "SELECT * FROM MyTable WHERE title like '%" + Title + "%'";
                }else{
                    sql = "SELECT * FROM MyTable WHERE title like '%" + Title + "%' and strftime('%Y/ %m/ %d',date) like '%"+Date+"%'";
                }
                //Cursor(カーソル）クラスを使って、抽出したDB内のデータを取得
                Cursor csr = db.rawQuery(sql, null);
                //カーソルをレコードの先頭行へ移動
                csr.moveToFirst();
                //[1]→[2]→[3]と取得をして、adapterにセット
                for (int i = 0; i < csr.getCount(); i++) {
                    String str1 = csr.getString(1);
                    String str2 = csr.getString(2);
                    String str3 = csr.getString(3);
                    adapter.add(str1 + "        " + str2 + "        " + str3 +"円");
                    //adapter.add(str2);
                    //adapter.add(str3);
                    csr.moveToNext();
                }
                //カーソルを終了
                csr.close();
                //DBを閉じる
                db.close();
                //ListViewのIDを取得
                ListView listView = (ListView) findViewById(R.id.list);
                //ListViewのsetAdapterメソッドを使って、取得したDB内のadapter(データ)をセット、表示
                listView.setAdapter(adapter);
            }
    }

    //Dialogの中のOK押下で呼ばれるメソッド
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //TextViewを取得
        TextView textView = (TextView) findViewById(R.id.search_day);
        //デイトピッカーで選択した日付をテキストにセット
        textView.setText(String.format("%d/ %02d/ %02d", year, monthOfYear + 1, dayOfMonth));
    }

    //日選択のボタン押下で呼ばれるメソッド
    public void showDatePickerDialog(View v) {
        //カレンダー型デイトピッカーを表示
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}