package com.okellosoftwarez.darajastk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

public class MainActivity extends AppCompatActivity {

    private Daraja daraja;
    EditText etPhone;
    Button stkPushBtn;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = findViewById(R.id.editText);
        stkPushBtn = findViewById(R.id.button);

        //For Sandbox Mode
        daraja = Daraja.with("I4T05zcfAXKYRiunkGv5ZRslqKm5zNoP", "I4Sufz8fLAZxT67M", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(MainActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(MainActivity.this, "AccessToken : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(MainActivity.this.getClass().getSimpleName(), error);
            }
        });
        stkPushBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                phoneNumber = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber)) {
                    etPhone.setError("Enter A valid Phone Number");
                    return;
                }

                new AsyncTask<Void, Void, String>(){

                    @Override
                    protected String doInBackground(Void... voids) {
                        initiateStkPush();
                        return null;
                    }
                }.execute();
            }

        });
    }

    private void initiateStkPush() {
        LNMExpress lnmExpress = new LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerPayBillOnline,
                "1",
                phoneNumber,
                "174379",
                phoneNumber,
                "http://mycallbackurl.com/checkout.php",
                "OKELLO",
                "Goods Payment"
        );

        daraja.requestMPESAExpress(lnmExpress, new DarajaListener<LNMResult>() {
            @Override
            public void onResult(@NonNull LNMResult lnmResult) {
                Log.i(MainActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
            }

            @Override
            public void onError(String error) {
                Log.i(MainActivity.this.getClass().getSimpleName(), error);
            }
        });
    }
}
