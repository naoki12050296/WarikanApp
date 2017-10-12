package com.example.naokihonda.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static com.example.naokihonda.calculator.R.id.sumPrice;
import static com.example.naokihonda.calculator.R.string.sum_price;

/**
 * Created by naokihonda on 2017/09/18.
 */


public class InputForm_Activity extends AppCompatActivity {

    //onCreate メソッドは、アクティビティクラスがインスタンス化される時に自動的に処理されるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }

    //割り勘結果の金額のフィールド定義
    public static Integer warikanKingaku = 0;

    //電卓で計算する
    public void calculation(View view) {

        //EditTextを取得
        EditText sumprice = (EditText) findViewById(sumPrice);
        EditText persons = (EditText) findViewById(R.id.persons);
        EditText campaign = (EditText) findViewById(R.id.campaign);

        //EditTextの中身を取得
        //getTextでtextを取得、toStringでString型に変換、trimで先頭または最後の空白を除去
        String sumPrice = sumprice.getText().toString().trim();
        String Persons = persons.getText().toString().trim();
        String Campaign = campaign.getText().toString().trim();

        //中身をみてエラーを出す
        //合計金額が入力されていなかったら、エラーを出す
        if (sumPrice.equals("")) {
            sumprice.setError("合計金額が入っていません");
        }
        //合計人数が入力されていなかったら、エラーを出す
        else if (Persons.equals("")) {
            persons.setError("合計人数が入っていません");
        }

        else{

            //合計金額と合計人数を計算に用いるため、int型に変換
            int sum_price = Integer.parseInt(sumPrice);
            int sum_persons = Integer.parseInt(Persons);

            if(sum_price == 0){
                sumprice.setError("0は入力できません");
            }
            else if(sum_persons == 0){
                persons.setError("0は入力できません");
            }
            else if(sum_price <= sum_persons){
                persons.setError("合計人数が合計金額を超えています");
            }
            //カンパ金額が入っていなかった時の処理
            //割ったものを変数warikanKingakuに代入
            else if (Campaign.equals("")) {

                //カンパ金額が入っていなかったら、単純に合計金額を合計人数で割る
                warikanKingaku = sum_price / sum_persons;

                //切り金額を取得
                getRadiogroup();

                //結果表示画面に遷移（割り勘金額の値を渡す）
                forResultDisplay();
            }


            //カンパ金額が入っていた時の処理
            else{
                //カンパ金額を取得し、String型に変換、計算に用いるためにint型に変換（いきなりintに変換はできない）
                campaign = (EditText) findViewById(R.id.campaign);
                Campaign = campaign.getText().toString().trim();
                int CamPaign = Integer.parseInt(Campaign);

                //合計金額からカンパ金額を引いたものを合計人数で割り、結果を変数warikanに代入
                warikanKingaku = (sum_price - CamPaign) / sum_persons;

                //切り金額を取得
                getRadiogroup();

                //結果表示画面に遷移（割り勘金額の値を渡す）
                forResultDisplay();
            }
        }
    }

    public void getRadiogroup() {

        // ラジオグループのオブジェクトを取得
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);

        // チェックされているラジオボタンの ID を取得
        int CheckedId = rg.getCheckedRadioButtonId();

        // チェックされているラジオボタンオブジェクトを取得（CheckedIdとidを紐付け）
        RadioButton radioButton = (RadioButton) findViewById(CheckedId);

        // getId()でラジオボタンを識別し、ラジオボタンごとの処理を行う
        boolean checked = radioButton.isChecked();
        int amari;
        switch (radioButton.getId()) {
            case R.id.roundup_100:
                if (checked) {
                    amari = (warikanKingaku % 100);
                    if(amari != 0){
                        warikanKingaku = (warikanKingaku / 100) + 1;
                        warikanKingaku *= 100;
                    }else{
                        warikanKingaku = (warikanKingaku / 100);
                        warikanKingaku *= 100;
                    }
                }
                break;
            case R.id.roundup_500:
                if (checked) {
                    amari = (warikanKingaku % 500);
                    if(amari != 0){
                        warikanKingaku = (warikanKingaku / 500) + 1;
                        warikanKingaku *= 500;
                    }else{
                        warikanKingaku = (warikanKingaku / 500);
                        warikanKingaku *= 500;
                    }
                }
                break;
            case R.id.roundup_1000:
                if (checked) {
                    amari = (warikanKingaku % 1000);
                    if(amari != 0){
                        warikanKingaku = (warikanKingaku / 1000) + 1;
                        warikanKingaku *= 1000;
                    }else{
                        warikanKingaku = (warikanKingaku / 1000);
                        warikanKingaku *= 1000;
                    }
                }
                break;
            case R.id.radioButton_not:
                if (checked) {
                }
                break;
            default:
                break;
        }
    }

    public void forResultDisplay() {
        //次の画面へ遷移する
        //Intentクラスをインスタンス生成、行き先指定
        Intent intent = new Intent(getApplication(), ResultDisplay_Activity.class);
        //putExtraで行き先に渡す値とキーを設定
        intent.putExtra("EXTRA_WARIKAN", warikanKingaku);
        //startActivityで行き先の画面を起動
        startActivity(intent);
    }

    public void listView(View view) {

        //一覧を表示ボタンを押した時に呼び出される
        //Intentクラスをインスタンス生成、行き先指定
        Intent intent = new Intent(getApplication(), ListDisplay_Activity.class);
        //startActivityで行き先の画面を起動
        startActivity(intent);
    }
}