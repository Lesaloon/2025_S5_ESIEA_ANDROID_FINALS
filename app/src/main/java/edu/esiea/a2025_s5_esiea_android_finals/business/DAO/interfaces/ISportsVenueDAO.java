package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

@Dao
public interface ISportsVenueDAO extends IDAO<SportsVenue> {
    @Override
    @Query("SELECT * FROM sports_venue ORDER BY name ASC")
    LiveData<List<SportsVenue>> getAll();

    @Override
    @Query("SELECT * FROM sports_venue WHERE id = :id")
    LiveData<SportsVenue> getById(int id);
}
