package com.bms.twitterapidemo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by roshan on 05/04/17.
 */

public class NetworkManager {



    public Retrofit getRetrofitClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(interceptor);

        return new Retrofit.Builder().baseUrl(NetworkConstants.BaseURL).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();

    }

    public ApiInterface getRetrofitAPIClient()
    {
        return getRetrofitClient().create(ApiInterface.class);
    }



}
