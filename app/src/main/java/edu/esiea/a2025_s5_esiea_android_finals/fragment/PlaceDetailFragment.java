package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.ViewModel.PlaceViewModel;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.AccomodationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.CulturalTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.EntertainmentTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.PriceRange;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RelaxationTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.RestaurantTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.enums.SportsTag;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Place;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

public class PlaceDetailFragment extends Fragment {
    
    private static final String ARG_PLACE_ID = "place_id";
    private static final String ARG_PLACE_TYPE = "place_type";
    
    private int placeId;
    private String placeType;
    private Place currentPlace;
    
    private PlaceViewModel placeViewModel;
    
    private TextView nameTextView;
    private TextView typeTextView;
    private TextView descriptionTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView websiteTextView;
    private TextView locationTextView;
    private LinearLayout specificDetailsContainer;
    private MaterialButton editButton;
    private MaterialButton deleteButton;
    
    public PlaceDetailFragment() {
        // Required empty public constructor
    }
    
    public static PlaceDetailFragment newInstance(int placeId, String placeType) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PLACE_ID, placeId);
        args.putString(ARG_PLACE_TYPE, placeType);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            placeId = getArguments().getInt(ARG_PLACE_ID);
            placeType = getArguments().getString(ARG_PLACE_TYPE);
        }
        
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize views
        nameTextView = view.findViewById(R.id.place_name);
        typeTextView = view.findViewById(R.id.place_type);
        descriptionTextView = view.findViewById(R.id.place_description);
        phoneTextView = view.findViewById(R.id.place_phone);
        emailTextView = view.findViewById(R.id.place_email);
        websiteTextView = view.findViewById(R.id.place_website);
        //locationTextView = view.findViewById(R.id.place_location);
        specificDetailsContainer = view.findViewById(R.id.specific_details_container);
        editButton = view.findViewById(R.id.btn_edit);
        deleteButton = view.findViewById(R.id.btn_delete);
        
        // Load place data based on type
        loadPlaceData();
        
        // Set up button listeners
        setupButtonListeners();
    }
    
    private void loadPlaceData() {
        if (placeType == null || placeId <= 0) {
            Toast.makeText(requireContext(), "Error: Invalid place data", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
            return;
        }
        
        switch (placeType) {
            case "Accommodation":
                loadAccommodation();
                break;
            case "Restaurant":
                loadRestaurant();
                break;
            case "CulturalVenue":
                loadCulturalVenue();
                break;
            case "EntertainmentVenue":
                loadEntertainmentVenue();
                break;
            case "RelaxationVenue":
                loadRelaxationVenue();
                break;
            case "SportsVenue":
                loadSportsVenue();
                break;
            default:
                Toast.makeText(requireContext(), "Error: Unknown place type", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
    
    private void loadAccommodation() {
        typeTextView.setText("Accommodation");
        
        LiveData<List<Accomodation>> accommodationsLiveData = placeViewModel.getAllAccommodations();
        accommodationsLiveData.observe(getViewLifecycleOwner(), accommodations -> {
            for (Accomodation accommodation : accommodations) {
                if (accommodation.id == placeId) {
                    currentPlace = accommodation;
                    displayCommonFields(accommodation);
                    
                    // Display accommodation-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Price range
                    if (accommodation.minNightlyRate > 0) {
                        addDetailField("Minimum Nightly Rate", String.format("%.2f", accommodation.minNightlyRate));
                    }
                    
                    // Tags
                    if (accommodation.tags != null && !accommodation.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (AccomodationTag tag : accommodation.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void loadRestaurant() {
        typeTextView.setText("Restaurant");
        
        LiveData<List<Restaurant>> restaurantsLiveData = placeViewModel.getAllRestaurants();
        restaurantsLiveData.observe(getViewLifecycleOwner(), restaurants -> {
            for (Restaurant restaurant : restaurants) {
                if (restaurant.id == placeId) {
                    currentPlace = restaurant;
                    displayCommonFields(restaurant);
                    
                    // Display restaurant-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Cuisine origin
                    if (restaurant.cuisineOrigin != null && !restaurant.cuisineOrigin.isEmpty()) {
                        addDetailField("Cuisine Origin", restaurant.cuisineOrigin);
                    }
                    
                    // Price range
                    String priceRangeText = "Not specified";
                    if (restaurant.priceRange != null) {
                        switch (restaurant.priceRange) {
                            case CHEAP:
                                priceRangeText = "$ (Cheap)";
                                break;
                            case MODERATE:
                                priceRangeText = "$$ (Moderate)";
                                break;
                            case EXPENSIVE:
                                priceRangeText = "$$$ (Expensive)";
                                break;
                        }
                    }
                    addDetailField("Price Range", priceRangeText);
                    
                    // Tags
                    if (restaurant.tags != null && !restaurant.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (RestaurantTag tag : restaurant.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void loadCulturalVenue() {
        typeTextView.setText("Cultural Venue");
        
        LiveData<List<CulturalVenue>> venuesLiveData = placeViewModel.getAllCulturalVenues();
        venuesLiveData.observe(getViewLifecycleOwner(), venues -> {
            for (CulturalVenue venue : venues) {
                if (venue.id == placeId) {
                    currentPlace = venue;
                    displayCommonFields(venue);
                    
                    // Display venue-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Opening hours
                    addDetailField("Opening Hours", venue.openingHours);
                    
                    // Entrance fee
                    if (venue.entryFee != null) {
                        addDetailField("Entrance Fee", String.format("%.2f", venue.entryFee));
                    }
                    
                    // Tags
                    if (venue.tags != null && !venue.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (CulturalTag tag : venue.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void loadEntertainmentVenue() {
        typeTextView.setText("Entertainment Venue");
        
        LiveData<List<EntertainmentVenue>> venuesLiveData = placeViewModel.getAllEntertainmentVenues();
        venuesLiveData.observe(getViewLifecycleOwner(), venues -> {
            for (EntertainmentVenue venue : venues) {
                if (venue.id == placeId) {
                    currentPlace = venue;
                    displayCommonFields(venue);
                    
                    // Display venue-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Opening hours
                    addDetailField("Opening Hours", venue.openingHours);
                    
                    // Entrance fee
                    if (venue.entryFee != null) {
                        addDetailField("Entrance Fee", String.format("%.2f", venue.entryFee));
                    }
                    
                    // Tags
                    if (venue.tags != null && !venue.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (EntertainmentTag tag : venue.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void loadRelaxationVenue() {
        typeTextView.setText("Relaxation Venue");
        
        LiveData<List<RelaxationVenue>> venuesLiveData = placeViewModel.getAllRelaxationVenues();
        venuesLiveData.observe(getViewLifecycleOwner(), venues -> {
            for (RelaxationVenue venue : venues) {
                if (venue.id == placeId) {
                    currentPlace = venue;
                    displayCommonFields(venue);
                    
                    // Display venue-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Opening hours
                    addDetailField("Opening Hours", venue.openingHours);
                    
                    // Entrance fee
                    if (venue.entryFee != null) {
                        addDetailField("Entrance Fee", String.format("%.2f", venue.entryFee));
                    }
                    
                    // Tags
                    if (venue.tags != null && !venue.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (RelaxationTag tag : venue.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void loadSportsVenue() {
        typeTextView.setText("Sports Venue");
        
        LiveData<List<SportsVenue>> venuesLiveData = placeViewModel.getAllSportsVenues();
        venuesLiveData.observe(getViewLifecycleOwner(), venues -> {
            for (SportsVenue venue : venues) {
                if (venue.id == placeId) {
                    currentPlace = venue;
                    displayCommonFields(venue);
                    
                    // Display venue-specific fields
                    specificDetailsContainer.removeAllViews();
                    
                    // Opening hours
                    addDetailField("Opening Hours", venue.openingHours);
                    
                    // Subscription required
                    addDetailField("Subscription Required", venue.SubscriptionRequired ? "Yes" : "No");
                    
                    // Entrance fee
                    if (venue.entryFee != null) {
                        addDetailField("Entrance Fee", String.format("%.2f", venue.entryFee));
                    }
                    
                    // Tags
                    if (venue.tags != null && !venue.tags.isEmpty()) {
                        StringBuilder tagsText = new StringBuilder();
                        for (SportsTag tag : venue.tags) {
                            if (tagsText.length() > 0) tagsText.append(", ");
                            tagsText.append(tag.toString());
                        }
                        addDetailField("Tags", tagsText.toString());
                    }
                    
                    break;
                }
            }
        });
    }
    
    private void displayCommonFields(Place place) {
        nameTextView.setText(place.name);
        descriptionTextView.setText(place.description);
        
        phoneTextView.setText("Phone: " + (place.phoneNumber != null && !place.phoneNumber.isEmpty() ? place.phoneNumber : "Not provided"));
        emailTextView.setText("Email: " + (place.email != null && !place.email.isEmpty() ? place.email : "Not provided"));
        websiteTextView.setText("Website: " + (place.websiteURL != null && !place.websiteURL.isEmpty() ? place.websiteURL : "Not provided"));
        
        //locationTextView.setText(String.format("Latitude: %.6f, Longitude: %.6f", place.latitude, place.longitude));
    }
    
    private void addDetailField(String label, String value) {
        View view = getLayoutInflater().inflate(R.layout.item_detail_field, specificDetailsContainer, false);
        TextView labelView = view.findViewById(R.id.field_label);
        TextView valueView = view.findViewById(R.id.field_value);
        
        labelView.setText(label);
        valueView.setText(value != null ? value : "Not provided");
        
        specificDetailsContainer.addView(view);
    }
    
    private void setupButtonListeners() {
        editButton.setOnClickListener(v -> openEditScreen());
        deleteButton.setOnClickListener(v -> confirmDelete());
    }
    
    private void openEditScreen() {
        if (currentPlace == null) {
            Toast.makeText(requireContext(), "Error: Place data not available", Toast.LENGTH_SHORT).show();
            return;
        }
        
        CreatePlaceFragment createPlaceFragment = new CreatePlaceFragment();
        Bundle args = new Bundle();
        args.putInt("place_id", placeId);
        args.putString("place_type", placeType);
        args.putDouble("latitude", currentPlace.latitude);
        args.putDouble("longitude", currentPlace.longitude);
        args.putBoolean("edit_mode", true);
        createPlaceFragment.setArguments(args);
        
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createPlaceFragment)
                .addToBackStack(null)
                .commit();
    }
    
    private void confirmDelete() {
        if (currentPlace == null) {
            Toast.makeText(requireContext(), "Error: Place data not available", Toast.LENGTH_SHORT).show();
            return;
        }
        
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Place")
                .setMessage("Are you sure you want to delete this place?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deletePlace();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    
    private void deletePlace() {
        if (currentPlace != null) {
            switch (placeType) {
                case "Accommodation":
                    placeViewModel.deleteAccommodation((Accomodation) currentPlace);
                    break;
                case "Restaurant":
                    placeViewModel.deleteRestaurant((Restaurant) currentPlace);
                    break;
                case "CulturalVenue":
                    placeViewModel.deleteCulturalVenue((CulturalVenue) currentPlace);
                    break;
                case "EntertainmentVenue":
                    placeViewModel.deleteEntertainmentVenue((EntertainmentVenue) currentPlace);
                    break;
                case "RelaxationVenue":
                    placeViewModel.deleteRelaxationVenue((RelaxationVenue) currentPlace);
                    break;
                case "SportsVenue":
                    placeViewModel.deleteSportsVenue((SportsVenue) currentPlace);
                    break;
            }
            
            Toast.makeText(requireContext(), "Place deleted", Toast.LENGTH_SHORT).show();
            
            // Return to map fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
}