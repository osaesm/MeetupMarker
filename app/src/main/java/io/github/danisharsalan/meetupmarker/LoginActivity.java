package io.github.danisharsalan.meetupmarker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    String firstName, lastName, email, profile_picture_url, id;
    ProgressDialog mDialog;

    RequestQueue queue, queue2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving Data...");
                mDialog.show();

                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        try {
                            firstName = response.getJSONObject().getString("first_name");
                            lastName = response.getJSONObject().getString("last_name");
                            email = response.getJSONObject().getString("email");
                            id = response.getJSONObject().getString("id");
                            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
                            profile_picture_url = profile_picture.toString();

                            updateUI(true);

//                            String url = "https://blahblah.com/is_registered?id=" + id;
//                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    if(response.equals("true")) {
//                                        updateUI(true);
//                                    } else {
//                                        updateUI(false);
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//
//                                }
//                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login attempt canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Login attempt failed", Toast.LENGTH_SHORT).show();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null) {
            mDialog = new ProgressDialog(LoginActivity.this);
            mDialog.setMessage("Retrieving Data...");
            mDialog.show();

            String accesstoken = AccessToken.getCurrentAccessToken().getToken();

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    mDialog.dismiss();
                    try {
                        firstName = response.getJSONObject().getString("first_name");
                        lastName = response.getJSONObject().getString("last_name");
                        email = response.getJSONObject().getString("email");
                        id = response.getJSONObject().getString("id");
                        URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
                        profile_picture_url = profile_picture.toString();

                        queue = Volley.newRequestQueue(getBaseContext());
                        String url = "https://meetupmarker.appspot.com/is_registered?id=" + id;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("true")) {

                                    queue2 = Volley.newRequestQueue(getBaseContext());
                                    StringRequest sr = new StringRequest(Request.Method.POST,"https://meetupmarker.appspot.com/get_user", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("Register Response", response);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String,String> getParams(){
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put("user_id", id);
                                            return params;
                                        }

                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put("Content-Type","x-www-form-urlencoded");
                                            return params;
                                        }
                                    };
                                    queue2.add(sr);




                                    updateUI(true);
                                } else {
                                    updateUI(false);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(stringRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
        }

    }

    private void updateUI(boolean toMap) {
        Intent i = new Intent(LoginActivity.this, Navigation.class);

        i.putExtra("to map", toMap);

        if(profile_picture_url == null) {
            i.putExtra("photo url", "https://support.plymouth.edu/kb_images/Yammer/default.jpeg");
        } else {
            i.putExtra("photo url", profile_picture_url);
        }

        i.putExtra("first name", firstName);
        i.putExtra("last name", lastName);
        i.putExtra("email", email);
        i.putExtra("id", id);

        startActivity(i);
    }

}
