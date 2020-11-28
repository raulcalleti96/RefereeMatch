package com.example.refereematch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

public class intermedioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbarfin;
    Menu menu;
    TextView textView;
    CountDownTimer countDownTimer;
    TextView countDownText;
    long contadortiempos = 180000; //3 min

    private intermedioActivity.Tablero fondo;
    int x, y;
    private Casilla[][] casillas;
    private boolean activo = true;

    protected void onCreate(Bundle savedInstanceState) {
        //Temporizador


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modointermedio);

        //temporizador
        countDownText = findViewById(R.id.textViewtiempomedio);
        startTimer();


        //Menu
        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.nav_view);
        toolbarfin = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbarfin);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarfin, R.string.nav_draw_abrir, R.string.nav_draw_cerrar);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutmed);

        fondo = new Tablero(this);
        fondo.setOnTouchListener(this);
        layout.addView(fondo);
        casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                casillas[f][c] = new Casilla();
            }
        }
        this.disponerBombas();
        this.contarBombasPerimetro();

    }
    /*======Parte de juego=====*/


    public void presionado(View v) {
        casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                casillas[f][c] = new Casilla();
            }
        }
        this.disponerBombas();
        this.contarBombasPerimetro();
        activo = true;

        fondo.invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (activo)
            for (int f = 0; f < 8; f++) {
                for (int c = 0; c < 8; c++) {
                    if (casillas[f][c].dentro((int) event.getX(),
                            (int) event.getY())) {
                        casillas[f][c].destapado = true;
                        if (casillas[f][c].contenido == 80) {

                            AlertDialog.Builder builder4 = new AlertDialog.Builder(intermedioActivity.this);
                            builder4.setTitle(R.string.tituloderrota);
                            builder4.setMessage(R.string.derrota);
                            builder4.setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    recreate();
                                }
                            });
                            builder4.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(intermedioActivity.this, principalmenu.class);
                                    startActivity(intent);
                                }
                            });
                            builder4.setCancelable(false);

                            AlertDialog dialog4 = builder4.create();
                            dialog4.show();
                            Window window = dialog4.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.BOTTOM;
                            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                            window.setAttributes(wlp);

                            activo = false;
                        } else if (casillas[f][c].contenido == 0)
                            recorrer(f, c);
                        fondo.invalidate();
                    }
                }
            }
        if (gano() && activo) {
            AlertDialog.Builder builder4 = new AlertDialog.Builder(intermedioActivity.this);
            builder4.setTitle(R.string.titulovictora);
            builder4.setMessage(R.string.victoria);
            builder4.setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recreate();
                }
            });
            builder4.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(intermedioActivity.this, principalmenu.class);
                    startActivity(intent);
                }
            });
            builder4.setCancelable(false);

            AlertDialog dialog4 = builder4.create();
            dialog4.show();
            Window window = dialog4.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
            activo = false;
        }

        return true;
    }

    class Tablero  extends View {
        public Tablero(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(0, 0, 0);
            int ancho = 0;
            if (canvas.getWidth() < canvas.getHeight())
                ancho = fondo.getWidth();
            else
                ancho = fondo.getHeight();
            int anchocua = ancho / 8;
            Paint paint = new Paint();
            paint.setTextSize(20);
            Paint paint2 = new Paint();
            paint2.setTextSize(20);
            paint2.setTypeface(Typeface.DEFAULT_BOLD);
            paint2.setARGB(255, 0, 0, 255);
            Paint paintlinea1 = new Paint();
            paintlinea1.setARGB(255, 255, 255, 255);
            int filaact = 0;
            for (int f = 0; f < 8; f++) {
                for (int c = 0; c < 8; c++) {
                    casillas[f][c].fijarxy(c * anchocua, filaact, anchocua);
                    if (casillas[f][c].destapado == false)
                        paint.setARGB(153, 204, 204, 204);
                    else
                        paint.setARGB(255, 153, 153, 153);
                    canvas.drawRect(c * anchocua, filaact, c * anchocua
                            + anchocua - 2, filaact + anchocua - 2, paint);
                    // linea blanca
                    canvas.drawLine(c * anchocua, filaact, c * anchocua
                            + anchocua, filaact, paintlinea1);
                    canvas.drawLine(c * anchocua + anchocua - 1, filaact, c
                                    * anchocua + anchocua - 1, filaact + anchocua,
                            paintlinea1);

                    if (casillas[f][c].contenido >= 1
                            && casillas[f][c].contenido <= 8
                            && casillas[f][c].destapado)
                        canvas.drawText(
                                String.valueOf(casillas[f][c].contenido), c
                                        * anchocua + (anchocua / 2) - 8,
                                filaact + anchocua / 2, paint2);

                    if (casillas[f][c].contenido == 80
                            && casillas[f][c].destapado) {
                        Paint bomba = new Paint();
                        Bitmap b  ;

                        if(principalmenu.personajeSeleccionado == 0){
                            b = BitmapFactory.decodeResource(getResources(), R.drawable.amarilla);
                            bomba.setColor(Color.RED);
                            canvas.drawBitmap(b,c * anchocua + ((anchocua / 2)-70),filaact + ((anchocua / 2)-70),bomba);
                        }else  if (principalmenu.personajeSeleccionado == 1){
                            b = BitmapFactory.decodeResource(getResources(), R.drawable.roja);
                            bomba.setColor(Color.RED);
                            canvas.drawBitmap(b,c * anchocua + ((anchocua / 2)-70),filaact + ((anchocua / 2)-70),bomba);
                        }else if(principalmenu.personajeSeleccionado == 2){
                            b = BitmapFactory.decodeResource(getResources(), R.drawable.silbato);
                            bomba.setColor(Color.RED);
                            canvas.drawBitmap(b,c * anchocua + ((anchocua / 2)-70),filaact + ((anchocua / 2)-70),bomba);

                        }
                    }

                }
                filaact = filaact + anchocua;
            }
        }
    }
    private void disponerBombas() {
        int cantidad = 8;
        do {
            int fila = (int) (Math.random() * 8);
            int columna = (int) (Math.random() * 8);
            if (casillas[fila][columna].contenido == 0) {
                casillas[fila][columna].contenido = 80;
                cantidad--;
            }
        } while (cantidad != 0);
    }

    private boolean gano() {
        int cant = 0;
        for (int f = 0; f < 8; f++)
            for (int c = 0; c < 8; c++)
                if (casillas[f][c].destapado)
                    cant++;
        if (cant == 56)
            return true;
        else
            return false;
    }

    private void contarBombasPerimetro() {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                if (casillas[f][c].contenido == 0) {
                    int cant = contarCoordenada(f, c);
                    casillas[f][c].contenido = cant;
                }
            }
        }
    }
    int contarCoordenada(int fila, int columna) {
        int total = 0;
        if (fila - 1 >= 0 && columna - 1 >= 0) {
            if (casillas[fila - 1][columna - 1].contenido == 80)
                total++;
        }
        if (fila - 1 >= 0) {
            if (casillas[fila - 1][columna].contenido == 80)
                total++;
        }
        if (fila - 1 >= 0 && columna + 1 < 8) {
            if (casillas[fila - 1][columna + 1].contenido == 80)
                total++;
        }

        if (columna + 1 < 8) {
            if (casillas[fila][columna + 1].contenido == 80)
                total++;
        }
        if (fila + 1 < 8 && columna + 1 < 8) {
            if (casillas[fila + 1][columna + 1].contenido == 80)
                total++;
        }

        if (fila + 1 < 8) {
            if (casillas[fila + 1][columna].contenido == 80)
                total++;
        }
        if (fila + 1 < 8 && columna - 1 >= 0) {
            if (casillas[fila + 1][columna - 1].contenido == 80)
                total++;
        }
        if (columna - 1 >= 0) {
            if (casillas[fila][columna - 1].contenido == 80)
                total++;
        }
        return total;
    }

    private void recorrer(int fil, int col) {
        if (fil >= 0 && fil < 8 && col >= 0 && col < 8) {
            if (casillas[fil][col].contenido == 0) {
                casillas[fil][col].destapado = true;
                casillas[fil][col].contenido = 50;
                recorrer(fil, col + 1);
                recorrer(fil, col - 1);
                recorrer(fil + 1, col);
                recorrer(fil - 1, col);
                recorrer(fil - 1, col - 1);
                recorrer(fil - 1, col + 1);
                recorrer(fil + 1, col + 1);
                recorrer(fil + 1, col - 1);
            } else if (casillas[fil][col].contenido >= 1
                    && casillas[fil][col].contenido <= 8) {
                casillas[fil][col].destapado = true;
            }
        }
    }




    /*======Parte de juego=====*/

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
                Intent intenthome = new Intent(intermedioActivity.this, principalmenu.class);
                startActivity(intenthome);
                break;
            case R.id.nav_restart:
                recreate();
                break;
            case R.id.nav_pers:
                AlertDialog.Builder builder = new AlertDialog.Builder(intermedioActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogo_spinner, null);
                builder.setTitle(R.string.Personaje);
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(intermedioActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listadoPersonajes));
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
                AlertDialog.Builder builder2 = new AlertDialog.Builder(intermedioActivity.this);
                builder2.setTitle(R.string.Instrucciones);
                builder2.setMessage(R.string.instruccionesTexto);
                builder2.setNeutralButton("OK", null);
                builder2.setCancelable(false);

                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.nav_Ajustes:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(intermedioActivity.this);
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

                            Intent intent = new Intent(intermedioActivity.this, facilActivity.class);
                            startActivity(intent);

                        }else if (principalmenu.dificultad == 1){
                            Intent intent = new Intent(intermedioActivity.this, intermedioActivity.class);
                            startActivity(intent);
                        }else if (principalmenu.dificultad == 2){
                            Intent intent = new Intent(intermedioActivity.this, dificilActivity.class);
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
                AlertDialog.Builder builder4 = new AlertDialog.Builder(intermedioActivity.this);
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

    public void startTimer(){
        countDownTimer = new CountDownTimer(contadortiempos,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                contadortiempos = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder4 = new AlertDialog.Builder(intermedioActivity.this);
                builder4.setTitle(R.string.perdidatiempo);
                builder4.setMessage(R.string.textoperdidatiempo);
                builder4.setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                });
                builder4.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(intermedioActivity.this, principalmenu.class);
                        startActivity(intent);
                    }
                });
                builder4.setCancelable(false);

                AlertDialog dialog4 = builder4.create();
                dialog4.show();
            }
        }.start();
    }

    public void updateTimer(){
        int minutos = (int) contadortiempos / 60000;
        int segundos = (int) contadortiempos % 60000 / 1000;

        String cambiotiempo;

        cambiotiempo = "" + minutos;
        cambiotiempo += ":";
        if(segundos < 10) cambiotiempo += "0";
        cambiotiempo +=segundos;

        countDownText.setText(cambiotiempo);

    }
}
