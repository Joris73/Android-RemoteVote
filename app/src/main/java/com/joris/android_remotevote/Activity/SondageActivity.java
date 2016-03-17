package com.joris.android_remotevote.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.joris.android_remotevote.Fragment.FinishFragment;
import com.joris.android_remotevote.Fragment.QuestionFragment;
import com.joris.android_remotevote.Fragment.SondageFragment;
import com.joris.android_remotevote.Models.Answers;
import com.joris.android_remotevote.Models.Question;
import com.joris.android_remotevote.Models.Sondage;
import com.joris.android_remotevote.R;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SondageActivity extends AppCompatActivity {

    private ProgressActivity progressActivity;
    private IconDrawable errorDrawable;
    private String idSondage;
    private static Sondage sondage;

    private static int actualQuestion;
    private String username;
    public String API = "http://%s/api/sondages/";
    public String API_GET_SONDAGE;

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
            username = getIntent().getExtras().getString("username");
            String urlserver = getIntent().getExtras().getString("urlserver");
            API_GET_SONDAGE = String.format(API, urlserver);
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

    public void nextQuestion(Question previousQuestion) {
        if (previousQuestion != null) {
            sondage.getQuestions().get(actualQuestion - 1).setAnswers(previousQuestion.getAnswers());
            JSONObject jsonObject = new JSONObject();
            try {
                JSONArray idResponse = new JSONArray();
                ArrayList<Answers> answers = previousQuestion.getAnswers();
                for (int i = 0; i < answers.size(); i++) {
                    if (answers.get(i).isSelected())
                        idResponse.put(i);
                }
                jsonObject.put("user", username);
                jsonObject.put("answers", idResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new POSTQuestion(sondage.getIdSimple(), actualQuestion - 1, jsonObject).execute(API_GET_SONDAGE);
        }

        Fragment fragment;
        if (actualQuestion < sondage.getQuestions().size()) {
            fragment = new QuestionFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable("question", sondage.getQuestions().get(actualQuestion++));
            fragment.setArguments(bundle);
        } else {
            fragment = new FinishFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("sondage", sondage);
            fragment.setArguments(bundle);
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
            if (result != null) {
                JsonElement jsonResult = new JsonParser().parse(result);
                Gson gson = new Gson();
                sondage = gson.fromJson(jsonResult, Sondage.class);
                if (sondage == null) {
                    error();
                } else {
                    progressActivity.showContent();

                    setFragmentSondage();
                }
            } else {
                error();
            }
        }

        private void error() {
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

        @Override
        protected void onPreExecute() {
            progressActivity.showLoading();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class POSTQuestion extends AsyncTask<String, Void, String> {

        final String id;
        final int idQuestion;
        final JSONObject jsonObject;

        public POSTQuestion(String id, int idQuestion, JSONObject jsonObject) {
            this.id = id;
            this.idQuestion = idQuestion;
            this.jsonObject = jsonObject;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpRequest request = HttpRequest.post(params[0] + id + "/questions/" + idQuestion)
                        .contentType("application/json")
                        .send(jsonObject.toString());
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
            if (result == null) {
                new POSTQuestion(this.id, this.idQuestion, jsonObject).execute(API_GET_SONDAGE);
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
