package edu.esiea.a2025_s5_esiea_android_finals.business.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.business.enums.AccomodationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.CulturalTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.EntertainmentTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RelaxationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RestaurantTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.SportsTag;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromAccomodationTagList(List<AccomodationTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<AccomodationTag> toAccomodationTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<AccomodationTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }

    @TypeConverter
    public static String fromRestaurantTagList(List<RestaurantTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<RestaurantTag> toRestaurantTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<RestaurantTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }

    @TypeConverter
    public static String fromCulturalTagList(List<CulturalTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<CulturalTag> toCulturalTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<CulturalTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }

    @TypeConverter
    public static String fromEntertainmentTagList(List<EntertainmentTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<EntertainmentTag> toEntertainmentTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<EntertainmentTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }

    @TypeConverter
    public static String fromRelaxationTagList(List<RelaxationTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<RelaxationTag> toRelaxationTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<RelaxationTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }

    @TypeConverter
    public static String fromSportsTagList(List<SportsTag> tags) {
        if (tags == null) {
            return null;
        }
        return gson.toJson(tags);
    }

    @TypeConverter
    public static List<SportsTag> toSportsTagList(String tagsString) {
        if (tagsString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<SportsTag>>() {}.getType();
        return gson.fromJson(tagsString, listType);
    }
}