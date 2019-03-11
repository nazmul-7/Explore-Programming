package com.example.triangle3soft.exploreprogramming.Model;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.triangle3soft.exploreprogramming.MainActivity;
import com.example.triangle3soft.exploreprogramming.R;

public class SplashScreen extends AppCompatActivity {

    private TextView text1;
    public static Typeface t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.splash);
        super.onCreate(savedInstanceState);

        // Remove the title ber
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification ber

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        text1 = (TextView) findViewById(R.id.text1);

        Typeface t11 = Typeface.createFromAsset(getAssets(),"font/Amaranth_Regular.otf");
        t1 = t11;

        text1.setTypeface(t1);

        Thread th = new Thread(){

            @Override
            public void run(){

                try {
                    sleep(3000);




                    Intent ii = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(ii);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        };
        th.start();

    }
}
