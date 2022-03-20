package ru.mirea.jukov.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class MyTimeDialogFragment{
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) { }
    };

    Context context;
    int themeResId, hourOdDay, minute;
    boolean is24HourView;

    public MyTimeDialogFragment(Context context, int themeResId, int hourOfDay, int minute, boolean is24HourView) {
        this.context = context;
        this.themeResId = themeResId;
        this.hourOdDay = hourOfDay;
        this.minute = minute;
        this.is24HourView = is24HourView;
    }

    public void show(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,timeSetListener,hourOdDay,minute,is24HourView);
        timePickerDialog.show();
    }
}
