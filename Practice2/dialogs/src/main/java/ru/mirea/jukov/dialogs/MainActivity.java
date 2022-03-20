package ru.mirea.jukov.dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    public void clickTimeDialog(View view) {
        Calendar curTime = Calendar.getInstance();

        int hour = curTime.get(Calendar.HOUR_OF_DAY);
        int minute = curTime.get(Calendar.MINUTE);

        MyTimeDialogFragment myTimeDialogFragment = new MyTimeDialogFragment(this,0, hour,minute,true);
        myTimeDialogFragment.show();
    }

    public void clickDateDialog(View view) {
        Calendar date = Calendar.getInstance();

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        MyDateDialogFragment myDateDialogFragment = new MyDateDialogFragment(this, year, month, day);
        myDateDialogFragment.show();
    }

    public void clickProgressDialog(View view) {
        MyProgressDialogFragment myProgressDialogFragment = new MyProgressDialogFragment(this);
        myProgressDialogFragment.show();
    }
}