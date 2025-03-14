package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.ViewModel.PlaceViewModel;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;

public class MapFragment extends Fragment implements MapEventsReceiver {
    private MapView mapView;
    private EditText searchBar;

    public MapFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Context ctx = requireContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // Initialize Map
        mapView = view.findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Add map click listener
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        mapView.getOverlays().add(0, mapEventsOverlay); // Add at position 0 to ensure it gets events first

        // Load saved places from the database (see previous implementation)
        loadSavedPlaces();

        // Center the map (Default location: Paris)
        IMapController mapController = mapView.getController();
        mapController.setZoom(14.0);
        GeoPoint startPoint = new GeoPoint(48.8566, 2.3522); // Paris
        mapController.setCenter(startPoint);

        // Search Bar functionality remains unchanged
        searchBar = view.findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString();
            if (!query.isEmpty()) {
                Toast.makeText(getContext(), "Searching: " + query, Toast.LENGTH_SHORT).show();
                // TODO: Implement search functionality
            }
            return false;
        });

        // Setup the List POI button
        Button btnListPoi = view.findViewById(R.id.btn_list_poi);
        btnListPoi.setOnClickListener(v -> {
            PointsOfInterestListFragment poiListFragment = new PointsOfInterestListFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, poiListFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    /**
     * Loads saved places from the database and adds a marker for each.
     */
    private void loadSavedPlaces() {
        PlaceViewModel placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
        placeViewModel.getAllAccommodations().observe(getViewLifecycleOwner(), accommodations -> {
            // Clear all overlays except the first one (the MapEventsOverlay)
            while (mapView.getOverlays().size() > 1) {
                mapView.getOverlays().remove(1);
            }

            // For each saved accommodation, create a marker on the map
            for (Accomodation accommodation : accommodations) {
                Marker marker = new Marker(mapView);
                marker.setPosition(new GeoPoint(accommodation.latitude, accommodation.longitude));
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(accommodation.name);
                mapView.getOverlays().add(marker);
            }
            mapView.invalidate();
        });
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint point) {
        // Single tap on map - open CreatePlaceFragment with coordinates
        try {
            System.out.println("Single tap at: " + point.getLatitude() + ", " + point.getLongitude());
            openCreatePlaceFragment(point.getLatitude(), point.getLongitude());
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error opening form: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint point) {
        // Not used but required by the interface
        return false;
    }

    private void openCreatePlaceFragment(double latitude, double longitude) {

        // Create new instance of CreatePlaceFragment with coordinates
        CreatePlaceFragment createPlaceFragment = new CreatePlaceFragment();

        // Pass the coordinates as arguments
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        createPlaceFragment.setArguments(args);

        // Show the fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, createPlaceFragment);

        fragmentTransaction.addToBackStack(null); // Allow back button to return to map
        fragmentTransaction.commit();
        System.out.println("test");
    }

    public void showAllPlaces() {
        if (mapView == null) return;

        List<GeoPoint> locations = new ArrayList<>();
        locations.add(new GeoPoint(48.8566, 2.3522)); // Paris Example
        locations.add(new GeoPoint(48.8606, 2.3376)); // Louvre Example

        // Clear all but the mapEventsOverlay (which is at position 0)
        while (mapView.getOverlays().size() > 1) {
            mapView.getOverlays().remove(1);
        }
        
        // Add markers
        for (GeoPoint point : locations) {
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle("Place");
            mapView.getOverlays().add(marker);
        }
        mapView.invalidate();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapView != null) {
            mapView.onDetach();
        }
    }
}