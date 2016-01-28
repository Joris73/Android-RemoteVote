package com.joris.android_remotevote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_SETTINGS = 42;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        username = sharedPrefs.getString("prefUsername", "");
        if (username.equals("")) {
            launchPreference();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                launchPreference();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchPreference() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivityForResult(intent, RESULT_SETTINGS);
    }
}
