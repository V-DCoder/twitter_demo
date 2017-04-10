package com.bms.twitterapidemo.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roshan on 05/04/17.
 */

public class BearerToken {

    @SerializedName("token_type")
    private String Token_type;
    @SerializedName("access_token")
    private String Access_token;


    public BearerToken(String token_type, String access_token) {
        Token_type = token_type;
        Access_token = access_token;
    }


    public String getAccess_token() {
        return Access_token;
    }

    public void setAccess_token(String access_token) {
        Access_token = access_token;
    }

    public String getToken_type() {
        return Token_type;
    }

    public void setToken_type(String token_type) {
        Token_type = token_type;
    }
}
