package com.watchwatch.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.watchwatch.app.api.ApiClient;
import com.watchwatch.app.api.ApiService;
import com.watchwatch.app.database.AppDatabase;
import com.watchwatch.app.database.BookmarkDao;
import com.watchwatch.app.database.BookmarkEntity;
import com.watchwatch.app.model.Category;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.model.WpPage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Callback;

public class PostRepository {

    private final ApiService api;

    public PostRepository() {
        api = ApiClient.getApiService();
    }

    public void getLatestPosts(int perPage, int page, Callback<List<Post>> callback) {
        api.getLatestPosts(perPage, page, 1).enqueue(callback);
    }

    public void getPostsByTag(int tagId, int perPage, Callback<List<Post>> callback) {
        api.getPostsByTag(tagId, perPage, 1).enqueue(callback);
    }

    public void getCategories(Callback<List<Category>> callback) {
        api.getCategories().enqueue(callback);
    }

    public void getPostsByCategory(int categoryId, int perPage, int page, Callback<List<Post>> callback) {
        api.getPostsByCategory(categoryId, perPage, page, 1).enqueue(callback);
    }

    public void getPostDetail(int id, Callback<Post> callback) {
        api.getPostDetail(id, 1).enqueue(callback);
    }

    public void searchPosts(String keyword, int perPage, Callback<List<Post>> callback) {
        api.searchPosts(keyword, perPage, 1, "post_title").enqueue(callback);
    }

    public void getPageBySlug(String slug, Callback<List<WpPage>> callback) {
        api.getPageBySlug(slug).enqueue(callback);
    }
}
