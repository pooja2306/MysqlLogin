package com.pooja2306.mysqllogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Register extends AppCompatActivity {


    String name, email, c_no, address, pwd,cpwd ;
    EditText nameEt, emailEt, c_noEt, addressEt, pwdEt, cpwdEt;
    Button reg;
    String line=null;
    String result=null;
    InputStream is=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEt = (EditText) findViewById(R.id.user);


        emailEt= (EditText) findViewById(R.id.email);
        c_noEt = (EditText) findViewById(R.id.c_no);
        addressEt = (EditText) findViewById(R.id.addr);
        pwdEt = (EditText) findViewById(R.id.pwd);
        cpwdEt = (EditText) findViewById(R.id.pwd2);
        reg = (Button) findViewById(R.id.btnReg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameEt.getText().toString();
                email = emailEt.getText().toString();
                c_no = c_noEt.getText().toString();
                address = addressEt.getText().toString();
                pwd = pwdEt.getText().toString();
                cpwd = cpwdEt.getText().toString();
                Toast.makeText(Register.this, "Values = "+name.length(), Toast.LENGTH_SHORT).show();


                if(name.length()>0)
                {
                    nameEt.setFocusable(false);
                    if(email.length()>0)
                    {
                        emailEt.setFocusable(false);
                        if(c_no.length()>0)
                        {
                            c_noEt.setFocusable(false);
                            if(address.length()>0)
                            {
                                addressEt.setFocusable(false);
                                if(pwd.length()>0)
                                {
                                    pwdEt.setFocusable(false);
                                    if(cpwd.equals(pwd))
                                    {
                                        cpwdEt.setFocusable(false);
                                        new Insert().execute("");
                                        Toast.makeText(Register.this, "All Done", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        cpwdEt.setFocusable(true);
                                        cpwdEt.setError("Passwords donot match");
                                    }
                                }
                                else
                                {
                                    pwdEt.setFocusable(true);
                                    pwdEt.setError("Required Feild");
                                }
                            }
                            else
                            {
                                addressEt.setFocusable(true);
                                addressEt.setError("Required Feild");
                            }
                        }
                        else
                        {
                            c_noEt.setFocusable(true);
                            c_noEt.setError("Required Feild");
                        }
                    }
                    else
                    {
                        emailEt.setFocusable(true);
                        emailEt.setError("Required Feild");
                    }
                }
                else
                {
                    nameEt.setFocusable(true);
                    nameEt.setError("Required Feild");
                }

            }
        });


    }
    class Insert extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] params) {


            Config_link c = new Config_link();
            ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("name", name));
            values.add(new BasicNameValuePair("email", email));
            values.add(new BasicNameValuePair("contact_no", c_no));
            values.add(new BasicNameValuePair("address", address));
            values.add(new BasicNameValuePair("password", pwd));

            try
            {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(c.url+"/android_php/practice.php");
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
                Log.i("TAG", "result retrived " + result);
            } catch (Exception e) {
                Log.i("Tag", "result not retrived" + e.toString());

            }
            try {
                JSONObject obj = new JSONObject(result);
                if(obj.getString("result").equalsIgnoreCase("true"))
                {
                    Intent i = new Intent(getApplication(),MainActivity.class);
                    startActivity(i);

                }
            }
            catch (Exception e) {
                Log.i("tag", e.toString());
            }

            return null;

        }
    }
}
