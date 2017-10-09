package com.example.naokihonda.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by naokihonda on 2017/09/18.
 */


public class Form extends AppCompatActivity {

    //onCreate メソッドは、アクティビティクラスがインスタンス化される時に自動的に処理されるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //割り勘結果の金額のフィールド定義
    public static Integer warikan = 0;


    //電卓で計算する
    public void calculation(View view) {

        //EditTextを取得
        EditText sumprice = (EditText) findViewById(R.id.sumPrice);
        EditText persons = (EditText) findViewById(R.id.persons);
        EditText campaign = (EditText) findViewById(R.id.campaign);

        // ラジオグループのオブジェクトを取得
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);

        //EditTextの中身を取得
        String sumPrice = sumprice.getText().toString().trim();
        String Persons = persons.getText().toString().trim();
        String Campaign = campaign.getText().toString().trim();

        //中身をみて条件分岐
        if (sumPrice.equals("")) {
            sumprice.setError("合計金額が入っていません");
        } else if (Persons.equals("")) {
            persons.setError("合計人数が入っていません");
        } else {
            //int型に変換する
            int sum_price = Integer.parseInt(sumPrice);
            int sum_persons = Integer.parseInt(Persons);

            if (Campaign.equals("")) {
                warikan = sum_price / sum_persons;
            } else {
                campaign = (EditText) findViewById(R.id.campaign);
                Campaign = campaign.getText().toString().trim();
                int CamPaign = Integer.parseInt(Campaign);
                warikan = (sum_price - CamPaign) / sum_persons;
            }

            // チェックされているラジオボタンの ID を取得
            int CheckedId = rg.getCheckedRadioButtonId();

            // チェックされているラジオボタンオブジェクトを取得
            RadioButton radioButton = (RadioButton) findViewById(CheckedId);

            // getId()でラジオボタンを識別し、ラジオボタンごとの処理を行う
            boolean checked = radioButton.isChecked();
            switch (radioButton.getId()) {
                case R.id.rounddown_100:
                    if (checked) {
                        warikan = (warikan / 100) + 1;
                        warikan *= 100;
                    }
                    break;
                case R.id.rounddown_500:
                    if (checked) {
                        warikan = (warikan / 500) + 1;
                        warikan *= 500;
                    }
                    break;
                case R.id.rounddown_1000:
                    if (checked) {
                        warikan = (warikan / 1000) + 1;
                        warikan *= 1000;
                    }
                    break;
                case R.id.radioButton_not:
                    if (checked) {
                    }
                    break;
                default:
                    break;
            }
            //次の画面へ
            Intent intent = new Intent(getApplication(), Result.class);
            intent.putExtra("EXTRA_WARIKAN", warikan);
            startActivityForResult(intent, warikan);
        }
    }

    public void listView(View view) {

        Intent intent = new Intent(getApplication(), List.class);
        startActivity(intent, null);
    }
}