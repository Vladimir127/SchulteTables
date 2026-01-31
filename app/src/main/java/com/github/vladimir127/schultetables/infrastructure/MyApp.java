package com.github.vladimir127.schultetables.infrastructure;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.github.vladimir127.schultetables.domain.repository.SchulteCombinedRepositoryImpl;
import com.github.vladimir127.schultetables.domain.repository.SchulteLocalRepositoryImpl;
import com.github.vladimir127.schultetables.domain.repository.SchulteRepository;
import com.github.vladimir127.schultetables.domain.repository.room.GameRecordDao;
import com.github.vladimir127.schultetables.domain.repository.room.MyDatabase;

public class MyApp extends Application {
    public static Context context;
    public static final String BASE_URL = "http://94.124.78.199/api/v1/";

    public SchulteRepository getSchulteRepository() {
        if (schulteRepository == null) {
            schulteRepository = new SchulteLocalRepositoryImpl(gameRecordDao);
        }
        return schulteRepository;
    }

    private GameRecordDao gameRecordDao;

    private SchulteRepository schulteRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDatabase database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "schulte_tables.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        gameRecordDao = database.gameRecordDao();
    }
}
