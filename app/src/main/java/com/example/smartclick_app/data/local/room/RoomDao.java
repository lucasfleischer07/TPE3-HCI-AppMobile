package com.example.smartclick_app.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class RoomDao {

    @Query("SELECT * FROM Room")
    public abstract LiveData<List<LocalRoom>> findAll();

    @Query("SELECT * FROM Room LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalRoom>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalRoom... room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalRoom> rooms);

    @Update
    public abstract void update(LocalRoom room);

    @Delete
    public abstract void delete(LocalRoom room);

    @Query("DELETE FROM Room WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Room WHERE id IN (SELECT id FROM Room LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Room")
    public abstract void deleteAll();

    @Query("SELECT * FROM Room WHERE id = :id")
    public abstract LiveData<LocalRoom> findById(String id);
}