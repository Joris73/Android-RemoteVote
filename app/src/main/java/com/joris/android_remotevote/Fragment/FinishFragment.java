package com.joris.android_remotevote.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joris.android_remotevote.Activity.SondageActivity;
import com.joris.android_remotevote.Models.Sondage;
import com.joris.android_remotevote.R;

public class FinishFragment extends Fragment {

    private Sondage sondage;
    private SondageActivity context;

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
        context = (SondageActivity) view.getContext();
        TextView title = (TextView) view.findViewById(R.id.tv_sondage_title);
        title.setText(sondage.getTitle());
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
