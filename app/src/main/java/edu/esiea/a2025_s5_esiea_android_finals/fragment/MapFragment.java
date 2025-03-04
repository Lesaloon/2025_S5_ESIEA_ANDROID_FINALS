package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.a2025_s5_esiea_android_finals.R;

public class MapFragment extends Fragment {
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
        showAllPlaces();
        // Center the map (Default location: Paris)
        IMapController mapController = mapView.getController();
        mapController.setZoom(14.0);
        GeoPoint startPoint = new GeoPoint(48.8566, 2.3522); // Paris
        mapController.setCenter(startPoint);

        // Search Bar functionality
        searchBar = view.findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString();
            if (!query.isEmpty()) {
                Toast.makeText(getContext(), "Searching: " + query, Toast.LENGTH_SHORT).show();
                // TODO: Implement search functionality
            }
            return false;
        });

        return view;
    }

    public void showAllPlaces() {
        if (mapView == null) return;

        List<GeoPoint> locations = new ArrayList<>();
        locations.add(new GeoPoint(48.8566, 2.3522)); // Paris Example
        locations.add(new GeoPoint(48.8606, 2.3376)); // Louvre Example

        mapView.getOverlays().clear();
        for (GeoPoint point : locations) {
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle("Place");
            mapView.getOverlays().add(marker);
        }
        mapView.invalidate();
    }
}
