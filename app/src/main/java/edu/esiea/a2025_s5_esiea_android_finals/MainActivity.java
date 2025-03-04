package edu.esiea.a2025_s5_esiea_android_finals;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the MapFragment
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .commit();

        // Bottom Navigation Button Clicks
        //findViewById(R.id.btn_all_places).setOnClickListener(v -> mapFragment.showAllPlaces());
        //findViewById(R.id.btn_nearby_places).setOnClickListener(v -> showNearbyPlaces());
    }

    private void showNearbyPlaces() {
        Toast.makeText(this, "Showing Nearby Places", Toast.LENGTH_SHORT).show();
        // TODO: Implement GPS-based nearby places search
    }
}
