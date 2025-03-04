package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.SportsTag;

public class SportsVenue extends Place {
    public String openingHours;
    public String closingHours;
    public Boolean SubscriptionRequired;
    public Float entryFee;
    public List<SportsTag> tags;
}