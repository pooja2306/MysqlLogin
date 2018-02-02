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

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText nameEt, pwdEt;
    String name, pwd;
    Button btn;
    String line=null;
    String result=null;
    InputStream is=null;
    int status=0;
    String msg=null;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String login = pref.getString("Login",null);

        if(login != null)
        {
            if(login.equalsIgnoreCase("yes"))
            {
                Intent myIntent = new Intent(getApplication(),Main2Activity.class);
                startActivity(myIntent);
            }
        }

        tv = (TextView) findViewById(R.id.newuser);
        nameEt = (EditText) findViewById(R.id.userName);
        pwdEt = (EditText) findViewById(R.id.pwd);
        btn = (Button) findViewById(R.id.btnLogin);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplication(), Register.class);
                startActivity(i);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            name = nameEt.getText().toString();
            pwd = pwdEt.getText().toString();
                new Login().execute("");

            }
        });
    }

    class Login extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] params) {

            Config_link c = new Config_link();
            ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("name", name));
            values.add(new BasicNameValuePair("password", pwd));

            try
            {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(c.url+"login.php");
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
                    status=1;
                    id = obj.getString("id");

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("Login","yes");
                    editor.putString("id",id);
                    editor.commit();

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
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplication(),Main2Activity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
