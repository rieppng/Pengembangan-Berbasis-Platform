package com.watchwatch.app.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * BookmarkEntity — merepresentasikan satu baris di tabel "bookmarks".
 *
 * Room akan otomatis membuat tabel SQLite berdasarkan class ini.
 * Kita menyimpan data yang diperlukan untuk tampilan list bookmark
 * agar tidak perlu hit API lagi saat membuka halaman Bookmark.
 */
@Entity(tableName = "bookmarks")
public class BookmarkEntity {

    @PrimaryKey
    public int id;          // ID post dari WordPress (juga primary key tabel)

    public String title;
    public String imageUrl;
    public String categoryName;
    public String date;
    public long savedAt;    // timestamp saat disimpan, untuk sorting terbaru di atas
}
