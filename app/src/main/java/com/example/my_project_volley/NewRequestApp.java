package com.example.my_project_volley;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NewRequestApp  {
    TextView myView;
    String UrlAdress;

    public NewRequestApp GetDataFromServer(TextView myView, String UrlAdress){
        CustomJsonObjectRequest myRequest = new CustomJsonObjectRequest(Request.Method.GET, UrlAdress, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               HashMap<String, String>answerServer = new HashMap<>();
               String rezult = "";
                try {
                    answerServer = ParsingJSONObject_on_String(response, "categories", "");

                    for (String key: answerServer.keySet()){
                        rezult += key + ": " + answerServer.get(key) + "\n";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myView.setText(rezult);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myView.setText(error.toString());
            }
        });
        App.getApp().addToRequestQueue(myRequest);
        myRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        return null;
    }

    public HashMap<String, String> ParsingJSONObject_on_String(JSONObject myJson, String nameArray_1, String nameArray_2) throws JSONException {
        HashMap<String, String> RezultParsing = new HashMap<>();
        String myKey = "";
        String valueKey = "";
        JSONArray ourArray = myJson.getJSONArray(nameArray_1);
        for (int i = 0; i < ourArray.length(); i++){
            Iterator<?> keys1 = ourArray.getJSONObject(i).keys();
            while (keys1.hasNext()){
                String timeKey = (String) keys1.next();
                valueKey = ourArray.getJSONObject(i).getString(timeKey);
                if ( timeKey.equals(myKey)){
                    timeKey += String.valueOf(i);
                    RezultParsing.put(timeKey, valueKey);
                }
                else{
                    RezultParsing.put(timeKey, valueKey);
                    myKey = timeKey;
                }
            }
            if ( ourArray.getJSONObject(i).has(nameArray_2)){
                JSONArray childArray = ourArray.getJSONArray(i);
                for (int k = 0; k < childArray.length(); k++){
                    Iterator<?>keys2 = childArray.getJSONObject(k).keys();
                    while (keys2.hasNext()){
                        String timeKey = (String) keys2.next();
                        valueKey = childArray.getJSONObject(k).getString(timeKey);
                        if (timeKey.equals(myKey)){
                            timeKey += String.valueOf(k);
                            RezultParsing.put(timeKey, valueKey);
                        }
                        else{
                            RezultParsing.put(timeKey, valueKey);
                            myKey = timeKey;
                        }
                    }
                }
            }
        }
        return RezultParsing;
    }


}



