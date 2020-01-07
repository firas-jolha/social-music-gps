package firas.jolha.gpsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Button button = null;
    private static final double LAT = 35.671354;
    private static final double LNG = 35.803961;
    private static final String LATLNG_DISPLAY_FORMAT = "%,.6f";
    private static final String LAT_DISPLAY_FORMAT = LATLNG_DISPLAY_FORMAT;
    private static final String LNG_DISPLAY_FORMAT = LATLNG_DISPLAY_FORMAT;
    private static final String[] REQUESTED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
    private MainActivity current = this;
    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(0, 0));
    private TextView latText = null;
    private TextView lngText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.locateButton);
        latText = findViewById(R.id.latText);
        lngText = findViewById(R.id.lngText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(current, String.format("Clicked %s", "Locate Button"), Toast.LENGTH_LONG).show();
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(current, REQUESTED_PERMISSIONS, 1);
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
        });
//        findViewById(R.id.mapView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        LatLng location = new LatLng(LAT, LNG);
//        map.addMarker(new MarkerOptions().position(location).title("Location - Own"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(location));
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

//            //    Activity#requestPermissions
//            map.setMyLocationEnabled(true);
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }

        map.addMarker(markerOptions);
        map.setMaxZoomPreference(12); // 21 is permitted max zoom level
        map.setMinZoomPreference(3);// 2 is permitted min zoom level

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Log.w("Zoom Level", String.format("%f", map.getCameraPosition().zoom));
            }
        });
        UiSettings mapUiSettings = map.getUiSettings();
        mapUiSettings.setMyLocationButtonEnabled(true);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(current, REQUESTED_PERMISSIONS, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(current, String.format("Current location:\n(%f, %f)", location.getLatitude(), location.getLongitude()), Toast.LENGTH_LONG).show();
                markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(current, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                markerOptions.position(latLng).draggable(true);
                map.addMarker(markerOptions);
                latText.setText(String.format(LAT_DISPLAY_FORMAT, latLng.latitude));
                lngText.setText(String.format(LNG_DISPLAY_FORMAT, latLng.longitude));
                Toast.makeText(current, "Marker changed", Toast.LENGTH_LONG).show();
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setTitle("Now");
                Toast.makeText(current, marker.getTitle(), Toast.LENGTH_LONG);
                return false;
            }
        });

        mapUiSettings.setCompassEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setIndoorLevelPickerEnabled(true);

    }
}
