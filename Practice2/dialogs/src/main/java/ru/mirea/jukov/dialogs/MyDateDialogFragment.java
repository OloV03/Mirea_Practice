package ru.mirea.jukov.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class MyDateDialogFragment {
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { }
    };

    Context context;
    int year, month, dayOfMonth;

    public MyDateDialogFragment(Context context, int year, int month, int dayOfMonth) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public void show(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}
