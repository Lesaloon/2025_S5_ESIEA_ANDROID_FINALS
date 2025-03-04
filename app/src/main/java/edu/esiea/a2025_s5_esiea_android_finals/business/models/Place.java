package edu.esiea.a2025_s5_esiea_android_finals.business.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This is the POI (Point of Interest) class that will be the mother class of all the other POI classes
@Entity(tableName = "places")
public abstract class Place {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public String phoneNumber;
    public String email;
    public String websiteURL;
    public float latitude;
    public float longitude;

    public Place() {}

    public Place(String name, String description, String phoneNumber, String email, String websiteURL, float latitude, float longitude) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.websiteURL = websiteURL;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", websiteURL='" + websiteURL + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}