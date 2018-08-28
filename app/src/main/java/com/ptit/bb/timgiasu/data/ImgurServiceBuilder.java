package com.ptit.bb.timgiasu.data;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImgurServiceBuilder {
    private static Retrofit retrofit;
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl url = original.url().newBuilder()
                    .build();
            Request request = original.newBuilder()
                    .addHeader("Authorization", "Client-ID 00c2a2fb0d3a76b")
                    .url(url)
                    .build();
            return chain.proceed(request);
        }
    }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    public static ImgurlService getImgurAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.imgur.com/3/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .build();
        }

        return retrofit.create(ImgurlService.class);
    }

}
