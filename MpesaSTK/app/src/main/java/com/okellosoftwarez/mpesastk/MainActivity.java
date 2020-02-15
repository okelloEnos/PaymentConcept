package com.okellosoftwarez.mpesastk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okellosoftwarez.mpesastk.Okello.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String auth;
    private String stringToken;

    //    public static Retrofit retrofitAuth = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button payBtn = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.txr);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // Use base64 to encode the consumer key and secret.
                    String app_key = "I4T05zcfAXKYRiunkGv5ZRslqKm5zNoP";
                    String app_secret = "I4Sufz8fLAZxT67M";
                    String appKeySecret = app_key + ":" + app_secret;
                    byte[] bytes = appKeySecret.getBytes("ISO-8859-1");

                    auth = Base64.encodeToString(bytes, Base64.NO_WRAP);

                    textView.setText(auth);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                            .get()
                            .addHeader("authorization", "Basic " + auth)
                            .addHeader("cache-control", "no-cache")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            textView.setText(e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (!response.isSuccessful()){
                                textView.setText("Code : " + response.code());
                            }
                            else {
//                                Use the Response
                                final String stringJsonToken = response.body().string();

                                new Handler(Looper.getMainLooper()).post(new Runnable(){
                                    @Override
                                    public void run() {
                                        try {
                                            JSONObject obj = new JSONObject(stringJsonToken);

                                            stringToken = obj.getString("access_token");
                                            textView.setText(stringToken);
                                        }
                                        catch (JSONException js){
                                            js.getMessage();
                                        }
                                    }
                                });

//                                OkHttpClient clientReg = new OkHttpClient();
//
//                                MediaType mediaType = MediaType.parse("application/json");
//                                RequestBody body = RequestBody.create(mediaType, "{\"ShortCode\":\"ShortCode\"," +
//                                       "\"ResponseType\":\"ResponseType\"," +
//                                        "\"ConfirmationURL\":\"http://ip_address:port/confirmation\"," +
//                                        "\"ValidationURL\":\"http://ip_address:port/validation_url\"}");
//
//                                Request request = new Request.Builder()
//                                        .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl")
//                                        .post(body)
//                                        .addHeader("authorization", "Bearer" + stringToken)
//                                        .addHeader("content-type", "application/json")
//                                        .build();
//
//                                Response responseReg = clientReg.newCall(request).execute();



                                OkHttpClient clientRun = new OkHttpClient();

                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody bodyRun = RequestBody.create(mediaType, "{\"ShortCode\":\"600284 \"," +
                                        "\"CommandID\":\"CustomerPayBillOnline\"," +
                                        "\"Amount\":\"10 \"," +
                                        "\"Msisdn\":\" 25416229563\"," +
                                        "\"BillRefNumber\":\" \" }");
                                Request requestRun = new Request.Builder()
                                        .url("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate")
                                        .post(bodyRun)
                                        .addHeader("authorization", "Bearer" + stringToken)
                                        .addHeader("content-type", "application/json")
                                        .build();

//                                Response responseRun = clientRun.newCall(request).execute();
                                clientRun.newCall(requestRun).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {

                                        if (!response.isSuccessful()){
                                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MainActivity.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MainActivity.this, "Success...", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    }
                                });
                            }

                        }
                    });
                }
                catch (UnsupportedEncodingException us){
                    us.printStackTrace();
                }
            }
        });

    }

}
