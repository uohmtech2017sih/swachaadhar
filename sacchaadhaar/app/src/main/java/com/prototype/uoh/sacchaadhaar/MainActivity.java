package com.prototype.uoh.sacchaadhaar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prototype.uoh.sacchaadhaar.scanner.MorphoProcess;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MorphoProcess.getPermission(this);

        SharedPreferences sharedPref = getSharedPreferences("yas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("db2", -1);
        editor.putInt("cid", -1);
        editor.commit();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, SecActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
