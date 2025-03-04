package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import androidx.room.Entity;

import edu.esiea.a2025_s5_esiea_android_finals.business.enums.AccomodationTag;
import java.util.List;

@Entity(tableName = "accomodation")
public class Accomodation extends Place {
    public Float minNightlyRate;
    public List<AccomodationTag> tags;

}