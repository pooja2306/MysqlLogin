package com.pooja2306.mysqllogin;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends com.pooja2306.mysqllogin.Menu{

    ImageView img;
    int imgId[] = {R.drawable.abc,
            R.drawable.abc_2,
            R.drawable.abc_3,
            R.drawable.abc_4,
            R.drawable.abc_5};
    int index = 0;
    int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = (ImageView) findViewById(R.id.image);

        final Handler hm = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                img.setImageResource(imgId[index % imgId.length]);
                index++;
                try {
                    Animation an = AnimationUtils.loadAnimation(getApplication(), android.R.anim.fade_in);
                    img.startAnimation(an);
                }
                catch (Exception e) {


                }
            }


        };
        Timer time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                hm.post(r);
            }
        },1, 3000);

        Toast.makeText(this, "Id: ", Toast.LENGTH_SHORT).show();
    }

}
