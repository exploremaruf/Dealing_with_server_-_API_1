package com.maruf.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    ListView listview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String, String> hashMap;

    @SuppressLint("MissingInflatedId")
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
        //******************************************************************************************
        //******************************************************************************************
        progressBar = findViewById(R.id.progressBar);
        listview = findViewById(R.id.listview);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        String url = "http://192.168.1.107/apps/video.json";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressBar.setVisibility(View.GONE);

                try {
                    for (int x = 0; x < jsonArray.length(); x++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        String title = jsonObject.getString("title");
                        String video_id = jsonObject.getString("video_id");
                        HashMap hashMap = new HashMap();
                        hashMap.put("title", title);
                        hashMap.put("video_id", video_id);
                        arrayList.add(hashMap);

                        Log.d(
                                "serverresponse",
                                "Response received\nTitle: " + title + "\nVideo ID: " + video_id
                        );

                    }
                    Myadapter myadapter = new Myadapter();
                    listview.setAdapter(myadapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("serverresponse", "the error is" + volleyError.getMessage());

            }
        }

        );
        requestQueue.add(arrayRequest);

        ///******************************************************************
    }
    //*************************************************************************

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {

            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myview = inflater.inflate(R.layout.item, parent, false);

            TextView tvtitle = myview.findViewById(R.id.video_title);
            ImageView thambnail = myview.findViewById(R.id.video_thambnail);


            HashMap<String,String> hashMap = arrayList.get(position);
            String title = hashMap.get("title");
            String video_id = hashMap.get("video_id");

            tvtitle.setText(title);
            String thambnailurl="http://img.youtube.com/vi/"+video_id+"/0.jpg";

            Picasso.get().load(thambnailurl).placeholder(R.drawable.casual_tshirt).into(thambnail);



            return myview;
        }

    }

}