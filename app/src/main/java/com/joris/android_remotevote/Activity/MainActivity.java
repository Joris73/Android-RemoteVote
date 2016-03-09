package com.joris.android_remotevote.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.joris.android_remotevote.R;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_SETTINGS = 42;
    private String username;
    private TextView tvWelcome;
    private ImageView imgQrcode;
    private TextInputLayout inputLayoutIdSurvey;
    private EditText inputIdSurvey;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvWelcome = (TextView) findViewById(R.id.tv_welcome);
        imgQrcode = (ImageView) findViewById(R.id.img_qrcode);
        inputLayoutIdSurvey = (TextInputLayout) findViewById(R.id.input_layout_idSurvey);
        inputIdSurvey = (EditText) findViewById(R.id.input_idSurvey);
        btnSignup = (Button) findViewById(R.id.btn_signup);

        imgQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchScan();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idSondage = inputIdSurvey.getText().toString();
                launchSondage(idSondage);
            }
        });
    }

    private void launchSondage(String idSondage) {
        Intent sondageIntent = new Intent(getBaseContext(), SondageActivity.class);
        sondageIntent.putExtra("idSondage", idSondage);
        startActivity(sondageIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        username = sharedPrefs.getString("prefUsername", "");
        if (username.equals("")) {
            launchPreference();
        } else {
            String welcome = getResources().getString(R.string.welcome_messages, username);
            tvWelcome.setText(welcome);
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

    private void launchScan() {
        new IntentIntegrator(this)
                .setCaptureActivity(CaptureActivityAnyOrientation.class)
                .setOrientationLocked(false)
                .initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                launchSondage(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
