package com.pooja2306.mysqllogin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by abc on 18-10-2017.
 */

public class Menu extends AppCompatActivity {
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main2_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.update) {
            Intent i = new Intent(getApplication(), home1.class);
            startActivity(i);
        } else if (id == R.id.about) {
            Intent i = new Intent(getApplication(), About.class);
            startActivity(i);
        } else if (id == R.id.contact) {
            Intent i = new Intent(getApplication(), ContactNew.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("Login", null);

            editor.commit();
            Intent i = new Intent(getApplication(), MainActivity.class);

            startActivity(i);
            finish();
        }
        return true;
    }
}
