package alugueis.alugueis.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class StdService {
    public static final int NOT_FOUND = 404;
    public static final int ACCEPTED = 202;

    //private static final String API_BASE_URL = "http://52.67.29.228:8080/";
    private static final String API_BASE_URL = "http://192.168.1.103:8080/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}