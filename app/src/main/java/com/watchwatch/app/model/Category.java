package com.watchwatch.app.model;

/**
 * Model untuk satu kategori dari WordPress REST API.
 * Endpoint: GET /wp-json/wp/v2/categories
 *
 * Contoh response:
 * { "id": 3, "name": "Analog", "slug": "analog", "count": 12 }
 */
public class Category {

    private int id;
    private String name;   // nama tampilan, misal "Analog"
    private String slug;   // versi URL, misal "analog"
    private int count;     // jumlah post dalam kategori ini

    public int getId()      { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public int getCount()   { return count; }
}
