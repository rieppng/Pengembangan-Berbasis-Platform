package com.watchwatch.app.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "bookmarks")
public class BookmarkEntity {

    @PrimaryKey
    public int id;

    public String title;
    public String imageUrl;
    public String categoryName;
    public String date;
    public long savedAt;
}
