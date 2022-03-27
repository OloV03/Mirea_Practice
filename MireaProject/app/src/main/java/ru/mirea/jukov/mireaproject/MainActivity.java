package ru.mirea.jukov.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import ru.mirea.jukov.mireaproject.databinding.ActivityMainBinding;
import ru.mirea.jukov.mireaproject.ui.mediaPlayer.PlayerService;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = getSupportFragmentManager();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onButtonNumberClick(View view) {
        TextView textView = findViewById(R.id.currentText);
        Button but = findViewById(view.getId());

        String curText = textView.getText().toString();
        curText += but.getText().toString();

        textView.setText(curText);
    }

    private int firstNum = 0;
    private int secondNum = 0;
    private String operation = "+";

    public void onButtonOperationClick(View view){
        TextView textView = findViewById(R.id.currentText);
        firstNum = Integer.parseInt(textView.getText().toString());

        Button but = findViewById(view.getId());
        operation = but.getText().toString();

        textView.setText("");
    }

    public void getResultButton(View view){
        TextView textView = findViewById(R.id.currentText);
        TextView resText = findViewById(R.id.resText);

        String curText = textView.getText().toString();
        secondNum = Integer.parseInt(curText);

        double res = 0;
        switch (operation){
            case "+":
                res = firstNum + secondNum;
                break;
            case "-":
                res = firstNum - secondNum;
                break;
            case "/":
                res = firstNum / secondNum;
                break;
            case "*":
                res = firstNum * secondNum;
                break;
        }
        textView.setText(String.valueOf(firstNum)+" "+operation+" "+String.valueOf(secondNum));
        resText.setText(Double.toString(res));
    }

    public void clickHome(View view){
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("https://metanit.com");
    }

    public void clickFind(View view){
        WebView webView = findViewById(R.id.webView);
        EditText editText = findViewById(R.id.urlEdit);

        webView.loadUrl(editText.getText().toString());
    }

    public void onClickPlayMusic(View view) {
        startService(
                new Intent(MainActivity.this, PlayerService.class));
    }
    public void onClickStopMusic(View view) {
        stopService(
                new Intent(MainActivity.this, PlayerService.class));
    }
}