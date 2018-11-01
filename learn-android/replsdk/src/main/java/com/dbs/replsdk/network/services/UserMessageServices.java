package com.dbs.replsdk.network.services;

import com.dbs.replsdk.model.ChatResponse;
import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.model.UserMessageRequest;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by deepak on 14/9/18.
 */

public interface UserMessageServices {
    @POST("user_message")
    Single<UserMessageRequest> postValidateCvv(@Body UserMessageRequest data);

    @Headers("Content-Type: application/json")
    @POST("event")
    Call<ChatResponse> requestStartSession(@Body Request request);

    @Headers("Content-Type: application/json")
    @POST("user_message")
    Call<ChatResponse> sendUserMessage(@Body Request request);
}
