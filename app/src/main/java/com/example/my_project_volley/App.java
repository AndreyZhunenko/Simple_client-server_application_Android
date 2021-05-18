package com.example.my_project_volley;

import android.app.Application;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class App extends Application {
    private static RequestQueue myRequestQueue;
    private static App my_app;

    @Override
    public void onCreate() {
        super.onCreate();
        my_app = this;
    }

    public static synchronized  App getApp(){
        return my_app;
    }

    public RequestQueue getMyRequestQueue(){
        if (myRequestQueue == null){
            myRequestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
        return myRequestQueue;
    }

    public void addToRequestQueue(Request myRequest){
        getMyRequestQueue().add(myRequest);
    }

}
