package com.mobiblanc.baridal_maghrib.datamanager.retrofit;


import com.mobiblanc.baridal_maghrib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {

    private RestEndpoint restEndpoint;
    private static RestService restService;

    public static RestService getInstance() {
        if (restService == null) {
            restService = new RestService();
        }
        return restService;
    }

    private RestService() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        client.writeTimeout(30, TimeUnit.SECONDS);

        client.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json;version=" + BuildConfig.VERSION_NAME)
                    .addHeader("os", "android");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASEURL)
                .client(client.build())
                .build();
        restEndpoint = retrofit.create(RestEndpoint.class);
    }

    public RestEndpoint endpoint() {
        return restEndpoint;
    }
}
