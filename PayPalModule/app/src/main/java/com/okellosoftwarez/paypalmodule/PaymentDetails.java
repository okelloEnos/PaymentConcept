package com.okellosoftwarez.paypalmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {
TextView txtId, txtAmount, txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);

//        Get Intent
        Intent receivedIntent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(receivedIntent.getStringExtra("paymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), receivedIntent.getStringExtra("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String amount) {
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(" $ " + amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
