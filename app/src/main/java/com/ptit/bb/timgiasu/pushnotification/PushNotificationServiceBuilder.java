package com.ptit.bb.timgiasu.pushnotification;

import com.ptit.bb.timgiasu.data.ImgurlService;
import com.ptit.bb.timgiasu.data.NullOnEmptyConverterFactory;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PushNotificationServiceBuilder {

    private static Retrofit retrofit;
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl url = original.url().newBuilder()
                    .build();
            Request request = original.newBuilder()
                    .addHeader("Authorization", "KEY=AAAACJseUdg:APA91bGWERJwVyCgvWcIsUunPx5fDAz1TGsJU5GdavQkYUXrVC43nknenFlU5EPdGIqLb2T97WVYiREA5BuuaJD3Z8AkmUzkW-cORqe01UIX_3H3BnvGN--CIBRrnttvETft8fJ7NHEBSfBzMHWJPnGny9nYGZAC7A")
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();
            return chain.proceed(request);
        }
    }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    public static PushNoticationService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/fcm/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .build();
        }

        return retrofit.create(PushNoticationService.class);
    }
}
