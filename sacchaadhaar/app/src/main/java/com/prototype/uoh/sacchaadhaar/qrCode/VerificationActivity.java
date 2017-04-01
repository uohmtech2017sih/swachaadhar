package com.prototype.uoh.sacchaadhaar.qrCode;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.morpho.morphosmart.sdk.MorphoDevice;
import com.prototype.uoh.sacchaadhaar.R;
import com.prototype.uoh.sacchaadhaar.SecActivity;
import com.prototype.uoh.sacchaadhaar.database.ProcessInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import google.zxing.integration.android.IntentIntegrator;
import google.zxing.integration.android.IntentResult;
import com.prototype.uoh.sacchaadhaar.util.*;
public class VerificationActivity extends AppCompatActivity implements Observer {
    private EditText aadhaarNum;
    private boolean validAadhar = false;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;

    MorphoDevice morphoDevice;
    private static ProcessInfo pi=ProcessInfo.getInstance();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        aadhaarNum = (EditText)findViewById(R.id.aadharNumText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);




        return true;
    }
    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder alertadd =new AlertDialog.Builder(this);
        alertadd.setMessage("Do you want to logout?");
        alertadd.setCancelable(true);


        final View successview;


            alertadd.setNegativeButton("no",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();


                        }
                    });

            alertadd.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent i=new Intent(VerificationActivity.this,SecActivity.class);

                            startActivity(i);
                            dialog.cancel();




                        }
                    });
            alertadd.show();


    }
    public void launchQRCodeScanner(View v) {
        if (v.getId() == R.id.launchCameraBtn) {

            try {

                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan(IntentIntegrator.ALL_CODE_TYPES);

            } catch (ActivityNotFoundException anfe) {
                Log.w("ACtivity not found",anfe);
                //on catch, show the download dialog

                //showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

            }
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            // AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // builder.setTitle("Scan Result");
            // builder.setMessage(scanContent);
            Log.w("XML parsed data is " , scanContent);
            String a_num = "Error !!";
            try{
                InputStream stream = new ByteArrayInputStream(scanContent.getBytes("UTF-8"));

                a_num = this.getAadharNumber(stream);
            }
            catch(UnsupportedEncodingException unenEx) { Log.w("Unsupported encoding", unenEx);}


            //aadhaarNum = (EditText) findViewById(R.id.aadharNumText);

            pi.setAadharnum(a_num);
            aadhaarNum.setText(pi.getAadharnum());

            //AlertDialog alert1 = builder.create();
            //alert1.show();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received! Try Again", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String getAadharNumber(InputStream stream) {

        String aadharId = null;
        try {

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(stream);
            //  Element docEl = doc.getDocumentElement();

            NodeList nList = doc.getElementsByTagName("PrintLetterBarcodeData");
            Node dataNode = nList.item(0);
            Element eElement = (Element) dataNode;

            aadharId = eElement.getAttribute("uid");


        } catch (Exception ee) {

        }

        return aadharId;

    }
    public void onSendBtnClicked(View V) {
        if(V.getId() == R.id.submitAadharBtn) {
            // check from local database
        }
        validAadhar = true;//true only if aadhaar is found in DB
        Button scan = (Button)findViewById(R.id.scan);
        scan.setVisibility(View.VISIBLE);
    }
    public void scan(View view){
//        if(!MorphoProcess.checkPermission()){
//            MorphoProcess.getPermission(this);
//
//        }
//        else{
//            String sensorName = MorphoProcess.enumerate(morphoDevice,this);
//            if(sensorName.equals("")){
//                Toast.makeText(this,"Not Enumerated",Toast.LENGTH_SHORT).show();
//            }
//            else{
//                MorphoProcess.connect(this,morphoDevice,sensorName);
//            }
//        }
//        if(!MorphoProcess.checkPermission()){
//            Toast.makeText(this,"Morpho Device Not Connected",Toast.LENGTH_SHORT).show();
//
//        }
        AlertDialog.Builder alertadd =new AlertDialog.Builder(this);
        LayoutInflater factory =LayoutInflater.from(this);
        final View successview;
         boolean test=false;
     if(test){
        successview = factory.inflate(R.layout.activity_sucess,null);
        alertadd.setCancelable(true);
        alertadd.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        pi.setAadharnum("");
                        aadhaarNum.setText("");
                        Button scan = (Button)findViewById(R.id.scan);
                        scan.setVisibility(View.INVISIBLE);


                    }
                });



}
        else {
        alertadd.setNegativeButton("Report",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Button scan = (Button) findViewById(R.id.scan);
                        scan.setVisibility(View.INVISIBLE);
                        aadhaarNum.setText("");
                        report();

                    }
                });

         alertadd.setPositiveButton("scan",
                 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         dialog.cancel();
                         Button scan = (Button) findViewById(R.id.scan);




                     }
                 });
         successview = factory.inflate(R.layout.failure, null);
         alertadd.setView(successview);
         alertadd.show();
     }


    }

    private void report() {
        SmsManager sm=SmsManager.getDefault();
        pi.setPhonenum("9028032318");
        String number=pi.getPhonenum();
        String msg="suscpicious activity for registraion number 3274089 at center "+pi.getCenterid();
        sm.sendTextMessage(number,null,msg,null,null);

    }


    @Override
    public void update(Observable o, Object arg){

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
