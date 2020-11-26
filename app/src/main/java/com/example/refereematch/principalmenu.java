package com.example.refereematch;

import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class principalmenu extends AppCompatActivity {

    public static int  dificultad = 0;
    public static int personajeSeleccionado = 0;
    private CardView jugar,instrucciones, ajustes, personaje, info, salir;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);


        jugar = (CardView) findViewById(R.id.Cardjugar);
        instrucciones = (CardView) findViewById(R.id.CardInstrucciones);
        ajustes = (CardView) findViewById(R.id.CardAjustes);
        personaje = (CardView) findViewById(R.id.CardPersonaje);
        info = (CardView) findViewById(R.id.CardInfo);
        salir = (CardView) findViewById(R.id.CardSalir);

        instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(principalmenu.this);
               builder.setTitle(R.string.Instrucciones);
               builder.setMessage(R.string.instruccionesTexto);
               builder.setNeutralButton("OK", null);
               builder.setCancelable(false);

               AlertDialog dialog = builder.create();
               dialog.show();
            }
        });

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(principalmenu.this);
                builder.setTitle(R.string.dificultad);
                String[] items = {"Fácil","Normal","Difícil"};
                int checkedItem = dificultad;
                builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                               dificultad = 0;
                                break;
                            case 1:
                               dificultad = 1;
                                break;
                            case 2:
                                dificultad = 2;
                                break;
                        }
                    }
                });

                builder.setNeutralButton("OK", null);
                builder.setCancelable(false);


                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });


        /*Falta Spinner Personaje!!!!!!!!*/
        personaje.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(principalmenu.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogo_spinner,null);
                builder.setTitle(R.string.Personaje);
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(principalmenu.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.listadoPersonajes));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mSpinner.getSelectedItem().toString().equalsIgnoreCase("Tarjeta amarilla")){
                            personajeSeleccionado = 0;
                        }else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Tarjeta roja")){
                            personajeSeleccionado = 1;
                        }else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Silbato")){
                            personajeSeleccionado = 2;
                        }else if (mSpinner.getSelectedItem().toString().equalsIgnoreCase("Balon")){
                            personajeSeleccionado = 3;
                        }

                    }
                });
                builder.setCancelable(false);
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


            }
        });
        //Info
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(principalmenu.this);
                builder.setTitle(R.string.Informacion);
                builder.setMessage(R.string.info);
                builder.setNeutralButton("OK", null);
                builder.setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        /*Salir*/
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });



    }
}
