package com.watchwatch.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * BookmarkDao — mendefinisikan semua query ke tabel bookmarks.
 *
 * Room akan otomatis membuat implementasi dari interface ini.
 * Method yang return LiveData bersifat asynchronous secara otomatis.
 * Method yang return void/int harus dipanggil dari thread non-UI (pakai Executor).
 */
@Dao
public interface BookmarkDao {

    /** Simpan bookmark. Jika ID sudah ada, replace (update) data lama. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkEntity bookmark);

    /** Hapus berdasarkan post ID */
    @Query("DELETE FROM bookmarks WHERE id = :postId")
    void deleteById(int postId);

    /** Ambil semua bookmark, urut dari yang terbaru disimpan */
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    LiveData<List<BookmarkEntity>> getAll();

    /**
     * Cek apakah post sudah di-bookmark.
     * Return LiveData<Integer>: nilainya 1 jika ada, 0 jika tidak.
     * Dipakai oleh DetailActivity untuk update ikon bookmark secara reaktif.
     */
    @Query("SELECT COUNT(*) FROM bookmarks WHERE id = :postId")
    LiveData<Integer> isBookmarkedLive(int postId);
}
