package com.pooja2306.mysqllogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contact extends com.pooja2306.mysqllogin.Menu{

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btn = (Button) findViewById(R.id.btnSub);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
