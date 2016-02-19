package com.joris.android_remotevote.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joris.android_remotevote.Activity.SondageActivity;
import com.joris.android_remotevote.Models.Answers;
import com.joris.android_remotevote.Models.Question;
import com.joris.android_remotevote.R;

public class FinishFragment extends Fragment {

    private Question question;
    private SondageActivity context;

    public FinishFragment() {

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
        context = (SondageActivity) view.getContext();
        TextView title = (TextView) view.findViewById(R.id.tv_question_title);
        title.setText(question.getContent());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
