package com.example.firebasetoken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("index.php")
    Call<String> getToken(
            @Field("UID") String phone
    );

    @FormUrlEncoded
    @POST("indexVerify.php")
    Call<String> verifytoken(
            @Field("token") String phone
    );

}
