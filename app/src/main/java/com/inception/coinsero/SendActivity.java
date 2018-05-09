package com.inception.coinsero;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SendActivity extends AppCompatActivity {

    private static CustomEditText receipt_address , amount_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        getSupportActionBar().setTitle("Back");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        receipt_address = findViewById(R.id.receipt_address);

        amount_et = findViewById(R.id.amount_et);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }

    public void scan_address(View view) {

        if (ContextCompat.checkSelfPermission(SendActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(SendActivity.this, new String[] {Manifest.permission.CAMERA}, 100);


        }else {

            new IntentIntegrator(this).initiateScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 100)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                new IntentIntegrator(this).initiateScan();


            } else {
                // Permission Denied
                Toast.makeText(SendActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            // handle scan result

           System.out.println(scanResult.getContents());

           receipt_address.setText(scanResult.getContents());
        }
        // else continue with
    }

    public void send_amount(View view) {

        if(receipt_address.getText().toString().isEmpty())
        {
            Toast.makeText(SendActivity.this , "please enter recipient address" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(amount_et.getText().toString().isEmpty())
        {
            Toast.makeText(SendActivity.this , "please enter valid amount" , Toast.LENGTH_SHORT).show();
            return;

        }

        new ConfirmTransactionDialog(SendActivity.this , R.style.translucentDialog , receipt_address.getText().toString() , amount_et.getText().toString()).show();


    }

    public static void clear_fields()
    {
        receipt_address.setText("");
        amount_et.setText("");

    }
}
