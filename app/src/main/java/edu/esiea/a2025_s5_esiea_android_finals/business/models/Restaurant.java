package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import androidx.room.Entity;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.PriceRange;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RestaurantTag;

@Entity(tableName = "restaurant")
public class Restaurant extends Place {
    public Enum<PriceRange> priceRange;
    public List<RestaurantTag> tags;
    public String cuisineOrigin; // e.g. French, Italian, Chinese, etc.
}