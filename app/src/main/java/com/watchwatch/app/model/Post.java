package com.watchwatch.app.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Model untuk satu artikel/post dari WordPress REST API.
 *
 * Endpoint: GET /wp-json/wp/v2/posts
 * Dengan parameter ?_embed=1, WordPress menyertakan data gambar
 * dan kategori langsung dalam satu response — tanpa request tambahan.
 */
public class Post {

    private int id;

    // Format dari API: "2024-01-15T10:30:00" (ISO 8601)
    private String date;

    // Slug adalah versi URL dari judul, misal: "review-casio-g-shock"
    private String slug;

    // URL lengkap artikel di website
    private String link;

    @SerializedName("featured_media")
    private int featuredMedia;

    private List<Integer> categories;
    private List<Integer> tags;

    private Title title;
    private Content content;
    private Excerpt excerpt;

    // Data embed: gambar & kategori dikirim sekaligus saat pakai ?_embed=1
    @SerializedName("_embedded")
    private Embedded embedded;

    // ── Getters ────────────────────────────────────────────────────────────

    public int getId()               { return id; }
    public String getDate()          { return date; }
    public String getSlug()          { return slug; }
    public String getLink()          { return link; }
    public int getFeaturedMedia()    { return featuredMedia; }
    public List<Integer> getCategories() { return categories; }
    public List<Integer> getTags()   { return tags; }
    public Embedded getEmbedded()    { return embedded; }

    // ── Helper Methods ─────────────────────────────────────────────────────

    public String getTitleRendered() {
        return (title != null) ? title.rendered : "";
    }

    public String getContentRendered() {
        return (content != null) ? content.rendered : "";
    }

    public String getExcerptRendered() {
        return (excerpt != null) ? excerpt.rendered : "";
    }

    /** Ambil URL gambar utama dari data embedded. Return null jika tidak ada. */
    public String getFeaturedImageUrl() {
        if (embedded != null
                && embedded.featuredmedia != null
                && !embedded.featuredmedia.isEmpty()) {
            return embedded.featuredmedia.get(0).sourceUrl;
        }
        return null;
    }

    /** Ambil nama kategori pertama dari data embedded. */
    public String getCategoryName() {
        if (embedded != null
                && embedded.terms != null
                && !embedded.terms.isEmpty()
                && !embedded.terms.get(0).isEmpty()) {
            return embedded.terms.get(0).get(0).name;
        }
        return "";
    }

    // ── Nested Classes ─────────────────────────────────────────────────────

    public static class Title {
        public String rendered;
    }

    public static class Content {
        public String rendered;
    }

    public static class Excerpt {
        public String rendered;
    }

    public static class Embedded {

        // wp:featuredmedia → array berisi satu objek gambar utama
        @SerializedName("wp:featuredmedia")
        public List<FeaturedMedia> featuredmedia;

        // wp:term → [0] = array kategori, [1] = array tag
        @SerializedName("wp:term")
        public List<List<Term>> terms;
    }

    public static class FeaturedMedia {
        @SerializedName("source_url")
        public String sourceUrl;
    }

    public static class Term {
        public int id;
        public String name;
        public String slug;
        public String taxonomy; // "category" atau "post_tag"
    }
}
