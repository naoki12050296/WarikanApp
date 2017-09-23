package com.example.naokihonda.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.naokihonda.calculator.Form.warikan;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int i = intent.getIntExtra("EXTRA_WARIKAN",warikan);
        TextView resultLabel = (TextView)findViewById(R.id.resultLabel);
        resultLabel.setText(String.valueOf(i));

    }

}
