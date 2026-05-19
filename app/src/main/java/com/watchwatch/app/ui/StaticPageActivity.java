package com.watchwatch.app.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.watchwatch.app.api.ApiClient;
import com.watchwatch.app.databinding.ActivityStaticPageBinding;
import com.watchwatch.app.model.WpPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * StaticPageActivity — menampilkan halaman statis dari WordPress
 * (About, Disclaimer, Privacy Policy, Kontak).
 *
 * Menerima slug dan judul via Intent, lalu fetch konten dari API,
 * dan render HTML-nya dengan Html.fromHtml() — tanpa WebView.
 */
public class StaticPageActivity extends AppCompatActivity {

    public static final String EXTRA_SLUG  = "extra_slug";
    public static final String EXTRA_TITLE = "extra_title";

    private ActivityStaticPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaticPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String slug  = getIntent().getStringExtra(EXTRA_SLUG);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        setupToolbar(title);
        loadPage(slug);
    }

    private void setupToolbar(String title) {
        binding.toolbar.setTitle(title != null ? title : "");
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadPage(String slug) {
        if (slug == null || slug.isEmpty()) {
            Toast.makeText(this, "Halaman tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.progressBar.setVisibility(View.VISIBLE);

        ApiClient.getApiService().getPageBySlug(slug).enqueue(new Callback<List<WpPage>>() {
            @Override
            public void onResponse(Call<List<WpPage>> call, Response<List<WpPage>> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()
                        && response.body() != null
                        && !response.body().isEmpty()) {

                    WpPage page = response.body().get(0);
                    displayPage(page);

                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<WpPage>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                showError();
            }
        });
    }

    private void displayPage(WpPage page) {
        String rawHtml = page.getContentRendered();

        // Bersihkan tag yang tidak dirender Html.fromHtml
        String cleanHtml = rawHtml
                .replaceAll("<img[^>]*>", "")
                .replaceAll("(?s)<!--.*?-->", "");

        binding.tvContent.setText(
                Html.fromHtml(cleanHtml, Html.FROM_HTML_MODE_COMPACT));
    }

    private void showError() {
        Toast.makeText(this, "Gagal memuat halaman. Periksa koneksi.", Toast.LENGTH_LONG).show();
    }
}
