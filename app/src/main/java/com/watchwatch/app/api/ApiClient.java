package com.watchwatch.app.api;

import com.watchwatch.app.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient — Singleton untuk instance Retrofit.
 *
 * Pola Singleton memastikan hanya ada SATU instance Retrofit
 * di seluruh aplikasi, agar tidak boros memori.
 *
 * Cara pakai di mana saja cukup:
 *   ApiService api = ApiClient.getApiService();
 *   api.getLatestPosts(...).enqueue(...);
 */
public class ApiClient {

    private static Retrofit retrofitInstance = null;

    private static Retrofit getInstance() {
        if (retrofitInstance == null) {

            // Logging interceptor: tampilkan detail request & response di Logcat
            // Berguna untuk debug — bisa lihat data JSON yang masuk dari API
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    /** Ambil implementasi ApiService yang siap dipakai */
    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }

    // Prevent instantiation
    private ApiClient() {}
}
