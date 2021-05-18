package com.example.my_project_volley;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    String urlAdressSend = "http://192.168.0.39/send_data_to_server.php";
    String urlAdressDownload = "http://192.168.0.39/download_data_from_server.php";

    EditText yourName;
    TextView Text_for_user;
    TextView ResponseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yourName = findViewById(R.id.userName);
        Text_for_user = findViewById(R.id.programmAnswer);
        ResponseData = findViewById(R.id.tv_DownloadData);
    }
    public void Send_data_on_server(View view){
        String userData = yourName.getText().toString();
        if (userData.isEmpty()){
            Text_for_user.setText("Ошибка! Укажите ваше имя!");
        }else{
            Text_for_user.setText(null);
            SendPOSTrequest(userData);
        }
    }

    public void Download_data_from_server(View view){
        String userData = "";
        SendPOSTrequest(userData);
    }



    private  void SendPOSTrequest(String strData){
        if (strData.isEmpty()){
            CustomJsonObjectRequest myRequest = new CustomJsonObjectRequest(Request.Method.GET, urlAdressDownload, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MainActivity.this, "Данные успешно получены!", Toast.LENGTH_LONG).show();
                    ResponseData.setText(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                    Text_for_user.setText(error.toString());
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
        }
        else {
            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("Name", strData);
                CustomJsonObjectRequest resultJSON = new CustomJsonObjectRequest(Request.Method.POST, urlAdressSend, requestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, "Данные успешно отправлены!", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                        Text_for_user.setText(error.toString());
                    }
                });
                App.getApp().addToRequestQueue(resultJSON);
                resultJSON.setRetryPolicy(new RetryPolicy() {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}