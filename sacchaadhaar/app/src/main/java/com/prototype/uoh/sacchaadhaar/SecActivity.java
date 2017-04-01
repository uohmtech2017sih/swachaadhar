package com.prototype.uoh.sacchaadhaar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prototype.uoh.sacchaadhaar.database.ProcessInfo;
import com.prototype.uoh.sacchaadhaar.qrCode.VerificationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecActivity extends AppCompatActivity {

    ProgressBar pb;
    EditText et1;
    EditText et2;
    String st1, st2;
    int result = -1;
    ArrayList<String> al;
    String q = "";
    ProcessInfo pi=ProcessInfo.getInstance();
    // DBHelper myDb;
    //private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);

        // myDb=new DBHelper(this);
        pb.setVisibility(View.GONE);


        // setContentView(R.layout.activity_sec);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        al = new ArrayList<String>();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        /*ask Aathreya Sir*/
        inflater.inflate(R.menu.menu, menu);



        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
              //  display("Summary");
                return true;
            case R.id.help:
                display("Logout");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

*/


    public void validate(View view) {
        st1 = et1.getText().toString().trim();
        st2 = et2.getText().toString().trim();


        if (st1.equals("") || st2.equals("")) {


            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Centreid or Password Should not be empty");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });



            AlertDialog alert11 = builder1.create();
            alert11.show();


        } else {
            //new GetContacts().execute(null,null, null);
            pi.setCenterid(et1.getText().toString().trim());
            Intent intent = new Intent(this,VerificationActivity.class);
            startActivity(intent);

        }

    }


    //****************************************************


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            HttpHandler sh2 = new HttpHandler();

            final String url = "http://yashaswiathreya.000webhostapp.com/res.php?q="+st1;
            String jsonStr = sh.makeServiceCall(url);

            final String url2 = "http://yashaswiathreya.000webhostapp.com/student.php/";
            String jsonStr2 = sh2.makeServiceCall(url2);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                }
            });

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObj.getJSONArray("centre2");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        final String id = c.getString("cid");
                        final String name = c.getString("pwd");
                        al.add(id);
                        al.add(name);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (final JSONException e) {
                }

            } else {
                // Log.e(TAG, "Couldn't get json from server.");

            }
            //*****************************************************

            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj2 = new JSONObject(jsonStr);
                    JSONArray contacts2 = jsonObj2.getJSONArray("students");
                    for (int i = 0; i < contacts2.length(); i++) {
                        JSONObject c = contacts2.getJSONObject(i);
                        final String id = c.getString("reg");
                        final String uid = c.getString("uid");
                        final String name = c.getString("uid");
                        al.add(id+"  "+uid+"  "+name);
                        //al.add(name);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),al.get(0),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (final JSONException e) {
                }

            } else {
                // Log.e(TAG, "Couldn't get json from server.");

            }






//***********************************************
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.ertx);
            if (al.get(1).equals("-1")) {
                tv.setText("Incorrect Username or password");
                Toast.makeText(SecActivity.this,"kk",Toast.LENGTH_LONG).show();
            } else {

                // tv.setText("correct Username or password");
                //  int kk=Integer.valueOf(al.get(1));
                //  SharedPreferences sharedPref = getSharedPreferences("yas", Context.MODE_PRIVATE);
                //  SharedPreferences.Editor editor = sharedPref.edit();
                //   editor.putInt("cid",kk);
                //   editor.commit();




                DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                /**
                 * CRUD Operations
                 * */
                // Inserting Contacts
                //Log.d("Insert: ", "Inserting ..");
                // String k="hi";
                int k;
                k=db.addContact(new Student(0,1001,"123412341234","Yashu1","9100000001","yashaswi1.athreya@gmail.com"));
                Toast.makeText(SecActivity.this,k+" t",Toast.LENGTH_LONG).show();
                k=db.addContact(new Student(1,1002,"123412341235","Yashu2","9100000002","yashaswi2.athreya@gmail.com"));
                Toast.makeText(SecActivity.this,k+"",Toast.LENGTH_LONG).show();

                k=db.addContact(new Student(2,1003,"123412341236","Yashu3","9100000003","yashaswi3.athreya@gmail.com"));
                Toast.makeText(SecActivity.this,k+"",Toast.LENGTH_LONG).show();

                // k=db.addContact(new Student(3,1004,"123412341237","Yashu4","9100000004","yashaswi4.athreya@gmail.com"));


                // Reading all contacts
                //Log.d("Reading: ", "Reading all contacts..");
                List<Student> contacts = db.getAllContacts();

                for (Student cn : contacts) {
                    String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                    Toast.makeText(SecActivity.this,log,Toast.LENGTH_LONG).show();
                }

                Intent intent=new Intent(SecActivity.this, VerificationActivity.class);
                startActivity(intent);













            }
        }

    }
}
