package com.watchwatch.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * AppDatabase — singleton Room database untuk seluruh aplikasi.
 *
 * Pola singleton memastikan hanya ada satu koneksi database,
 * menghindari kondisi race condition saat diakses dari multiple thread.
 *
 * exportSchema = false: menonaktifkan export skema ke file JSON
 * (tidak diperlukan untuk project ini).
 */
@Database(entities = {BookmarkEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract BookmarkDao bookmarkDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "watchwatch_database"
                    ).build();
                }
            }
        }
        return instance;
    }
}
