package com.watchwatch.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkEntity bookmark);

    @Query("DELETE FROM bookmarks WHERE id = :postId")
    void deleteById(int postId);

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    LiveData<List<BookmarkEntity>> getAll();

    @Query("SELECT COUNT(*) FROM bookmarks WHERE id = :postId")
    LiveData<Integer> isBookmarkedLive(int postId);
}
