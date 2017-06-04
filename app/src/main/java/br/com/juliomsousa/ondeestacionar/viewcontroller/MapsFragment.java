package br.com.juliomsousa.ondeestacionar.viewcontroller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.juliomsousa.ondeestacionar.model.Local;

public class MapsFragment extends SupportMapFragment
        implements
            OnMapReadyCallback,
            LocationListener,
            ActivityCompat.OnRequestPermissionsResultCallback,
            GoogleMap.OnMapLongClickListener,
            GoogleMap.OnMapClickListener,
            GoogleMap.OnMarkerClickListener,
            GoogleMap.OnMarkerDragListener,
            GoogleMap.OnInfoWindowClickListener {

    private LocationManager lm;
    private Location location;
    private double longitude = -23.5550527;
    private double latitude = -46.6571561;

    private ArrayList<Local> infoLocais;

    private FirebaseDatabase database;

    private static final int REQUEST_PERMISSION = 1;

    private GoogleMap map;

    private static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        getMapAsync(this);

        initMaps();

    }

    public void initMaps(){
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();

        } else {
            lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 60000, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;

        if (lm != null) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

        // exibir transito
        map.setTrafficEnabled(false);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));

        loadMarker();

        //obtem posição clicada
        map.setOnMapLongClickListener(this);
        //controla eventos do marcador clicado
        map.setOnMarkerClickListener(this);
        // controla eventos do infoWindows
        map.setOnInfoWindowClickListener(this);


        // desativa a barra de ferramentas do mapa
        map.getUiSettings().setMapToolbarEnabled(false);
        // ativa os botoes de zoom
        map.getUiSettings().setZoomControlsEnabled(true);
        // desativa controle de nivel
        map.getUiSettings().setIndoorLevelPickerEnabled(false);

    }

    public void addPin(LatLng latLng){
        Intent telaCadastro = new Intent(getContext(), NovoLocalActivity.class);

        telaCadastro.putExtra("latLng", latLng);

        startActivity(telaCadastro);

    }

    @Override
    public void onLocationChanged(Location arg0) {

    }

    @Override
    public void onProviderDisabled(String arg0) {

    }

    @Override
    public void onProviderEnabled(String arg0) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),"Autorizado",Toast.LENGTH_SHORT).show();
                    initMaps();

                } else {
                    Toast.makeText(getContext(),"Permissão negada",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),PERMISSIONS , REQUEST_PERMISSION);
    }

    public void loadMarker(){
        final DatabaseReference locais = database.getReference("locais");
        infoLocais = new ArrayList<>();

        locais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                map.clear();


                for (DataSnapshot dataSnapshot1 : dataSnapshots) {

                    Local local = dataSnapshot1.getValue(Local.class);
                    MarkerOptions markerOptions = new MarkerOptions();

                    infoLocais.add(local);

                    markerOptions.position(new LatLng(local.getLatitude(), local.getLongitude()))
                            .title(local.getNome())
                            .snippet(local.getEndereco() + " " + local.getNumero())
                            .icon(null);

                    map.addMarker(markerOptions);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
        map.animateCamera(update);
        Log.d("EVENTO", "onMapClick Ativado");

        map.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title("Novo Local")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

        );
    }

    @Override
    public void onMapClick(LatLng latLng) { }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) { }

    @Override
    public void onMarkerDrag(Marker marker) { }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.showInfoWindow();
    }



    public void onCreateDialog(final Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Novo Local");
        builder.setMessage("O que deseja fazer?");

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addPin(marker.getPosition());
            }
        });
        builder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                marker.remove();
            }
        });
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }


    public Local getInfoMarker(LatLng latLng){
        Local endereco = new Local();

        for (Local locais: infoLocais) {

            // debug
            Log.d("Locais loop", "" + locais.toString());

            if(locais.getLatitude() == latLng.latitude && locais.getLongitude() == latLng.longitude) {

                // debug
                Log.d("Local retornado", "" + locais.toString());

                return locais;
            }

        }
        return endereco;
    }

    public void telaInfo(Local local) {

        Intent telaDetalhes = new Intent(this.getContext(), DetalhesActivity.class);

        telaDetalhes.putExtra("d", local);

        startActivity(telaDetalhes);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Local detalhes = getInfoMarker(marker.getPosition());

        Log.d("OnMarkerClick", "valor: " + detalhes.isAtivo());
        if(detalhes.isAtivo()) {
            telaInfo(detalhes);
        } else {
            onCreateDialog(marker);
        }
    }
}