package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.EntertainmentTag;
public class EntertainmentVenue extends Place {
    public String openingHours;
    public String closingHours;
    public Float entryFee;
    public List<Enum<EntertainmentTag>> tags;
}