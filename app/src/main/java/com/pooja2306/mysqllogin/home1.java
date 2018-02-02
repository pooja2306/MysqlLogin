package com.pooja2306.mysqllogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class home1 extends com.pooja2306.mysqllogin.Menu {

    String name, email, c_no, address, pwd, cpwd;
    EditText nameEt, emailEt, c_noEt, addressEt, pwdEt, cpwdEt;
    Button reg;
    String line = null;
    String result = null;
    InputStream is = null;
    String id = null;
    String name_f, email_f, c_no_f, add_f, pwd_f;
    String name_u,c_no_u,add_u,pwd_u, msg;
    int status=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        nameEt = (EditText) findViewById(R.id.user);
        emailEt = (EditText) findViewById(R.id.email);
        emailEt.setFocusable(false);
        c_noEt = (EditText) findViewById(R.id.c_no);
        addressEt = (EditText) findViewById(R.id.addr);
        pwdEt = (EditText) findViewById(R.id.pwd);
        cpwdEt = (EditText) findViewById(R.id.pwd2);
        reg = (Button) findViewById(R.id.btnReg);

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref",MODE_PRIVATE);
        id = pref.getString("id","");

       // Toast.makeText(this, "Id="+id, Toast.LENGTH_SHORT).show();
        new Fetch().execute("");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_u = nameEt.getText().toString();
                add_u = addressEt.getText().toString();
                c_no_u = c_noEt.getText().toString();
                pwd_u = pwdEt.getText().toString();

                new Update().execute("");
            }
        });
    }

    class Fetch extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {


            Config_link c = new Config_link();
            ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("id", id));

            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(c.url + "fetch.php");
                httpPost.setEntity(new UrlEncodedFormEntity(values)); // has URL encoded values
                HttpResponse Response = httpClient.execute(httpPost); //Will get whatever the PHP page will echo/print
                HttpEntity entity = Response.getEntity();
                is = entity.getContent(); // has result = true
                Log.i("Tag", "Connection successful");
            } catch (Exception e) {
                Log.i("Tag", "not connected");
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.i("TAG", "result retrieved " + result);
            } catch (Exception e) {
                Log.i("Tag", "result not retrieved" + e.toString());

            }
            try {
                JSONObject obj = new JSONObject(result);
                if (obj.getString("result").equalsIgnoreCase("true")) {
                    name_f = obj.getString("name");
                    email_f = obj.getString("email");
                    c_no_f = obj.getString("c_no");
                    add_f = obj.getString("address");
                    pwd_f = obj.getString("pwd");
                }

            } catch (Exception e) {
                Log.i("tag", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            nameEt.setText(name_f);
            emailEt.setText(email_f);
            c_noEt.setText(c_no_f);
            addressEt.setText(add_f);
            pwdEt.setText(pwd_f);

        }
    }


    class Update extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] params) {

            Config_link c = new Config_link();
            ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("id", id));
            values.add(new BasicNameValuePair("name", name_u ));
            values.add(new BasicNameValuePair("contact_no", c_no_u));
            values.add(new BasicNameValuePair("address", add_u));
            values.add(new BasicNameValuePair("password", pwd_u));

            try
            {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(c.url+"Update.php");
                httpPost.setEntity(new UrlEncodedFormEntity(values)); // has URL encoded values
                HttpResponse Response = httpClient.execute(httpPost); //Will get whatever the PHP page will echo/print
                HttpEntity entity = Response.getEntity();
                is = entity.getContent(); // has result = true
                Log.i("Tag", "Connection successful");
            } catch (Exception e) {
                Log.i("Tag", "not connected");
            }
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.i("TAG", "result retrieved " + result);
            } catch (Exception e) {
                Log.i("Tag", "result not retrieved" + e.toString());

            }
            try {
                JSONObject obj = new JSONObject(result);
                if(obj.getString("result").equalsIgnoreCase("true"))
                {
                    msg = obj.getString("msg");
                    status = 1;
                }
                else if(obj.getString("result").equalsIgnoreCase("false"))
                {
                    msg = obj.getString("msg");
                    status = 0;
                }
            }
            catch (Exception e) {
                Log.i("tag", e.toString());
            }

            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            if(status == 1)
            {
                Toast.makeText(home1.this, msg, Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(home1.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }



}