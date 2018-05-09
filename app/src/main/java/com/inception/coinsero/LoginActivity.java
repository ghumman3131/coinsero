package com.inception.coinsero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private RequestQueue requestQueue;

    private CustomButton login_btn ;

    private ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.login_btn);

        progressBar = findViewById(R.id.progress_bar);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    public void login(View view) {


        login_btn.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        EditText email_et = findViewById(R.id.email_et);

        EditText password_et = findViewById(R.id.password_et);




        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("username" , email_et.getText().toString());
            jsonObject.put("password" , password_et.getText().toString());
            jsonObject.put("module" , "login");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.url), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    if(response.getString("result").equals("done"))
                    {
                        JSONObject jsonObject1 = response.getJSONObject("data");

                        SharedPreferences.Editor sp = getSharedPreferences("user_info" , MODE_PRIVATE).edit();

                        sp.putString("username" , jsonObject1.getString("username"));

                        sp.commit();

                        Intent i = new Intent(LoginActivity.this , HomeActivity.class);
                        startActivity(i);
                        finish();

                    }

                    else {

                        login_btn.setVisibility(View.VISIBLE);

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(LoginActivity.this , "error try again" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                login_btn.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public void forgot_password(View view) {

        Intent i = new Intent(LoginActivity.this , ForgetPassword.class);
        startActivity(i);
    }
}
