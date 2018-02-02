package com.pooja2306.mysqllogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class Home extends AppCompatActivity {

    String name, email, c_no, address, pwd,cpwd ;
    EditText nameEt, emailEt, c_noEt, addressEt, pwdEt, cpwdEt;
    Button reg;
    String line=null;
    String result=null;
    InputStream is=null;
    String id=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nameEt = (EditText) findViewById(R.id.user);
        emailEt= (EditText) findViewById(R.id.email);
        c_noEt = (EditText) findViewById(R.id.c_no);
        addressEt = (EditText) findViewById(R.id.addr);
        pwdEt = (EditText) findViewById(R.id.pwd);
        cpwdEt = (EditText) findViewById(R.id.pwd2);
        reg = (Button) findViewById(R.id.btnReg);

        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        Toast.makeText(this, "ID = "+id, Toast.LENGTH_SHORT).show();

//        reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = nameEt.getText().toString();
//                email = emailEt.getText().toString();
//                c_no = c_noEt.getText().toString();
//                address = addressEt.getText().toString();
//                pwd = pwdEt.getText().toString();
//                cpwd = cpwdEt.getText().toString();
//            }
//        });
    }
}
