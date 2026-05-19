package com.watchwatch.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper untuk mengubah format tanggal dari WordPress API.
 * API mengirim format ISO 8601: "2024-01-15T10:30:00"
 * Kita ubah menjadi format Indonesia: "15 Jan 2024"
 */
public class DateUtils {

    private static final SimpleDateFormat INPUT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    private static final SimpleDateFormat OUTPUT_FORMAT =
            new SimpleDateFormat("dd MMM yyyy", new Locale("id", "ID"));

    public static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) return "";
        try {
            Date date = INPUT_FORMAT.parse(isoDate);
            return OUTPUT_FORMAT.format(date);
        } catch (ParseException e) {
            return isoDate; // kembalikan apa adanya jika gagal parse
        }
    }

    private DateUtils() {}
}
