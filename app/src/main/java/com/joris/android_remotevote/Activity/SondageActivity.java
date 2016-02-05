package com.joris.android_remotevote.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.joris.android_remotevote.Fragment.FinishFragment;
import com.joris.android_remotevote.Fragment.QuestionFragment;
import com.joris.android_remotevote.Fragment.SondageFragment;
import com.joris.android_remotevote.Models.Sondage;
import com.joris.android_remotevote.R;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressActivity;

public class SondageActivity extends AppCompatActivity {

    public final static String API_GET_SONDAGE = "http://10.7.244.111:3000/api/sondages/";
    private ProgressActivity progressActivity;
    private IconDrawable errorDrawable;
    private String idSondage;
    private static Sondage sondage;

    private static int actualQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage);

        setProgressActivity();

        getIdSondage();

        actualQuestion = 0;

        new DownloadSondage().execute(API_GET_SONDAGE + idSondage);
    }

    private void getIdSondage() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            idSondage = getIntent().getExtras().getString("idSondage");
        }
        if (idSondage == null) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
        }
    }

    private void setProgressActivity() {
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivitySondage);

        errorDrawable = new IconDrawable(this, Iconify.IconValue.zmdi_wifi_off)
                .colorRes(android.R.color.white);
    }

    private void setFragmentSondage() {
        Fragment sondageFragmentment = new SondageFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("sondage", sondage);
        sondageFragmentment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_sondage, sondageFragmentment).commit();
    }

    public void nextQuestion() {
        Fragment fragment;
        if (actualQuestion < sondage.getQuestions().size()) {
            fragment = new QuestionFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable("question", sondage.getQuestions().get(actualQuestion++));
            fragment.setArguments(bundle);
        } else {
            fragment = new FinishFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_sondage, fragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private class DownloadSondage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpRequest request = HttpRequest.get(params[0]);
                String result = null;
                if (request.ok()) {
                    result = request.body();
                }
                return result;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.wtf("timmmmm", "dfgdgd " + result);
            if (result != null) {
                JsonElement jsonResult = new JsonParser().parse(result);
                Gson gson = new Gson();
                sondage = gson.fromJson(jsonResult, Sondage.class);
                progressActivity.showContent();

                setFragmentSondage();
            } else {
                progressActivity.showError(errorDrawable,
                        getString(R.string.error_title_connexion),
                        getString(R.string.error_message_connexion),
                        getString(R.string.tryagain), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new DownloadSondage().execute(API_GET_SONDAGE);
                            }
                        });
            }
        }

        @Override
        protected void onPreExecute() {
            progressActivity.showLoading();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
