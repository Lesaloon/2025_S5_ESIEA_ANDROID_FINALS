package edu.esiea.a2025_s5_esiea_android_finals.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.database.AppDatabase;
import edu.esiea.a2025_s5_esiea_android_finals.business.database.GenericRepository;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

public class PlaceViewModel extends AndroidViewModel {
    private GenericRepository<Accomodation> accommodationRepository;
    private GenericRepository<CulturalVenue> culturalVenueRepository;
    private GenericRepository<EntertainmentVenue> entertainmentVenueRepository;
    private GenericRepository<RelaxationVenue> relaxationVenueRepository;
    private GenericRepository<Restaurant> restaurantRepository;
    private GenericRepository<SportsVenue> sportsVenueRepository;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = null;
        try {
            db = AppDatabase.getInstance(application);
            // Currently only Accommodation is implemented in AppDatabase
            accommodationRepository = new GenericRepository<>(db.accomodationDao());
            // Assuming corresponding DAO methods are available in AppDatabase:
            culturalVenueRepository = new GenericRepository<>(db.culturalVenueDao());
            entertainmentVenueRepository = new GenericRepository<>(db.entertainmentVenueDao());
            relaxationVenueRepository = new GenericRepository<>(db.relaxationVenueDao());
            restaurantRepository = new GenericRepository<>(db.restaurantDao());
            sportsVenueRepository = new GenericRepository<>(db.sportsVenueDao());
        } catch (Exception e) {
            e.printStackTrace();
            // Initialize repositories with null if there's a database initialization error
            accommodationRepository = null;
            culturalVenueRepository = null;
            entertainmentVenueRepository = null;
            relaxationVenueRepository = null;
            restaurantRepository = null;
            sportsVenueRepository = null;
        }
    }

    // --- CRUD operations for Accomodation ---
    public void insertAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.insert(accommodation);
        }
    }

    public LiveData<List<Accomodation>> getAllAccommodations() {
        if (accommodationRepository != null) {
            return accommodationRepository.getAll();
        }
        return null;
    }

    public void updateAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.update(accommodation);
        }
    }

    public void deleteAccommodation(Accomodation accommodation) {
        if (accommodationRepository != null) {
            accommodationRepository.delete(accommodation);
        }
    }

    // --- CRUD operations for CulturalVenue ---
    public void insertCulturalVenue(CulturalVenue culturalVenue) {
        if (culturalVenueRepository != null) {
            culturalVenueRepository.insert(culturalVenue);
        }
    }

    public LiveData<List<CulturalVenue>> getAllCulturalVenues() {
        if (culturalVenueRepository != null) {
            return culturalVenueRepository.getAll();
        }
        return null;
    }

    public void updateCulturalVenue(CulturalVenue culturalVenue) {
        if (culturalVenueRepository != null) {
            culturalVenueRepository.update(culturalVenue);
        }
    }

    public void deleteCulturalVenue(CulturalVenue culturalVenue) {
        if (culturalVenueRepository != null) {
            culturalVenueRepository.delete(culturalVenue);
        }
    }

    // --- CRUD operations for EntertainmentVenue ---
    public void insertEntertainmentVenue(EntertainmentVenue entertainmentVenue) {
        if (entertainmentVenueRepository != null) {
            entertainmentVenueRepository.insert(entertainmentVenue);
        }
    }

    public LiveData<List<EntertainmentVenue>> getAllEntertainmentVenues() {
        if (entertainmentVenueRepository != null) {
            return entertainmentVenueRepository.getAll();
        }
        return null;
    }

    public void updateEntertainmentVenue(EntertainmentVenue entertainmentVenue) {
        if (entertainmentVenueRepository != null) {
            entertainmentVenueRepository.update(entertainmentVenue);
        }
    }

    public void deleteEntertainmentVenue(EntertainmentVenue entertainmentVenue) {
        if (entertainmentVenueRepository != null) {
            entertainmentVenueRepository.delete(entertainmentVenue);
        }
    }

    // --- CRUD operations for RelaxationVenue ---
    public void insertRelaxationVenue(RelaxationVenue relaxationVenue) {
        if (relaxationVenueRepository != null) {
            relaxationVenueRepository.insert(relaxationVenue);
        }
    }

    public LiveData<List<RelaxationVenue>> getAllRelaxationVenues() {
        if (relaxationVenueRepository != null) {
            return relaxationVenueRepository.getAll();
        }
        return null;
    }

    public void updateRelaxationVenue(RelaxationVenue relaxationVenue) {
        if (relaxationVenueRepository != null) {
            relaxationVenueRepository.update(relaxationVenue);
        }
    }

    public void deleteRelaxationVenue(RelaxationVenue relaxationVenue) {
        if (relaxationVenueRepository != null) {
            relaxationVenueRepository.delete(relaxationVenue);
        }
    }

    // --- CRUD operations for Restaurant ---
    public void insertRestaurant(Restaurant restaurant) {
        if (restaurantRepository != null) {
            restaurantRepository.insert(restaurant);
        }
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        if (restaurantRepository != null) {
            return restaurantRepository.getAll();
        }
        return null;
    }

    public void updateRestaurant(Restaurant restaurant) {
        if (restaurantRepository != null) {
            restaurantRepository.update(restaurant);
        }
    }

    public void deleteRestaurant(Restaurant restaurant) {
        if (restaurantRepository != null) {
            restaurantRepository.delete(restaurant);
        }
    }

    // --- CRUD operations for SportsVenue ---
    public void insertSportsVenue(SportsVenue sportsVenue) {
        if (sportsVenueRepository != null) {
            sportsVenueRepository.insert(sportsVenue);
        }
    }

    public LiveData<List<SportsVenue>> getAllSportsVenues() {
        if (sportsVenueRepository != null) {
            return sportsVenueRepository.getAll();
        }
        return null;
    }

    public void updateSportsVenue(SportsVenue sportsVenue) {
        if (sportsVenueRepository != null) {
            sportsVenueRepository.update(sportsVenue);
        }
    }

    public void deleteSportsVenue(SportsVenue sportsVenue) {
        if (sportsVenueRepository != null) {
            sportsVenueRepository.delete(sportsVenue);
        }
    }
}
