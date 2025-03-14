package edu.esiea.a2025_s5_esiea_android_finals;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import edu.esiea.a2025_s5_esiea_android_finals.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {
    private MapFragment mapFragment;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    
    // For older Android versions, include storage permissions
    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permissions before loading the map
        if (hasPermissions()) {
            loadMapFragment();
        } else {
            requestPermissions();
        }
    }
    
    private boolean hasPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        
        // For API < 33, also check storage permissions
        if (Build.VERSION.SDK_INT < 33) {
            for (String permission : STORAGE_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT < 33) {
            // Include storage permissions for older devices
            String[] combinedPermissions = new String[REQUIRED_PERMISSIONS.length + STORAGE_PERMISSIONS.length];
            System.arraycopy(REQUIRED_PERMISSIONS, 0, combinedPermissions, 0, REQUIRED_PERMISSIONS.length);
            System.arraycopy(STORAGE_PERMISSIONS, 0, combinedPermissions, REQUIRED_PERMISSIONS.length, STORAGE_PERMISSIONS.length);
            
            ActivityCompat.requestPermissions(this, combinedPermissions, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            
            if (allGranted) {
                loadMapFragment();
            } else {
                Toast.makeText(this, "Permissions required to use the map", Toast.LENGTH_LONG).show();
                finish(); // Exit app if permissions not granted
            }
        }
    }
    
    private void loadMapFragment() {
        // Load the MapFragment
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .commit();
    }

    private void showNearbyPlaces() {
        Toast.makeText(this, "Showing Nearby Places", Toast.LENGTH_SHORT).show();
        // TODO: Implement GPS-based nearby places search
    }
}