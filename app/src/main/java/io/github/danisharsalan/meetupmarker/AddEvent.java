package io.github.danisharsalan.meetupmarker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.KeyEvent.*;
import static android.widget.LinearLayout.*;

public class AddEvent extends Fragment {

    private LinearLayout mLayout;
    private RelativeLayout ll;

    private ArrayList<String> sports = new ArrayList<>();
    private TextView[] textViews;
    private FragmentActivity frag;

    private EditText number;

    private String chosenSport;

    private String numberOfPeople;

    TextView tvTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frag = super.getActivity();
        ll = (RelativeLayout) (inflater.inflate(R.layout.fragment_add_event, container, false));

        mLayout = (LinearLayout) ll.findViewById(R.id.linlay1);

        mLayout.addView(createNewTextView("Basketball"));
        mLayout.addView(createNewTextView("Hockey"));
        mLayout.addView(createNewTextView("Racquetball"));
        mLayout.addView(createNewTextView("Soccer"));
        mLayout.addView(createNewTextView("Tennis"));
        mLayout.addView(createNewTextView("Work out"));

        number = (EditText) ll.findViewById(R.id.number);

        number.setText("1");

        number.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    numberOfPeople = number.getText().toString();
                }
                return false;
            }
        });

        return ll;
    }


    private TextView createNewTextView(String text){
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView((getActivity()));

        lparams.setMargins(0, 20, 0, 20);
        textView.setLayoutParams(lparams);
        textView.setTextColor(Color.argb(173,255,255,255));
        textView.setTextSize(18);
        textView.setMaxLines(1);
        textView.setPadding(20,10,20,10);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(text);
        textView.setClickable(true);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTemp = (TextView)view;
                tvTemp.setTextColor(Color.CYAN);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after .1s = 100ms

                        tvTemp.setTextColor(getResources().getColor(R.color.black));

                        chosenSport = (String)(tvTemp.getText());
                        // OPEN MAP SHOWING CHOSEN SPORTS AND ALSO CREATE EVENT FOR DB
                    }
                }, 100);
            }
        });
        return textView;
    }
}
