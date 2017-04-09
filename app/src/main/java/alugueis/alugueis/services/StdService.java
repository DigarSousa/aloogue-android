package alugueis.alugueis.services;

import android.content.Context;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import alugueis.alugueis.util.Util;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class StdService {
    public static final int NOT_FOUND = 404;
    public static final int ACCEPTED = 202;
    public static final int CONFLICT = 409;

    //private static final String API_BASE_URL = "http://52.67.29.228:8080/";
    private static final String API_BASE_URL = "https://aloogue.herokuapp.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().
            connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, Context c) throws ConnectException {
        if (!Util.isOnline(c)) {
            throw new ConnectException("Device is nos connected");
        }

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}