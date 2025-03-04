package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface IDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T entity);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);

    LiveData<List<T>> getAll();
    LiveData<T> getById(int id);
}