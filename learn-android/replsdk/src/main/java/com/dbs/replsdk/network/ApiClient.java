package com.dbs.replsdk.network;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by deepak on 12/9/18.
 */

public class ApiClient {

    private static final int REQUEST_TIMEOUT = 60;
    private static Retrofit retrofit;


    private ApiClient() {
        throw new RuntimeException("You shall not pass");
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) throw new RuntimeException("Retrofit is not initialized");
        return retrofit;
    }


    public static void init(ApiConfig config) {
        if (retrofit != null) {
            retrofit = retrofit.newBuilder().baseUrl(config.getBaseUrl())
                    .client(createClient(config.getApiKey()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(config.getBaseUrl())
                    .client(createClient(config.getApiKey()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private static OkHttpClient createClient(String apiKey) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json");

                    // Adding Authorization token (API Key)
                    // Requests will be denied without API key
                    if (!TextUtils.isEmpty(apiKey)) {
                        requestBuilder.addHeader("Authorization", apiKey);
                    }
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .hostnameVerifier((hostname, session) -> true)
                .build();

    }

    public static class ApiConfig {

        private String apiKey;
        private String baseUrl;

        public ApiConfig() {
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public ApiConfig withApiKey(String key) {
            this.apiKey = key;
            return ApiConfig.this;
        }

        public ApiConfig withBaseUrl(String url) {
            this.baseUrl = url;
            return ApiConfig.this;
        }
    }


}
