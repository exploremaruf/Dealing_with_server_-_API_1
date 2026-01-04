package com.maruf.server;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvname,tvmail,tvnumber,tvadress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvname=findViewById(R.id.name);
        tvmail=findViewById(R.id.mail);
        tvnumber=findViewById(R.id.number);
        tvadress=findViewById(R.id.adress);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.107/apps/data.json";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObjec = new JSONObject(response);
                            String name = jsonObjec.getString("name");
                            String Number = jsonObjec.getString("Number");
                            String mail = jsonObjec.getString("mail");
                            String Adress = jsonObjec.getString("Adress");

                            tvname.setText(name);
                            tvnumber.setText(Number);
                            tvmail.setText(mail);
                            tvadress.setText(Adress);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}