package com.watchwatch.app.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Post {

    private int id;

    private String date;

    private String slug;

    private String link;

    @SerializedName("featured_media")
    private int featuredMedia;

    private List<Integer> categories;
    private List<Integer> tags;

    private Title title;
    private Content content;
    private Excerpt excerpt;

    @SerializedName("_embedded")
    private Embedded embedded;


    public int getId()               { return id; }
    public String getDate()          { return date; }
    public String getSlug()          { return slug; }
    public String getLink()          { return link; }
    public int getFeaturedMedia()    { return featuredMedia; }
    public List<Integer> getCategories() { return categories; }
    public List<Integer> getTags()   { return tags; }
    public Embedded getEmbedded()    { return embedded; }

    public String getTitleRendered() {
        return (title != null) ? title.rendered : "";
    }

    public String getContentRendered() {
        return (content != null) ? content.rendered : "";
    }

    public String getExcerptRendered() {
        return (excerpt != null) ? excerpt.rendered : "";
    }

    public String getFeaturedImageUrl() {
        if (embedded != null
                && embedded.featuredmedia != null
                && !embedded.featuredmedia.isEmpty()) {
            return embedded.featuredmedia.get(0).sourceUrl;
        }
        return null;
    }

    public String getCategoryName() {
        if (embedded != null
                && embedded.terms != null
                && !embedded.terms.isEmpty()
                && !embedded.terms.get(0).isEmpty()) {
            return embedded.terms.get(0).get(0).name;
        }
        return "";
    }

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

        @SerializedName("wp:featuredmedia")
        public List<FeaturedMedia> featuredmedia;

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
        public String taxonomy;
    }
}
