package com.womkk.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.womkk.R;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.LinearRing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polygon;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.PolygonMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.Location;
import com.yandex.runtime.image.ImageProvider;
import java.util.Arrays;
import java.util.List;

public class MuseumMapFragment extends Fragment {
    private MapView mapView;
    private LocationManager locationManager;
    private MapObjectCollection mapObjects;
    private PlacemarkMapObject userLocationMarker;
    private static final int PERMISSIONS_REQUEST_LOCATION = 100;
    private static final Point DEFAULT_LOCATION = new Point(54.706640, 20.500556); // Координаты главного корпуса

    // Координаты вершин границ музея
    private static final List<Point> MUSEUM_BOUNDARY = Arrays.asList(
            new Point(54.707054, 20.492241),
            new Point(54.707248, 20.495922),
            new Point(54.706738, 20.501430),
            new Point(54.706818, 20.502763),
            new Point(54.706771, 20.504529),
            new Point(54.706181, 20.505126),
            new Point(54.705808, 20.503752),
            new Point(54.706424, 20.494690),
            new Point(54.706374, 20.492499)
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_museum_map, container, false);
        mapView = view.findViewById(R.id.mapview);
        ImageButton buttonLocation = view.findViewById(R.id.button_location);

        buttonLocation.setOnClickListener(v -> moveToUserLocation());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            initializeMap();
        }

        // Add a listener to the Yandex logo button
//        mapView.findViewById(R.id.logo).setOnClickListener(v -> {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yandex.ru/maps/org/muzey_mirovogo_okeana_glavny_korpus/1159975670/"));
//            startActivity(browserIntent);
//        }
//        );

        return view;
    }

    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
    }

    private void initializeMap() {
        mapView.getMap().move(new CameraPosition(DEFAULT_LOCATION, 20.0f, 0.0f, 0.0f)); // Устанавливаем начальный вид

        mapObjects = mapView.getMap().getMapObjects();
        addMuseumBoundaries();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = MapKitFactory.getInstance().createLocationManager();
            locationManager.requestSingleUpdate(locationListener);
        } else {
            Toast.makeText(getContext(), R.string.location_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationUpdated(@NonNull Location location) {
            Point userLocation = location.getPosition();
            if (userLocationMarker == null) {
                userLocationMarker = mapObjects.addPlacemark(userLocation, ImageProvider.fromResource(getContext(), R.drawable.user_arrow));
            } else {
                userLocationMarker.setGeometry(userLocation);
            }
            mapView.getMap().move(new CameraPosition(userLocation, 20.0f, 0.0f, 0.0f));
        }

        @Override
        public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
            if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                Toast.makeText(getContext(), R.string.location_not_available, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void moveToUserLocation() {
        if (userLocationMarker != null) {
            mapView.getMap().move(new CameraPosition(userLocationMarker.getGeometry(), 20.0f, 0.0f, 0.0f));
        } else {
            Toast.makeText(getContext(), R.string.location_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void addMuseumBoundaries() {
        MapObjectCollection mapObjects = mapView.getMap().getMapObjects();

        Polygon polygon = new Polygon(new LinearRing(MUSEUM_BOUNDARY), Arrays.asList());
        PolygonMapObject polygonMapObject = mapObjects.addPolygon(polygon);

        polygonMapObject.setStrokeColor(0xFF00FFFF); // Голубой цвет границ
        polygonMapObject.setFillColor(0x8000FFFF);   // Полупрозрачный голубой цвет для заливки
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeMap();
            } else {
                Toast.makeText(getContext(), R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                initializeMap(); // Инициализация карты с дефолтным местоположением
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}
