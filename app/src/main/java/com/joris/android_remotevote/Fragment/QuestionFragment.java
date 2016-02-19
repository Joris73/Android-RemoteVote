package com.joris.android_remotevote.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.joris.android_remotevote.R;

public class QuestionFragment extends Fragment {

    private Question question;
    private SondageActivity context;
    private TextView timer;

    public QuestionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = getArguments().getParcelable("question");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.question_layout);
        Button button;

        timer = (TextView) view.findViewById(R.id.timerview);
        context = (SondageActivity) view.getContext();
        TextView title = (TextView) view.findViewById(R.id.tv_question_title);
        title.setText(question.getContent());

        for (final Answers answers : question.getAnswers()) {
            button = (Button) inflater.inflate(R.layout.button_answers, layout, false);
            button.setText(answers.getContent());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color;
                    if (answers.isSelected()) {
                        answers.setSelected(false);
                        color = R.color.colorPrimary;
                    } else {
                        answers.setSelected(true);
                        color = R.color.greyDark;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        view.setBackgroundColor(context.getColor(color));
                    } else {
                        view.setBackgroundColor(context.getResources().getColor(color));
                    }
                }
            });

            layout.addView(button);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("done!");
            }
        }.start();
    }
}
