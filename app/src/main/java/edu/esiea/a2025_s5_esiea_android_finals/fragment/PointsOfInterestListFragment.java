package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.ViewModel.PlaceViewModel;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.CulturalVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.EntertainmentVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.RelaxationVenue;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Restaurant;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.SportsVenue;

public class PointsOfInterestListFragment extends Fragment {
    private ListView poiListView;
    private SimpleAdapter adapter;
    private PlaceViewModel viewModel;
    private List<Map<String, String>> data = new ArrayList<>();
    private List<Map<String, String>> filteredData = new ArrayList<>();
    
    // Filter states
    private boolean showAccommodation = true;
    private boolean showRestaurant = true;
    private boolean showCultural = true;
    private boolean showEntertainment = true;
    private boolean showRelaxation = true;
    private boolean showSports = true;

    public PointsOfInterestListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poi_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        poiListView = view.findViewById(R.id.poi_list_view);
        
        // Initialize the adapter with data structure
        adapter = new SimpleAdapter(
                requireContext(),
                filteredData,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "type"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        poiListView.setAdapter(adapter);

        // Set up filter chips
        setupFilterChips(view);

        // Set click listener for list items
        poiListView.setOnItemClickListener((parent, view1, position, id) -> {
            Map<String, String> item = filteredData.get(position);
            String placeType = item.get("actualType");
            int placeId = Integer.parseInt(item.get("id"));
            
            // Open the detail fragment
            openPlaceDetails(placeId, placeType);
        });

        // Load data for all place types
        viewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
        loadAllPlaces();

        // Set click listener for the back button
        view.findViewById(R.id.btn_back).setOnClickListener(v -> backToMap());
    }
    
    private void setupFilterChips(View view) {
        // Set up the "All" chip
        Chip chipAll = view.findViewById(R.id.chip_all);
        chipAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Enable all filters
                showAccommodation = true;
                showRestaurant = true;
                showCultural = true;
                showEntertainment = true;
                showRelaxation = true;
                showSports = true;
                
                // Check all other chips
                ((Chip) view.findViewById(R.id.chip_accommodation)).setChecked(true);
                ((Chip) view.findViewById(R.id.chip_restaurant)).setChecked(true);
                ((Chip) view.findViewById(R.id.chip_cultural)).setChecked(true);
                ((Chip) view.findViewById(R.id.chip_entertainment)).setChecked(true);
                ((Chip) view.findViewById(R.id.chip_relaxation)).setChecked(true);
                ((Chip) view.findViewById(R.id.chip_sports)).setChecked(true);
                
                applyFilters();
            } else {
                // If no other chip is checked, re-check this one
                boolean anyChecked = showAccommodation || showRestaurant || showCultural ||
                        showEntertainment || showRelaxation || showSports;
                if (!anyChecked) {
                    buttonView.setChecked(true);
                }
            }
        });
        
        // Set up individual filter chips
        Chip chipAccommodation = view.findViewById(R.id.chip_accommodation);
        chipAccommodation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showAccommodation = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
        
        Chip chipRestaurant = view.findViewById(R.id.chip_restaurant);
        chipRestaurant.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showRestaurant = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
        
        Chip chipCultural = view.findViewById(R.id.chip_cultural);
        chipCultural.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showCultural = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
        
        Chip chipEntertainment = view.findViewById(R.id.chip_entertainment);
        chipEntertainment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showEntertainment = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
        
        Chip chipRelaxation = view.findViewById(R.id.chip_relaxation);
        chipRelaxation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showRelaxation = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
        
        Chip chipSports = view.findViewById(R.id.chip_sports);
        chipSports.setOnCheckedChangeListener((buttonView, isChecked) -> {
            showSports = isChecked;
            updateAllChipState(view);
            applyFilters();
        });
    }
    
    private void updateAllChipState(View view) {
        // Update the "All" chip state based on individual chip states
        boolean allChecked = showAccommodation && showRestaurant && showCultural &&
                showEntertainment && showRelaxation && showSports;
        
        Chip chipAll = view.findViewById(R.id.chip_all);
        chipAll.setChecked(allChecked);
    }
    
    private void applyFilters() {
        // Clear the filtered data
        filteredData.clear();
        
        // Apply filters
        for (Map<String, String> item : data) {
            String type = item.get("actualType");
            if (type != null) {
                switch (type) {
                    case "Accommodation":
                        if (showAccommodation) filteredData.add(item);
                        break;
                    case "Restaurant":
                        if (showRestaurant) filteredData.add(item);
                        break;
                    case "CulturalVenue":
                        if (showCultural) filteredData.add(item);
                        break;
                    case "EntertainmentVenue":
                        if (showEntertainment) filteredData.add(item);
                        break;
                    case "RelaxationVenue":
                        if (showRelaxation) filteredData.add(item);
                        break;
                    case "SportsVenue":
                        if (showSports) filteredData.add(item);
                        break;
                }
            }
        }
        
        // Notify the adapter
        adapter.notifyDataSetChanged();
    }
    
    private void loadAllPlaces() {
        // Clear the data list
        data.clear();
        
        // Load accommodations
        viewModel.getAllAccommodations().observe(getViewLifecycleOwner(), accommodations -> {
            if (accommodations != null) {
                for (Accomodation place : accommodations) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Accommodation");
                    item.put("actualType", "Accommodation");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
        
        // Load restaurants
        viewModel.getAllRestaurants().observe(getViewLifecycleOwner(), restaurants -> {
            if (restaurants != null) {
                for (Restaurant place : restaurants) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Restaurant");
                    item.put("actualType", "Restaurant");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
        
        // Load cultural venues
        viewModel.getAllCulturalVenues().observe(getViewLifecycleOwner(), venues -> {
            if (venues != null) {
                for (CulturalVenue place : venues) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Cultural Venue");
                    item.put("actualType", "CulturalVenue");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
        
        // Load entertainment venues
        viewModel.getAllEntertainmentVenues().observe(getViewLifecycleOwner(), venues -> {
            if (venues != null) {
                for (EntertainmentVenue place : venues) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Entertainment Venue");
                    item.put("actualType", "EntertainmentVenue");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
        
        // Load relaxation venues
        viewModel.getAllRelaxationVenues().observe(getViewLifecycleOwner(), venues -> {
            if (venues != null) {
                for (RelaxationVenue place : venues) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Relaxation Venue");
                    item.put("actualType", "RelaxationVenue");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
        
        // Load sports venues
        viewModel.getAllSportsVenues().observe(getViewLifecycleOwner(), venues -> {
            if (venues != null) {
                for (SportsVenue place : venues) {
                    Map<String, String> item = new HashMap<>();
                    item.put("name", place.name);
                    item.put("type", "Sports Venue");
                    item.put("actualType", "SportsVenue");
                    item.put("id", String.valueOf(place.id));
                    data.add(item);
                }
                applyFilters();
            }
        });
    }
    
    private void openPlaceDetails(int placeId, String placeType) {
        PlaceDetailFragment detailFragment = PlaceDetailFragment.newInstance(placeId, placeType);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    public void backToMap() {
        // Go back to the map fragment
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MapFragment());
        fragmentTransaction.commit();
    }
}