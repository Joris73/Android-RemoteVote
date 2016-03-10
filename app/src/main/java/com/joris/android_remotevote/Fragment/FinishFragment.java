package com.joris.android_remotevote.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joris.android_remotevote.Activity.SondageActivity;
import com.joris.android_remotevote.Models.Answers;
import com.joris.android_remotevote.Models.Question;
import com.joris.android_remotevote.Models.Sondage;
import com.joris.android_remotevote.R;

import java.util.ArrayList;

public class FinishFragment extends Fragment {

    private Sondage sondage;
    private SondageActivity context;
    private ArrayList<Question> caca;

    public FinishFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sondage = getArguments().getParcelable("sondage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_resultat);
        context = (SondageActivity) view.getContext();
        TextView title = (TextView) view.findViewById(R.id.tv_sondage_title);
        title.setText(sondage.getTitle());

        TextView textView;
        for (Question question : sondage.getQuestions()) {
            textView = new TextView(context);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(question.getContent());
            layout.addView(textView);

            for (Answers answers : question.getUserAnswers()) {
                textView = new TextView(context);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(answers.getContent());
                if (answers.isGood()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextColor(context.getColor(R.color.green));
                    } else {
                        textView.setTextColor(context.getResources().getColor(R.color.green));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextColor(context.getColor(R.color.red));
                    } else {
                        textView.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
                layout.addView(textView);
            }
        }

        Button startButton = (Button) view.findViewById(R.id.btn_home);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = context.getPackageManager()
                        .getLaunchIntentForPackage(context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
