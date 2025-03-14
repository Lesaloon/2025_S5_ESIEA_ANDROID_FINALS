package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;

@Dao
public interface IRestaurantDAO extends IDAO<Restaurant> {
    @Override
    @Query("SELECT * FROM restaurant ORDER BY name ASC")
    LiveData<List<Restaurant>> getAll();

    @Override
    @Query("SELECT * FROM restaurant WHERE id = :id")
    LiveData<Restaurant> getById(int id);
}
