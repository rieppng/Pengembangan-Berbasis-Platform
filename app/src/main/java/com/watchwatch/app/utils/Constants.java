package com.watchwatch.app.utils;

public class Constants {

    // Base URL WordPress REST API — JANGAN hapus slash di akhir
    public static final String BASE_URL = "https://watchwatch.biz.id/wp-json/wp/v2/";

    // ID tag "trending" di WordPress.
    // Cara cek: buka https://watchwatch.biz.id/wp-json/wp/v2/tags di browser,
    // cari objek dengan "slug":"trending", lalu ambil nilai "id"-nya.
    public static final int TAG_TRENDING_ID = 13; // ← ganti dengan ID yang sebenarnya

    // Jumlah post yang ditampilkan per request
    public static final int POSTS_PER_PAGE = 10;
    public static final int HOME_POSTS_LIMIT = 5; // untuk section Latest & Trending di Home

    // Key untuk mengirim data antar Activity via Intent
    public static final String EXTRA_POST_ID = "extra_post_id";
    public static final String EXTRA_POST_TITLE = "extra_post_title";

    // Slug halaman statis WordPress (sesuaikan jika slug di web berbeda)
    public static final String SLUG_ABOUT       = "about";
    public static final String SLUG_DISCLAIMER  = "disclaimer";
    public static final String SLUG_PRIVACY     = "privacy-policy";
    public static final String SLUG_CONTACT     = "contact";

    private Constants() {}
}
