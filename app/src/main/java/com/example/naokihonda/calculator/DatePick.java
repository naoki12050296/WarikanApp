package com.example.naokihonda.calculator;

/**
 * Created by naokihonda on 2017/10/01.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

    public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        //カレンダー型のDialogを生成
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //生成したDialogを呼び出し元に戻す
            return new DatePickerDialog(getActivity(), (List)getActivity(),  year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //何もしない（List.javaでオーバーライドする）
        }

    }

