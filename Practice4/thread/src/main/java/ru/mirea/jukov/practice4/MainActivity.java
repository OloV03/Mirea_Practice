package ru.mirea.jukov.practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;
    public void startLagClick(View view) {
//        long endTime = System.currentTimeMillis() + 20 * 1000;
//        while (System.currentTimeMillis() < endTime) {
//            synchronized (this) {
//                try {
//                    wait(endTime - System.currentTimeMillis());
//                } catch (Exception e) {
//                }
//            }

        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread thread = new Thread(CountLessons);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread.start();

        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Текущий поток: " + mainThread.getName());

        mainThread.setName("MireaThread");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());
}


    Runnable CountLessons = () -> {
        int count = (9 + 19) * 2 / 30;
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Аverage number of lessons: " + count);
    };
}