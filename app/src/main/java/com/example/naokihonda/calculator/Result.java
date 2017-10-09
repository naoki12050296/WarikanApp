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

/**
 * Created by naokihonda on 2017/09/18.
 */

public class Result extends AppCompatActivity {

    // InsertボタンのClickリスナー登録
    private View.OnClickListener buttonInsert_ClickListener = new View.OnClickListener(){
        //ボタンが押された時に呼ばれるメソッド
        public void onClick(View v) {
            buttonInsert_Click(v);
        }
    };

    // DeleteボタンのClickリスナー登録
    private View.OnClickListener buttonDelete_ClickListener = new View.OnClickListener(){
        //ボタンが押された時に呼ばれるメソッド
        public void onClick(View v) {
            buttonDelete_Click(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Intentを受け取る
        Intent intent = getIntent();
        //Intentに保存されたデータを変数wにwarikanを代入
        int w = intent.getIntExtra("EXTRA_WARIKAN", warikan);
        //TextViewを取得
        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        //取得したTextViewに割り勘金額をセット、表示
        resultLabel.setText(String.valueOf(w) + "円");

        //ボタンにclickListenerを設定
        //今回のお会計を保存するボタンのIDを取得
        Button buttonInsert = (Button)this.findViewById(R.id.buttonInsert);
        //↑のボタンにclickListenerをセット
        buttonInsert.setOnClickListener(buttonInsert_ClickListener);

        //履歴を消去するボタンのIDを取得
        Button buttonDelete = (Button)this.findViewById(R.id.buttonDelete);
        //↑のボタンにclickListenerをセット
        buttonDelete.setOnClickListener(buttonDelete_ClickListener);
    }

    //InsertボタンClick処理
    public void buttonInsert_Click(View v){

        //LayoutInflaterは、指定したxmlのレイアウト(View)リソースを利用できる仕組み

        // コンテキストから取得
        //LayoutInflater inflater = LayoutInflater.from(this);

        // アクティビティから取得
        //LayoutInflater inflater = getLayoutInflater();

        // システムサービスから取得
        //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        LayoutInflater inflater = getLayoutInflater();

        //XMLからビューを取得（今後不変のためfinal）
        final View layout = inflater.inflate(R.layout.title_input,null);

        //AlertDialog生成（今後不変のためfinal）
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //レイアウト設定
        builder.setView(layout);

        //ＯＫボタン設定
        builder.setPositiveButton("ok",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){

                //ContentValuesクラス = DBテーブルに含まれるカラムをキーとし、カラムに対して設定したい値を保存するためのクラス
                //ContentValuesクラスのインスタンス生成
                ContentValues values = new ContentValues();

                //現在日時をyyyy/MM/dd形式で取得する
                Date date = new Date(System.currentTimeMillis());

                //AlertDialogの中のEditTextのIDを取得
                EditText Title = (EditText)layout.findViewById(R.id.title);
                //String型に変換
                String title = Title.getText().toString().trim();

                //データ（カラムと値のペア）を保存
                values.put("Date", String.valueOf(date));
                values.put("Title", title);
                values.put("Result", warikan);

                //DBの作成などを行うクラスをインスタンス化
                UserOpenHelper dbHelper = new UserOpenHelper(Result.this);

                //書き込みを行うので getWritableDatabase()メソッドを使用（読み込みonlyならgetReadableDatabase()メソッド）
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                long ret;
                try {
                    //DBへデータ挿入
                    ret = db.insert("MyTable", null, values);
                } finally {
                    db.close();
                }
                //失敗だった場合と成功だった場合でToastを出す
                //エラーだった場合、-1が戻り値として返される
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

    //DeleteボタンClick処理
    private void buttonDelete_Click(View v){

        //DB削除処理
        deleteDatabase("cal.db");
        //削除したことをユーザーにわかりやすくするため、Toast表示
        Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();
    }
}









