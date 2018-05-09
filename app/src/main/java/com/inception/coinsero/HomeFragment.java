package com.inception.coinsero;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RequestQueue requestQueue;

    private RecyclerView transaction_recycler;

    private JSONArray jsonArray;

    private ProgressBar progressBar;

    private CustomTextView send , receive , balance_txt;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        requestQueue = Volley.newRequestQueue(getActivity());


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        balance_txt = view.findViewById(R.id.balance_txt);

        send = view.findViewById(R.id.send);

        receive = view.findViewById(R.id.receive);

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , ReceiveActivity.class));

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , SendActivity.class));
            }
        });


        transaction_recycler = view.findViewById(R.id.transaction_recycler);

        transaction_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        get_balance();

        get_transactions();

        return view;
    }


    public void get_balance() {


        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("username", sp.getString("username", ""));
            jsonObject.put("module", "balance");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.url), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    balance_txt.setText( response.getJSONObject("result").getString("balance") );
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

    public void get_transactions() {


        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);


        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("username", sp.getString("username", ""));
            jsonObject.put("module", "transactions");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.url), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                progressBar.setVisibility(View.GONE);

                try {
                    jsonArray = response.getJSONObject("result").getJSONArray("transactions");

                    Adapter adapter = new Adapter();

                    transaction_recycler.setAdapter(adapter);

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


    private class Adapter extends RecyclerView.Adapter<view_holder> {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_cell, parent, false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);

                holder.address.setText(jsonObject.getString("address"));
                holder.amount.setText(jsonObject.getString("amount"));
                holder.confirmations.setText(jsonObject.getString("confirmations") + " CONFIRMATIONS");

                holder.time.setText(getTime(jsonObject.getLong("timereceived")));

                if(jsonObject.getString("category").equals("receive"))
                {
                    holder.amount.setText("+ "+jsonObject.getString("amount"));

                    holder.amount.setTextColor(getResources().getColor(R.color.green));

                }

                else {

                    holder.amount.setText("- "+jsonObject.getString("amount"));

                    holder.amount.setTextColor(getResources().getColor(R.color.red));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return jsonArray.length();
        }
    }


    private class view_holder extends RecyclerView.ViewHolder {

        private CustomTextView amount, address, confirmations , time;

        public view_holder(View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.amount);

            address = itemView.findViewById(R.id.address);

            confirmations = itemView.findViewById(R.id.confirmations);

            time = itemView.findViewById(R.id.time_txt);
        }
    }


    public String getTime(long unixSeconds)
    {
// convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);

    }

}
