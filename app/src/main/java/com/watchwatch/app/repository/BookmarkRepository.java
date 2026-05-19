package com.watchwatch.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.watchwatch.app.database.AppDatabase;
import com.watchwatch.app.database.BookmarkDao;
import com.watchwatch.app.database.BookmarkEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BookmarkRepository — lapisan antara ViewModel dan BookmarkDao.
 *
 * Room TIDAK boleh diakses dari main thread untuk operasi write
 * (insert, delete). Kita pakai ExecutorService untuk menjalankan
 * operasi tersebut di background thread secara aman.
 *
 * Read via LiveData sudah async secara otomatis — tidak perlu Executor.
 */
public class BookmarkRepository {

    private final BookmarkDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public BookmarkRepository(Context context) {
        dao = AppDatabase.getInstance(context).bookmarkDao();
    }

    /** Return LiveData — Room update otomatis saat data berubah */
    public LiveData<List<BookmarkEntity>> getAllBookmarks() {
        return dao.getAll();
    }

    /** Return LiveData<Integer>: 1 = bookmarked, 0 = tidak */
    public LiveData<Integer> isBookmarked(int postId) {
        return dao.isBookmarkedLive(postId);
    }

    /** Insert dijalankan di background thread */
    public void insert(BookmarkEntity entity) {
        executor.execute(() -> dao.insert(entity));
    }

    /** Delete dijalankan di background thread */
    public void deleteById(int postId) {
        executor.execute(() -> dao.deleteById(postId));
    }
}
