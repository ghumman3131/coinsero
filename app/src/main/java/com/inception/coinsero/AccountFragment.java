package com.inception.coinsero;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private LinearLayout logout_layout , change_passsword_layout ;


    private CustomTextView email;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);


        logout_layout = view.findViewById(R.id.logout_layout);

        change_passsword_layout = view.findViewById(R.id.change_password_layout);

        email = view.findViewById(R.id.user_email);

        SharedPreferences sp = getActivity().getSharedPreferences("user_info" , Context.MODE_PRIVATE);



        email.setText(sp.getString("username" , ""));

        change_passsword_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity() , ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp = getActivity().getSharedPreferences("user_info" , Context.MODE_PRIVATE).edit();

                sp.putString("username" , "").commit();

                Intent i = new Intent(getActivity() , LoginActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        return view;

    }

}
