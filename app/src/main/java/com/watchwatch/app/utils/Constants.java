package com.watchwatch.app.utils;

public class Constants {

    // Base URL WordPress REST API
    public static final String BASE_URL = "https://watchwatch.biz.id/wp-json/wp/v2/";

    public static final int TAG_TRENDING_ID = 13;

    // Jumlah post yang ditampilkan per request
    public static final int POSTS_PER_PAGE = 10;
    public static final int HOME_POSTS_LIMIT = 5; // untuk section Latest & Trending di Home

    public static final String EXTRA_POST_ID = "extra_post_id";
    public static final String EXTRA_POST_TITLE = "extra_post_title";

    public static final String SLUG_ABOUT       = "about";
    public static final String SLUG_DISCLAIMER  = "disclaimer";
    public static final String SLUG_PRIVACY     = "privacy-policy";
    public static final String SLUG_CONTACT     = "contact";

    private Constants() {}
}
