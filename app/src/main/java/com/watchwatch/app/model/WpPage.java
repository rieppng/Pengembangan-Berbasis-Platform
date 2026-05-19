package com.watchwatch.app.model;

/**
 * Model untuk halaman statis WordPress (About, Disclaimer, dll).
 * Endpoint: GET /wp-json/wp/v2/pages?slug=about
 *
 * Berbeda dari Post, halaman tidak punya kategori/tag.
 * Kita hanya butuh judul dan konten untuk ditampilkan di Settings.
 */
public class WpPage {

    private int id;
    private String slug;
    private Title title;
    private Content content;

    public int getId()      { return id; }
    public String getSlug() { return slug; }

    public String getTitleRendered() {
        return (title != null) ? title.rendered : "";
    }

    public String getContentRendered() {
        return (content != null) ? content.rendered : "";
    }

    public static class Title {
        public String rendered;
    }

    public static class Content {
        public String rendered;
    }
}
