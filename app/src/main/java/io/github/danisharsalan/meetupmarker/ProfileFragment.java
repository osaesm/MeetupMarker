package io.github.danisharsalan.meetupmarker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


//public class ProfileFragment extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_profile, container, false);
//    }
//
//
//
//}

public class ProfileFragment extends Fragment {

    TextView firstName_, lastName_;
    ImageButton profPic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Navigation nav = (Navigation) getActivity();
        Log.d("hey", nav.firstName);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        firstName_ = (TextView) v.findViewById(R.id.firstName);
        lastName_ = (TextView) v.findViewById(R.id.lastName);
        profPic = (ImageButton) v.findViewById(R.id.profPic);
        setFirstName_(nav.firstName);
        setLastName_(nav.lastName);

        return v;

    }



    public void setFirstName_(String first){
        firstName_.setText(first);
    }

    public void setLastName_(String last){
        lastName_.setText(last);
    }

    public void setImg(String profPicURL){

    }


}
