package com.pdxdev.agenda.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pdxdev.agenda.CustomJsonObjectRequest;
import com.pdxdev.agenda.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TesteVolley extends AppCompatActivity {

    private final String name = "app";
    private final String password = "asdf123";
    private String token;

    public TextView textView;
    public Button button;
    public Button button2;

    public String AUTH_URL = "http://10.0.2.2:8080/res/authapp";
    public String USUARIOSAPP_URL = "http://10.0.2.2:8080/res/usuariosapp";
    public String TAG = "TesteVolley";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_volley);

        textView = (TextView) findViewById(R.id.teste_data);
        button = (Button) findViewById(R.id.teste_btn);
        button2 = (Button) findViewById(R.id.teste_btn2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authApp();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });


    }

    private void authApp() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("password", password);

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, AUTH_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        token = "Bearer " + response.optString("access_token");
                        textView.setText(token);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }
        );

        NetworkManager.getInstance(TesteVolley.this).addToRequestQueue(request, "TesteVolley");
    }

    private void sendData() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", "asdasdasd");
        params.put("email", "niasdasdlto@tario.com");
        params.put("origin", "facebook");
        params.put("id_origin", "asdasdsa");

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, USUARIOSAPP_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText(response.optString("name"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        textView.setText("Erro");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("authorization", token);
                return headers;
            }
        };

        NetworkManager.getInstance(TesteVolley.this).addToRequestQueue(request, "TesteVolley");
    }
}
