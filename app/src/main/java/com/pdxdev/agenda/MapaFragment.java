package com.pdxdev.agenda;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pdxdev.agenda.dao.AlunoDAO;
import com.pdxdev.agenda.modelo.Aluno;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoDaEscola = pegaCoordernadaDoEndereco("Rua Carmelo Rangel 373, Batel, Curitiba");
        if(posicaoDaEscola != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng coordenada = pegaCoordernadaDoEndereco(aluno.getEndereco());
            if (coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();


        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String []{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 987);
        } else {
            new Localizador(getContext(), googleMap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 987) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                new Localizador(getContext(), googleMap);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private LatLng pegaCoordernadaDoEndereco(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);

            if (!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
