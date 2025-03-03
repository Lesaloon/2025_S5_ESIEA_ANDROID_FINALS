package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.PriceRange;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RestaurantTag;

public class Restaurant extends Place {
    public Enum<PriceRange> priceRange;
    public List<RestaurantTag> tags;
    public String cuisineOrigin; // e.g. French, Italian, Chinese, etc.
}