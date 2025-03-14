package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;

@Dao
public interface IRelaxationVenueDAO extends IDAO<RelaxationVenue> {
    @Override
    @Query("SELECT * FROM relaxation_venue ORDER BY name ASC")
    LiveData<List<RelaxationVenue>> getAll();

    @Override
    @Query("SELECT * FROM relaxation_venue WHERE id = :id")
    LiveData<RelaxationVenue> getById(int id);
}
