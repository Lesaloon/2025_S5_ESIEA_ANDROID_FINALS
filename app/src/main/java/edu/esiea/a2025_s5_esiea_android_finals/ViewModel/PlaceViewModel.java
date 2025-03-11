package edu.esiea.a2025_s5_esiea_android_finals.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.database.AppDatabase;
import edu.esiea.a2025_s5_esiea_android_finals.business.database.GenericRepository;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Place;

public class PlaceViewModel extends AndroidViewModel {
    private GenericRepository<Accomodation> accommodationRepository;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = null;
        try {
            db = AppDatabase.getInstance(application);
            // Currently only Accommodation is implemented in AppDatabase
            accommodationRepository = new GenericRepository<>(db.accomodationDao());
        } catch (Exception e) {
            e.printStackTrace();
            // Initialize with a null repository if there's a database initialization error
            accommodationRepository = null;
        }
    }

    // Method to insert an Accommodation
    public void insertAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.insert(accommodation);
        }
    }

    // Method to get all accommodations
    public LiveData<List<Accomodation>> getAllAccommodations() {
        if (accommodationRepository != null) {
            return accommodationRepository.getAll();
        }
        return null;
    }

    // Updates accommodation
    public void updateAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.update(accommodation);
        }
    }

    // Deletes accommodation
    public void deleteAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.delete(accommodation);
        }
    }
    
    // This class can be expanded as more DAO interfaces are added to the AppDatabase
}