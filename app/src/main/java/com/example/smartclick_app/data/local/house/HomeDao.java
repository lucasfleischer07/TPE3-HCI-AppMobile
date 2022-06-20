package com.example.smartclick_app.data.local.house;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartclick_app.data.local.room.LocalRoom;

import java.util.List;

@Dao
public abstract class HomeDao {

    @Query("SELECT * FROM Room")
    public abstract LiveData<List<LocalHome>> findAll();

    @Query("SELECT * FROM Room")
    public abstract LiveData<List<LocalRoom>> findAll(String overload);

    @Query("SELECT * FROM Room LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalHome>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalHome... house);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalHome> houses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalRoom> rooms,String overload);

    @Update
    public abstract void update(LocalHome room);

    @Delete
    public abstract void delete(LocalHome room);

    @Query("DELETE FROM Room WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Room WHERE id IN (SELECT id FROM Room LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Room")
    public abstract void deleteAll();

    @Query("SELECT * FROM Room WHERE id = :id")
    public abstract LiveData<LocalHome> findById(String id);


}