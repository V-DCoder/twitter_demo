package com.bms.twitterapidemo.network;

import com.bms.twitterapidemo.mvp.model.SearchResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by roshan on 05/04/17.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/oauth2/token")
    Call<BearerToken> getBearrerTokenAPI(@Header ("Authorization") String sec, @Header ("Content-Type") String contentType, @Header("Content-Length")int length , @Field("grant_type") String body111);



    @GET("/1.1/search/tweets.json")
    Call<SearchResult> getTwitForText(@Header("Authorization")String auth, @Query("q") String queryParam);

}
