package id.kelompok3.bookspace.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import id.kelompok3.bookspace.R;
import id.kelompok3.bookspace.activity.landing.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotoLogin = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(gotoLogin);
                MainActivity.this.finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}