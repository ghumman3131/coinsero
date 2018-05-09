package com.inception.coinsero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText current_password , new_password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        current_password = findViewById(R.id.current_password_et);

        new_password = findViewById(R.id.new_password_et);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }

    public void change_password(View view) {

        final Button login_btn = (Button) view;

        login_btn.setText("UPDATING..");
        login_btn.setEnabled(false);



        if(current_password.getText().toString().length() < 1)
        {
            Toast.makeText(ChangePasswordActivity.this , "please enter current password" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(new_password.getText().toString().length() < 4)
        {
            Toast.makeText(ChangePasswordActivity.this , "password too short" , Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject jsonObject = new JSONObject();

        SharedPreferences sp = getSharedPreferences("user_info" , Context.MODE_PRIVATE);


        try {
            jsonObject.put("module" , "change password");
            jsonObject.put("current_password" , current_password.getText().toString());
            jsonObject.put("new_password" , new_password.getText().toString());
            jsonObject.put("username" , sp.getString("username" , ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getString(R.string.url), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("result").equals("done"))
                    {
                        Toast.makeText(ChangePasswordActivity.this , "password updated , please login again" , Toast.LENGTH_SHORT).show();


                        SharedPreferences.Editor sp = getSharedPreferences("user_info" , Context.MODE_PRIVATE).edit();

                        sp.putString("username" , "").commit();

                        Intent i = new Intent(ChangePasswordActivity.this , LoginActivity.class);
                        startActivity(i);

                        finishAffinity();

                    }

                    else {

                        login_btn.setText("UPDATE");
                        login_btn.setEnabled(true);
                        Toast.makeText(ChangePasswordActivity.this , "invalid current password" , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);

                login_btn.setText("UPDATE");
                login_btn.setEnabled(true);

            }
        });

        Volley.newRequestQueue(ChangePasswordActivity.this).add(jsonObjectRequest);


    }
}
