package com.watchwatch.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.watchwatch.app.database.AppDatabase;
import com.watchwatch.app.database.BookmarkDao;
import com.watchwatch.app.database.BookmarkEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookmarkRepository {

    private final BookmarkDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public BookmarkRepository(Context context) {
        dao = AppDatabase.getInstance(context).bookmarkDao();
    }

    public LiveData<List<BookmarkEntity>> getAllBookmarks() {
        return dao.getAll();
    }

    public LiveData<Integer> isBookmarked(int postId) {
        return dao.isBookmarkedLive(postId);
    }

    public void insert(BookmarkEntity entity) {
        executor.execute(() -> dao.insert(entity));
    }

    public void deleteById(int postId) {
        executor.execute(() -> dao.deleteById(postId));
    }
}
