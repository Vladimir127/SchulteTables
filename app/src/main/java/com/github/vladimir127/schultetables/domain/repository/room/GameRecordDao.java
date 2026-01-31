package com.github.vladimir127.schultetables.domain.repository.room;

import static com.github.vladimir127.schultetables.utils.Constants.NAME_SCHULTE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameRecordDao {
    @Query("SELECT * FROM game_records WHERE name = '" + NAME_SCHULTE + "' AND isSynchronized = 0")
    List<GameRecordEntity> getUnsynchronizedSchulteRecords();

    //@Query("SELECT * FROM game_records WHERE name = '" + NAME_MATHS + "' AND isSynchronized = 0")
    //List<GameRecordEntity> getUnsynchronizedMathsRecords();

    @Query("SELECT * FROM game_records")
    List<GameRecordEntity> getAll();

    @Query("SELECT * FROM game_records WHERE id = :id")
    GameRecordEntity getGameRecord(long id);

    //@Query("SELECT * FROM game_records WHERE name = '" + NAME_MATHS + "' AND levelName = :levelName AND withVariants = :withVariants")
    //GameRecordEntity getMathsRecord(String levelName, boolean withVariants);

    @Query("SELECT * FROM game_records WHERE name = '" + NAME_SCHULTE + "' AND levelName = :levelName AND ascending = :ascending")
    GameRecordEntity getSchulteRecord(String levelName, boolean ascending);

    @Insert
    long insertGameRecord(GameRecordEntity gameRecord);

    @Update
    void updateGameRecord(GameRecordEntity gameRecord);
}
