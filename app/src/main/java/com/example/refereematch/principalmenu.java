package com.example.refereematch;

import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        //Info
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(principalmenu.this);
                builder.setTitle(R.string.info);
                builder.setMessage(R.string.app_name );
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
