package com.okellosoftwarez.paypalmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int PAYPAL_REQUEST = 1717 ;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AX8MOQLVxtSv1kHesSObR9NeCApvgPE63tiokt0HwS3w68nP0kDB9jBxpvNGgNOua95DHFiLQXcDr2b0");
//    String clientID = "AX8MOQLVxtSv1kHesSObR9NeCApvgPE63tiokt0HwS3w68nP0kDB9jBxpvNGgNOua95DHFiLQXcDr2b0";
//    String clientSecret = "EJV7BVrDZ5YNTUfKrmXK_dhm3c02dJqbpJA2WTbMbWGAf635baWTmfD3loFn9oYAsoE-BClhLQdJUr-z";
    String amount = "";
    private EditText etAmount;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Start Pay pal Services
        Intent serviceIntent = new Intent(MainActivity.this, PayPalService.class);
        serviceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(serviceIntent);

//        clientID AX8MOQLVxtSv1kHesSObR9NeCApvgPE63tiokt0HwS3w68nP0kDB9jBxpvNGgNOua95DHFiLQXcDr2b0
        etAmount = findViewById(R.id.editText);
        Button payBtn = findViewById(R.id.button);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();

//                PAYMENT_INTENT_SALE it will cause payment to be completed immediately
//                PAYMENT_INTENT_AUTHORIZE it will authorize payment and capture funds later
//                PAYMENT_INTENT_ORDER create a payment for authorization and capture later via calls from ur server
            }

//            @Override
//            protected void onActivityResult(int requestCode, int resultCode, Intent data){
//                if (resultCode == Activity.RESULT_OK){
//                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                    if (confirmation != null){
//                        try {
//                            Log.i(TAG, "onActivityResult: " + confirmation.toJSONObject().toString(4));
//                            TODO : Send a verification Message of success to your server
//                        }
//                        catch (JSONException e){
//                            Log.e(TAG, "onActivityResult: Unlikely Failure::  ", e );
//                        }
//                    }
//                }
//                else if (resultCode == Activity.RESULT_CANCELED){
//                    Log.i(TAG, "onActivityResult: The Buyer Has Cancelled The Order");
//                }
//                else {
//                    Log.i(TAG, "onActivityResult: Invalid Payment or PayPalConfiguration was Submitted... ");
//                }
//            }
        });


    }

    private void processPayment() {

        amount =  etAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Product " +
                "Purchase ", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetails.class)
                        .putExtra("paymentDetails", paymentDetails)
                                .putExtra("amount", amount)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalid Request...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
