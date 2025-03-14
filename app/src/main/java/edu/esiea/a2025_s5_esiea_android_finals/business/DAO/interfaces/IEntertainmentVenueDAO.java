package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;

@Dao
public interface IEntertainmentVenueDAO extends IDAO<EntertainmentVenue> {
    @Override
    @Query("SELECT * FROM entertainment_venue ORDER BY name ASC")
    LiveData<List<EntertainmentVenue>> getAll();

    @Override
    @Query("SELECT * FROM entertainment_venue WHERE id = :id")
    LiveData<EntertainmentVenue> getById(int id);
}
