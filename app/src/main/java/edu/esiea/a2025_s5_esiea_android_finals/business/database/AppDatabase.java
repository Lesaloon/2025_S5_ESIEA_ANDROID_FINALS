package edu.esiea.a2025_s5_esiea_android_finals.business.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IAccomodationDAO;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Place;

@Database(entities = {Place.class, Accomodation.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract IAccomodationDAO accomodationDao();

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