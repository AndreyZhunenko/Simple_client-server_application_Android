package com.example.my_project_volley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class for_request_on_server_activity extends Activity {

    TextView answerData;
    String urlAdressDownload = "http://192.168.0.39/download_data_from_server.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_request_on_server);

        answerData = findViewById(R.id.tv_DownloadData2);

        NewRequestApp OurRequest = new NewRequestApp().GetDataFromServer(answerData, urlAdressDownload);


    }
}
