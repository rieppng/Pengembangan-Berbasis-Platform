package com.watchwatch.app.api;

import com.watchwatch.app.model.Category;
import com.watchwatch.app.model.Post;
import com.watchwatch.app.model.WpPage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("posts")
    Call<List<Post>> getLatestPosts(
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query("_embed") int embed
    );

    @GET("posts")
    Call<List<Post>> getPostsByTag(
            @Query("tags") int tagId,
            @Query("per_page") int perPage,
            @Query("_embed") int embed
    );

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("posts")
    Call<List<Post>> getPostsByCategory(
            @Query("categories") int categoryId,
            @Query("per_page") int perPage,
            @Query("page") int page,
            @Query("_embed") int embed
    );

    @GET("posts/{id}")
    Call<Post> getPostDetail(
            @Path("id") int id,
            @Query("_embed") int embed
    );

    /**
     * search_columns=post_title → WordPress hanya mencari di judul post.
     * Tanpa parameter ini, pencarian juga masuk ke konten & excerpt
     * sehingga kata kunci pendek bisa memunculkan hasil yang tidak relevan.
     * WordPress 6.0+ sudah support parameter ini.
     */
    @GET("posts")
    Call<List<Post>> searchPosts(
            @Query("search") String keyword,
            @Query("per_page") int perPage,
            @Query("_embed") int embed,
            @Query("search_columns") String searchColumns
    );

    @GET("pages")
    Call<List<WpPage>> getPageBySlug(
            @Query("slug") String slug
    );
}
