package com.inception.coinsero;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment  implements View.OnClickListener{


    private RequestQueue requestQueue ;

    private RecyclerView transaction_recycler ;

    private JSONArray jsonArray ;

    private ProgressBar progressBar;

    private TextView no_transactions ;

    private CustomTextView all_transactions , today_transactions , monthly_transactions ;

    private  LinearLayout picker_layout ;


    public TransactionFragment()
    {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_transaction, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        transaction_recycler = view.findViewById(R.id.transaction_recycler);

        progressBar = view.findViewById(R.id.progress_bar);

        all_transactions = view.findViewById(R.id.all_transactions);

        today_transactions = view.findViewById(R.id.today_transactions);

        monthly_transactions = view.findViewById(R.id.monthly_transactions);

        no_transactions = view.findViewById(R.id.no_transactions);

        transaction_recycler.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL , false));

        all_transactions.setOnClickListener(this);
        today_transactions.setOnClickListener(this);
        monthly_transactions.setOnClickListener(this);

        picker_layout = view.findViewById(R.id.picker_layout);


//
        final NumberPicker aNumberPicker = new NumberPicker(getContext());
        aNumberPicker.setMaxValue(2118);
        aNumberPicker.setMinValue(2017);
        aNumberPicker.setScaleX(1.5f);
        aNumberPicker.setScaleY(1.5f);
//
        final NumberPicker aNumberPickerA = new NumberPicker(getContext());
        aNumberPickerA.setMaxValue(12);
        aNumberPickerA.setMinValue(1);
        aNumberPickerA.setScaleX(1.5f);
        aNumberPickerA.setScaleY(1.5f);

        final String[] months_arr = new String[] { "JANUARY", "FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER" , "DECEMBER"} ;

        aNumberPickerA.setDisplayedValues(months_arr);
//

//
        LinearLayout.LayoutParams numPicerParams = new LinearLayout.LayoutParams(0, 160);
        numPicerParams.weight = 1;
//
        LinearLayout.LayoutParams qPicerParams = new LinearLayout.LayoutParams(0 , 160);
        qPicerParams.weight = 1;
//

        picker_layout.addView(aNumberPickerA,qPicerParams);

        picker_layout.addView(aNumberPicker,numPicerParams);

        aNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                String sel_year = String.valueOf(newVal);

                String sel_month = months_arr[aNumberPickerA.getValue()-1];

                get_month_transactions(sel_year , sel_month);
            }
        });


        aNumberPickerA.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                String sel_year = String.valueOf(aNumberPicker.getValue());

                String sel_month = months_arr[newVal-1];

                get_month_transactions(sel_year , sel_month);
            }
        });

        get_transactions();

        return view;
    }

    public void get_transactions()
    {


        SharedPreferences sp = getActivity().getSharedPreferences("user_info" , Context.MODE_PRIVATE);


        final JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("username" , sp.getString("username",""));
            jsonObject.put("module" , "transactions");
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

                    if(jsonArray.length() == 0)
                    {
                        no_transactions.setVisibility(View.VISIBLE);
                    }

                    else {
                        no_transactions.setVisibility(View.GONE);

                    }

                    Adapter adapter = new Adapter(jsonArray);

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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.all_transactions:

                picker_layout.setVisibility(View.GONE);

                today_transactions.setBackgroundResource(R.drawable.primary_stroke_rectangle);
                monthly_transactions.setBackgroundResource(0);
                all_transactions.setBackgroundResource(R.drawable.left_rounded_rectangle);

                all_transactions.setTextColor(getResources().getColor(R.color.white));

                monthly_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                today_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                get_transactions();

                break;

            case R.id.today_transactions:

                picker_layout.setVisibility(View.GONE);

                today_transactions.setBackgroundResource(R.drawable.primary_rectangle);

                all_transactions.setBackgroundResource(0);

                monthly_transactions.setBackgroundResource(0);

                all_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                monthly_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                today_transactions.setTextColor(getResources().getColor(R.color.white));

                get_today_transactions();

                break;

            case R.id.monthly_transactions:

                picker_layout.setVisibility(View.VISIBLE);

                today_transactions.setBackgroundResource(R.drawable.primary_stroke_rectangle);
                monthly_transactions.setBackgroundResource(R.drawable.right_rounded_rectangle);
                all_transactions.setBackgroundResource(0);

                all_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                monthly_transactions.setTextColor(getResources().getColor(R.color.white));

                today_transactions.setTextColor(getResources().getColor(R.color.d_grey));

                get_month_transactions("2017" , "January");

                break;

        }
    }


    private class Adapter extends RecyclerView.Adapter<view_holder>
    {
        private JSONArray jarr ;

        public Adapter(JSONArray jsonArray)
        {
            jarr = jsonArray;
        }

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(position);

                holder.address.setText(jsonObject.getString("address"));
                holder.confirmations.setText(jsonObject.getString("confirmations")+" CONFIRMATIONS");


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
            return jarr.length();
        }
    }



    private class view_holder extends RecyclerView.ViewHolder
    {

        private CustomTextView amount , address , confirmations , time;

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

    public String getTime2(long unixSeconds)
    {
// convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM yyyy");
// give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);

    }

    private void get_today_transactions()
    {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        JSONArray today_array = new JSONArray();

        for(int i = 0 ; i < jsonArray.length() ; i ++)
        {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(getTime(jsonObject.getLong("timereceived")).split(" ")[0] == formattedDate.split("-")[0])
                {
                    today_array.put(jsonObject);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        if(today_array.length() == 0)
        {
            no_transactions.setVisibility(View.VISIBLE);
        }

        else {
            no_transactions.setVisibility(View.GONE);

        }


        Adapter adapter = new Adapter(today_array);

        transaction_recycler.setAdapter(adapter);



    }

    private void get_month_transactions(String sel_year , String sel_month)
    {
        System.out.println("selected month and year is ************* "+sel_year+"   month is   "+sel_month);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        JSONArray month_array = new JSONArray();

        for(int i = 0 ; i < jsonArray.length() ; i ++)
        {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                System.out.println("transaction month and year is ************* "+getTime2(jsonObject.getLong("timereceived")).split(" ")[2].toLowerCase()+"   month is   "+getTime2(jsonObject.getLong("timereceived")).split(" ")[1].toLowerCase());


                if(getTime2(jsonObject.getLong("timereceived")).split(" ")[1].toLowerCase().equals(sel_month.toLowerCase()) && getTime2(jsonObject.getLong("timereceived")).split(" ")[2].toLowerCase().equals(sel_year.toLowerCase()))
                {
                    month_array.put(jsonObject);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(month_array.length() == 0)
        {
            no_transactions.setVisibility(View.VISIBLE);
        }

        else {
            no_transactions.setVisibility(View.GONE);

        }


        Adapter adapter = new Adapter(month_array);

        transaction_recycler.setAdapter(adapter);



    }


}
