package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import androidx.room.Entity;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.EntertainmentTag;

@Entity(tableName = "entertainment_venue")
public class EntertainmentVenue extends Place {
    public String openingHours;
    public String closingHours;
    public Float entryFee;
    public List<EntertainmentTag> tags;
}