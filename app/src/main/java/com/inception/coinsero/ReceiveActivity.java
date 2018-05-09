package com.inception.coinsero;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveActivity extends AppCompatActivity {


    private RequestQueue requestQueue ;

    private CustomTextView address ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        requestQueue = Volley.newRequestQueue(ReceiveActivity.this);

        address = findViewById(R.id.address);

        get_Address();
    }


    public void get_Address() {


        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("username", sp.getString("username", ""));
            jsonObject.put("module", "getaddress");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.url), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONObject result = response.getJSONObject("result");

                    String s = result.getString("addressses").split(",")[0];

                    s.replace("\"" , "");

                    s.replace("[" , "");







                    address.setText(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void finiss(View view) {

        finish();
    }
}
