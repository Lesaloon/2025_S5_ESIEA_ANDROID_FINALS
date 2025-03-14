package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import androidx.room.Entity;

import java.util.List;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RelaxationTag;

@Entity(tableName = "relaxation_venue")
public class RelaxationVenue extends Place {
    public String openingHours;
    public String closingHours;
    public Float entryFee;
    public List<RelaxationTag> tags;
}
