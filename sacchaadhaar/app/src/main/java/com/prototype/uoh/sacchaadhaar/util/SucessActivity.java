package com.prototype.uoh.sacchaadhaar.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.prototype.uoh.sacchaadhaar.R;
import com.prototype.uoh.sacchaadhaar.database.ProcessInfo;
import com.prototype.uoh.sacchaadhaar.qrCode.VerificationActivity;

/**
 * Created by kc on 31/3/17.
 */

public class SucessActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);


    }
    public void scan(View view){
        ProcessInfo pi=ProcessInfo.getInstance();
        pi.setAadharnum("");

        Intent i=new Intent(this, VerificationActivity.class);
        startActivity(i);
    }


}
