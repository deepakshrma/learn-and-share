package com.dbs.replsdk.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dbs.replsdk.R;
import com.dbs.replsdk.network.ApiClient;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiClient.ApiConfig apiConfig =
                // default config
                new ApiClient.ApiConfig().withBaseUrl("https://10.93.131.13/kai/api/v2/capi/").withApiKey("");
//                new ApiClient.ApiConfig().withBaseUrl(Constants.KASISTO_VPA_URL).withApiKey("");
        ApiClient.init(apiConfig);

        setContentView(R.layout.activity_chat_container);
    }
}
