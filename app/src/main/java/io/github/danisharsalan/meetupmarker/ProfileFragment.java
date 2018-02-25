package io.github.danisharsalan.meetupmarker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


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

    TextView firstName_, lastName_, bio_;
    ImageView profPic;
    Button done, logout;
    RequestQueue queue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Navigation nav = (Navigation) getActivity();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        firstName_ = (TextView) v.findViewById(R.id.firstName);
        lastName_ = (TextView) v.findViewById(R.id.lastName);
        bio_ = (TextView) v.findViewById(R.id.bio);
        profPic = (ImageView) v.findViewById(R.id.profPic);
        setFirstName_(nav.firstName);
        setLastName_(nav.lastName);
        setImg(nav.profile_picture_url);

        done = (Button) v.findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue = Volley.newRequestQueue(v.getContext());
                StringRequest sr = new StringRequest(Request.Method.POST,"https://meetupmarker.appspot.com/register", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Register Response", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    mPostCommentResponse.requestEndedWithError(error);
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("userId",nav.id);
                        params.put("firstname",nav.firstName);
                        params.put("lastname",nav.lastName);
                        params.put("bio",bio_.getText().toString());
                        params.put("picUrl",nav.profile_picture_url);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","form-data");
                        return params;
                    }
                };
                queue.add(sr);
                nav.loadFragment(new AddEvent());
            }
        });

        return v;

    }



    public void setFirstName_(String first){
        firstName_.setText(first);
    }

    public void setLastName_(String last){
        lastName_.setText(last);
    }

    public void setImg(String profPicURL){
        Picasso.with(getContext())
                .load(profPicURL)
                .into(profPic);
    }


}
