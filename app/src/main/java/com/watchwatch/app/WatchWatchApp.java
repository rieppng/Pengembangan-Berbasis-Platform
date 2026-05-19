package com.watchwatch.app;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * WatchWatchApp — Application class yang dijalankan sebelum Activity manapun.
 *
 * Fungsinya: menerapkan preferensi dark mode yang tersimpan di SharedPreferences
 * saat aplikasi pertama kali dibuka, sehingga tema tidak "berkedip" saat launch.
 *
 * WAJIB didaftarkan di AndroidManifest.xml:
 * <application android:name=".WatchWatchApp" ...>
 */
public class WatchWatchApp extends Application {

    public static final String PREFS_NAME  = "watchwatch_settings";
    public static final String KEY_DARK_MODE = "dark_mode";

    @Override
    public void onCreate() {
        super.onCreate();
        applyDarkModePreference();
    }

    private void applyDarkModePreference() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDark = prefs.getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES
                       : AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        );
    }
}
