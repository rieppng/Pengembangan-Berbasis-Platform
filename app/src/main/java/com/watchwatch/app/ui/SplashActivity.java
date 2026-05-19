package com.watchwatch.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.watchwatch.app.api.ApiClient;
import com.watchwatch.app.databinding.ActivitySplashBinding;
import com.watchwatch.app.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    // Minimum waktu splash ditampilkan sebelum pindah ke MainActivity
    private static final int SPLASH_DURATION = 2000; // 2 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkApiAndNavigate();
    }

    private void checkApiAndNavigate() {
        // Panggil endpoint ringan di background untuk verifikasi koneksi API.
        // Request hanya 1 post (per_page=1) agar tidak boros bandwidth.
        ApiClient.getApiService()
                .getLatestPosts(1, 1, 1)
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        // API berhasil diakses.
                        // Tidak perlu action di sini — navigasi sudah diatur oleh Handler.
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        // Tidak ada koneksi atau API bermasalah.
                        // Tetap navigasi ke MainActivity — biarkan Fragment
                        // masing-masing yang menampilkan pesan error.
                    }
                });

        // Navigasi ke MainActivity setelah SPLASH_DURATION selesai.
        // Ini berjalan paralel dengan API check di atas.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish(); // tutup SplashActivity agar user tidak bisa Back ke sini
        }, SPLASH_DURATION);
    }
}
