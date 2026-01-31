package com.github.vladimir127.schultetables.domain.repository.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_records")
public class GameRecordEntity {
    public GameRecordEntity(String serverId, String name, String levelName, int record, boolean withVariants, boolean ascending) {
        this.serverId = serverId;
        this.name = name;
        this.levelName = levelName;
        this.record = record;
        this.withVariants = withVariants;
        this.ascending = ascending;
    }

    public long getId() {
        return id;
    }

    public String getServerId() {
        return serverId;
    }

    public String getName() {
        return name;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getRecord() {
        return record;
    }

    public boolean isWithVariants() {
        return withVariants;
    }

    public boolean isAscending() {
        return ascending;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String serverId;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String levelName;

    public void setRecord(int record) {
        this.record = record;
    }

    @ColumnInfo
    private int record;

    @ColumnInfo
    private boolean withVariants;

    @ColumnInfo
    private boolean ascending;

    public void setId(long id) {
        this.id = id;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @ColumnInfo
    private boolean isSynchronized = false;
}
