package com.github.vladimir127.schultetables.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Save {

    private final static String TAG = "ControlReality.Save";

    public static void saveString(Context ct, String name, String text) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putString(name, text);
        ed.commit();
    }

    public static String loadString(Context ct, String name) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        String result = sPref.getString(name, "");
        return result;
    }

    public static void saveInt(Context ct, String name, int value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putInt(name, value);
        ed.apply();
    }

    public static int loadInt(Context ct, String name, int defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getInt(name, defValue);
    }

    public static void saveLong(Context ct, String name, long value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putLong(name, value);
        ed.apply();
    }

    public static long loadLong(Context ct, String name, int defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getLong(name, defValue);
    }

    public static void saveBool(Context ct, String name, boolean value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putBoolean(name, value);
        ed.apply();
    }

    public static boolean loadBool(Context ct, String name, boolean defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getBoolean(name, defValue);
    }

    public static Uri saveTempImage(Context context, Bitmap bitmap, String NameImage) {
        try {
            File storageDir = context.getFilesDir();
            File filePath = File.createTempFile(
                    NameImage,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return Uri.fromFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri saveImage(Context context, Bitmap bitmap, String uri) {
        try {
            File storageDir = context.getFilesDir();
            File filePath = new File(uri);
            filePath.delete(); // Delete the File, just in Case, that there was still another File
            filePath.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return Uri.fromFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveRecord(Context ct, String name, int level, boolean ascending, int timeMillis) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (level > 0) {
            builder.append(level);
        }

        if (ascending) {
            builder.append("Asc");
        } else {
            builder.append("Desc");
        }

        String key = builder.toString();

        saveInt(ct, key, timeMillis);
    }

    public static int loadRecord(Context ct, String name, int level, boolean ascending) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (level > 0) {
            builder.append(level);
        }

        if (ascending) {
            builder.append("Asc");
        } else {
            builder.append("Desc");
        }

        String key = builder.toString();

        return loadInt(ct, key, -1);
    }
}
