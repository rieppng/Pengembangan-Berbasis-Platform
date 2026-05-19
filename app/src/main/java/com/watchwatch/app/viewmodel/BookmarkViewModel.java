package com.watchwatch.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.watchwatch.app.database.BookmarkEntity;
import com.watchwatch.app.repository.BookmarkRepository;

import java.util.List;

/**
 * BookmarkViewModel — menggunakan AndroidViewModel (bukan ViewModel biasa)
 * karena BookmarkRepository membutuhkan Context untuk inisialisasi AppDatabase.
 *
 * AndroidViewModel menyediakan Application context yang aman digunakan
 * di ViewModel (tidak bocor memory seperti Activity/Fragment context).
 */
public class BookmarkViewModel extends AndroidViewModel {

    private final BookmarkRepository repository;
    private final LiveData<List<BookmarkEntity>> allBookmarks;

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        repository = new BookmarkRepository(application);
        allBookmarks = repository.getAllBookmarks();
    }

    public LiveData<List<BookmarkEntity>> getAllBookmarks() {
        return allBookmarks;
    }

    public LiveData<Integer> isBookmarked(int postId) {
        return repository.isBookmarked(postId);
    }

    public void addBookmark(BookmarkEntity entity) {
        repository.insert(entity);
    }

    public void removeBookmark(int postId) {
        repository.deleteById(postId);
    }
}
