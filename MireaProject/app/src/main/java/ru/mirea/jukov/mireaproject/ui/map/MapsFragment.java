package ru.mirea.jukov.mireaproject.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ru.mirea.jukov.mireaproject.R;

public class MapsFragment extends Fragment implements GoogleMap.OnMapClickListener{

    private GoogleMap map;

    private ArrayList<MarkerOptions> markers = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            setUpMap();

            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    private void setUpMap(){
        // Вернадского 86
        LatLng mirea = new LatLng(55.661445, 37.477049);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(mirea).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        String snippetText = "Корпус РТУ МИРЭА\n" +
                "Дата основания: 1 июля 1900 г\n" +
                "Адрес: пр-т Вернадского 86 \n" +
                "Координаты: 55.661445, 37.477049";
        addNewMarker("МИРЭА, Институт тонких химический технологий", snippetText, mirea);

        // Стромынка 20
        mirea = new LatLng(55.794259, 37.701448 );
        cameraPosition = new CameraPosition.Builder().target(mirea).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        snippetText = "Корпус РТУ МИРЭА\n" +
                "Дата основания: 29 августа 1936 г\n" +
                "Адрес: ул. Стромынка 20 \n" +
                "Координаты: 55.794259, 37.701448 ";
        addNewMarker("МИРЭА (МГУПИ)", snippetText, mirea);

        // Ставрополь
        mirea = new LatLng(45.052028, 41.912651 );
        cameraPosition = new CameraPosition.Builder().target(mirea).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        snippetText = "Филиал РТУ МИРЭА, Ставрополь\n" +
                "Дата основания: 18 декабря 1996 г\n" +
                "Адрес: пр-т Кулакова, 8А \n" +
                "Координаты: 45.052028, 41.912651";
        addNewMarker("МИРЭА Ставрополь", snippetText, mirea);

        // Фрязино
        mirea = new LatLng(55.966887, 38.050533);
        cameraPosition = new CameraPosition.Builder().target(mirea).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        snippetText = "Филиал РТУ МИРЭА, Фрязино\n" +
                "Дата основания: 19 сентября 1962 г\n" +
                "Адрес: Вокзальная 2а, к.61\n" +
                "Координаты: 55.794259, 37.701448 ";
        addNewMarker("МИРЭА (МГУПИ)", snippetText, mirea);

        // Вернадского 78
        mirea = new LatLng(55.670005, 37.479894);
        cameraPosition = new CameraPosition.Builder().target(mirea).zoom(12).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        snippetText = "Головной корпус РТУ МИРЭА\n" +
                "Дата основания: 1947 г\n" +
                "Адрес: пр-т Вернадского 78с4 \n" +
                "Координаты: 55.670005, 37.479894";
        addNewMarker("МИРЭА - головной корпус", snippetText, mirea);

        placeMarkers(markers);
    }

    private void addNewMarker(String title, String snippet, LatLng latLng){
        MarkerOptions marker = new MarkerOptions().title(title)
                .snippet(snippet).position(latLng);
        map.addMarker(marker);

        markers.add(marker);
    }

    private void placeMarkers(ArrayList<MarkerOptions> markers){
        for(MarkerOptions marker: markers) map.addMarker(marker);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        addNewMarker("Будующий корпус РТУ МИРЭА", "В ближайшем будущем здесь будет открыт " +
                "новый корпус лучшего университета в России!", latLng);
    }
}