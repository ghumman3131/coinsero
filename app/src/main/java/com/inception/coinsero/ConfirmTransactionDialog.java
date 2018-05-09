package com.inception.coinsero;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

/**
 * Created by charanghumman on 23/03/18.
 */

public class ConfirmTransactionDialog extends Dialog {

    private CustomTextView transaction_details , send_btn , cancel_btn ;

    private String address , amount ;

    private Context context ;

    public ConfirmTransactionDialog(@NonNull Context context, int themeResId , String address , String amount) {
        super(context, themeResId);


        this.amount = amount ;
        this.address = address;

        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.confirm_dialog_layout);


        transaction_details = findViewById(R.id.transaction_details);

        send_btn = findViewById(R.id.send_btn);

        cancel_btn = findViewById(R.id.cancel_btn);

        transaction_details.setText("You are sending "+amount+" CSR to address "+address+" .");

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendActivity.clear_fields();
                Toast.makeText(context , "amount sent" , Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}
