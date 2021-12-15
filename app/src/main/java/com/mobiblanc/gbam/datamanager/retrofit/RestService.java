package com.mobiblanc.gbam.datamanager.retrofit;


import com.mobiblanc.gbam.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {

    private static RestService restService;
    private RestEndpoint restEndpoint;

    private RestService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);;
        client.connectTimeout(120, TimeUnit.SECONDS);
        client.readTimeout(120, TimeUnit.SECONDS);
        client.writeTimeout(120, TimeUnit.SECONDS);

        client.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json;version=" + BuildConfig.VERSION_NAME)
                    .addHeader("os", "android")
                    .addHeader("Authorization", ApiUrls.AUTHORIZATION);
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

    public static RestService getInstance() {
        if (restService == null) {
            restService = new RestService();
        }
        return restService;
    }

    public RestEndpoint endpoint() {
        return restEndpoint;
    }
}
