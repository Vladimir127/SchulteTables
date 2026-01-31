package com.github.vladimir127.schultetables.domain.repository.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GameRecordEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract GameRecordDao gameRecordDao();
}
