package com.prototype.uoh.sacchaadhaar.util;


import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.LayoutInflater;
        import android.view.View;

import com.prototype.uoh.sacchaadhaar.R;

//import com.prototype.uoh.sacchaadhaar.R;


public class ShowResponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showResponse(View view){

        AlertDialog.Builder alertadd =new AlertDialog.Builder(this);
        LayoutInflater factory =LayoutInflater.from(this);
        final View successview;


        successview = factory.inflate(R.layout.activity_sucess,null);


        alertadd.setView(successview);
        alertadd.show();
    }
    public void showFailureResponse(View view){
        AlertDialog.Builder alertadd =new AlertDialog.Builder(this);
        LayoutInflater factory =LayoutInflater.from(this);
        final View failureview;


        failureview = factory.inflate(R.layout.failure,null);


        alertadd.setView(failureview);
          alertadd.show();
//        Intent intent = new Intent (this, Main2Activity.class);
//        startActivity(intent);
//
    }
}