package com.example.refereematch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class dificilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbarfin;
    Menu menu;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mododificil);


        //Menu
        drawerLayout = findViewById(R.id.drawer_layout4);
        navigationView = findViewById(R.id.nav_view);
        toolbarfin = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbarfin);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarfin, R.string.nav_draw_abrir, R.string.nav_draw_cerrar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_inicio:
                Intent intenthome = new Intent(dificilActivity.this, principalmenu.class);
                startActivity(intenthome);
                break;
            case R.id.nav_restart:
                recreate();
                break;
            case R.id.nav_pers:
                AlertDialog.Builder builder = new AlertDialog.Builder(dificilActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogo_spinner, null);
                builder.setTitle(R.string.Personaje);
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(dificilActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listadoPersonajes));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Tarjeta amarilla")) {
                            principalmenu.personajeSeleccionado = 0;
                            recreate();
                        } else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Tarjeta roja")) {
                            principalmenu.personajeSeleccionado = 1;
                            recreate();
                        } else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Silbato")) {
                            principalmenu.personajeSeleccionado = 2;
                            recreate();
                        } else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Balon")) {
                            principalmenu.personajeSeleccionado = 3;
                            recreate();
                        };

                    }
                });
                builder.setCancelable(false);
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                //Valorar simplemente el cambio de personaje sin cambiar tablero
                break;
            case R.id.nav_Instrucciones:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(dificilActivity.this);
                builder2.setTitle(R.string.Instrucciones);
                builder2.setMessage(R.string.instruccionesTexto);
                builder2.setNeutralButton("OK", null);
                builder2.setCancelable(false);

                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.nav_Ajustes:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(dificilActivity.this);
                builder3.setTitle(R.string.dificultad);
                String[] items = {"Fácil","Normal","Difícil"};
                int checkedItem = principalmenu.dificultad;
                builder3.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                principalmenu.dificultad = 0;

                                break;
                            case 1:
                                principalmenu.dificultad = 1;

                                break;
                            case 2:
                                principalmenu.dificultad = 2;

                                break;
                        }
                    }
                });

                builder3.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(principalmenu.dificultad == 0){

                            Intent intent = new Intent(dificilActivity.this, facilActivity.class);
                            startActivity(intent);

                        }else if (principalmenu.dificultad == 1){
                            Intent intent = new Intent(dificilActivity.this, intermedioActivity.class);
                            startActivity(intent);
                        }else if (principalmenu.dificultad == 2){
                            Intent intent = new Intent(dificilActivity.this, dificilActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder3.setCancelable(false);


                AlertDialog dialog3 = builder3.create();
                dialog3.setCanceledOnTouchOutside(false);
                dialog3.show();

                break;
            case R.id.nav_info:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(dificilActivity.this);
                builder4.setTitle(R.string.Informacion);
                builder4.setMessage(R.string.info);
                builder4.setNeutralButton("OK", null);
                builder4.setCancelable(false);

                AlertDialog dialog4 = builder4.create();
                dialog4.show();
                break;
            case R.id.nav_Salir:
                finishAffinity();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
