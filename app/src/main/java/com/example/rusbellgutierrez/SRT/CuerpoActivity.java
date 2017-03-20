package com.example.rusbellgutierrez.SRT;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class CuerpoActivity extends AppCompatActivity implements FragmentIterationListener{

    //elementos para crear un drawer layout
    ListView lista_drawer;
    NavigationView nav;
    DrawerLayout drawer;
    //toolbar
    Toolbar toolbar;
    //boton del toolbar
    ActionBarDrawerToggle drawerToggle;

    public FragmentIterationListener mCallback=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo);

        //obtener los datos del intent
        Bundle extras =getIntent().getExtras();
        String nombre=extras.getString("nombre");

        //lista desplegable izquierda
        drawer =(DrawerLayout)findViewById(R.id.drawer_layout);
        nav =(NavigationView)findViewById(R.id.nav);
        //crear el view para el header_nav
        View header=nav.getHeaderView(0);
        //se crea el objeto que contendra el nombre
        TextView nom_usuario = (TextView)header.findViewById(R.id.username);
        //seteamos
        nom_usuario.setText(nombre);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //color al title
        toolbar.setTitleTextColor(Color.BLACK);
        //soporte para toolbar
        setSupportActionBar(toolbar);
        //muestra el nombre de la apliación
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //habilita el boton de la apliacion
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //accion del boton en toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"presiono",Toast.LENGTH_SHORT).show();
                //revisar llamado
                new Dialogo_Alerta().show(getSupportFragmentManager(), "SimpleDialog");
            }
        });*/

        //Aplicar el boton para desplegar el drawer
        drawerToggle = new ActionBarDrawerToggle(this,  drawer, toolbar,R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.truck_fast);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        //redirige a los fragmente, lógica para toda la funcionalidad
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.inicio:
                                fragment = new FragmentInicio();
                                fragmentTransaction = true;
                                break;
                            case R.id.ruta:
                                fragment = new FragmentRuta();
                                fragmentTransaction = true;
                                break;
                            case R.id.cliente:
                                fragment = new FragmentCliente();
                                fragmentTransaction = true;
                                break;
                            case R.id.documento:
                                fragment = new FragmentDocumento();
                                fragmentTransaction = true;
                                break;
                            case R.id.producto:
                                fragment = new FragmentProducto();
                                fragmentTransaction = true;
                                break;
                            case R.id.perfil:
                                fragment = new FragmentPerfil();
                                fragmentTransaction = true;
                                break;
                            case R.id.sesion:
                                new Dialogo_Alerta().show(getSupportFragmentManager(), "SimpleDialog");
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
        /*permite retroceder*/      .addToBackStack(null)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawer.closeDrawers();

                        return true;
                    }
                });

    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Bundle ob_bundle= new Bundle();
            ob_bundle.putString("contenido",scanContent);
            ob_bundle.putString("formato",scanFormat);
            mCallback.onFragmentIteration(ob_bundle);
            Intent i =new Intent(this,FragmentProducto.class);
            startActivity(i);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }*/



    @Override
    public void onFragmentIteration(Bundle parameters) {

    }
}