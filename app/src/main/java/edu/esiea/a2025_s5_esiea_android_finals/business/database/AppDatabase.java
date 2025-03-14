package edu.esiea.a2025_s5_esiea_android_finals.business.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IAccomodationDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.ICulturalVenueDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IEntertainmentVenueDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IRelaxationVenueDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IRestaurantDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.ISportsVenueDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Place;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

@Database(entities = {
        Place.class,
        Accomodation.class,
        CulturalVenue.class,
        EntertainmentVenue.class,
        RelaxationVenue.class,
        Restaurant.class,
        SportsVenue.class
}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract IAccomodationDAO accomodationDao();
    public abstract ICulturalVenueDAO culturalVenueDao();
    public abstract IEntertainmentVenueDAO entertainmentVenueDao();
    public abstract IRelaxationVenueDAO relaxationVenueDao();
    public abstract IRestaurantDAO restaurantDao();
    public abstract ISportsVenueDAO sportsVenueDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "app_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}