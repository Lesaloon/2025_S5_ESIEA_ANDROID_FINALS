package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;

@Dao
public interface ICulturalVenueDAO extends IDAO<CulturalVenue> {
    @Override
    @Query("SELECT * FROM cultural_venue ORDER BY name ASC")
    LiveData<List<CulturalVenue>> getAll();

    @Override
    @Query("SELECT * FROM cultural_venue WHERE id = :id")
    LiveData<CulturalVenue> getById(int id);
}
