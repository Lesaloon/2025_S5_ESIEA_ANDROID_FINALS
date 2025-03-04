package edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;

@Dao
public interface IAccomodationDAO extends IDAO<Accomodation> {
    @Override
    @Query("SELECT * FROM accomodation ORDER BY name ASC")
    LiveData<List<Accomodation>> getAll();

    @Override
    @Query("SELECT * FROM accomodation WHERE id = :id")
    LiveData<Accomodation> getById(int id);
}
