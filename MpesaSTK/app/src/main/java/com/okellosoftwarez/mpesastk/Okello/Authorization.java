package com.okellosoftwarez.mpesastk.Okello;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Authorization {

    @GET("base64string")
    Call<List<AccessToken>> getToken();
}
