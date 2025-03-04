package edu.esiea.a2025_s5_esiea_android_finals.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IAccomodationDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.database.AppDatabase;
import edu.esiea.a2025_s5_esiea_android_finals.business.database.GenericRepository;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;

public class AccomodationViewModel extends AndroidViewModel {
    private final GenericRepository<Accomodation> repository;
    private final LiveData<List<Accomodation>> allAccomodations;

    public AccomodationViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        repository = new GenericRepository<>(database.accomodationDao());
        allAccomodations = repository.getAll();
    }

    public LiveData<List<Accomodation>> getAllAccomodations() {
        return allAccomodations;
    }

    public LiveData<Accomodation> getAccomodationById(int id) {
        return repository.getById(id);
    }

    public void insert(Accomodation accomodation) {
        repository.insert(accomodation);
    }

    public void update(Accomodation accomodation) {
        repository.update(accomodation);
    }

    public void delete(Accomodation accomodation) {
        repository.delete(accomodation);
    }
}

