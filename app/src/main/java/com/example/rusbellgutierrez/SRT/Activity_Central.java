package com.example.rusbellgutierrez.SRT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rey.material.widget.ProgressView;

import java.util.Arrays;


public class Activity_Central extends AppCompatActivity implements Interface_FragmentListener {

    //elementos para crear un drawer layout
    ListView lista_drawer;
    NavigationView nav;
    DrawerLayout drawer;
    //toolbar
    Toolbar toolbar;
    //boton del toolbar
    Context context=this;
    ActionBarDrawerToggle drawerToggle;

    Interface_FragmentListener mCallback=null;

    SQL_Sentencias sql=new SQL_Sentencias();
    SQL_Helper helper=new SQL_Helper(this);

    //Clase para guardar los datos;
    Clase_Articulo art;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo);

        //obtener los datos del intent
        Bundle extras =this.getIntent().getExtras();
        String nombre=extras.getString("nombre");
        String codigo=extras.getString("codigo");

        //lista desplegable izquierda
        drawer =(DrawerLayout)findViewById(R.id.drawer_layout);
        nav =(NavigationView)findViewById(R.id.nav);
        //crear el view para el header_nav
        View header=nav.getHeaderView(0);
        //se crea el objeto que contendra el nombre
        TextView nom_usuario = (TextView)header.findViewById(R.id.username);
        TextView cod_usuario= (TextView)header.findViewById(R.id.code);
        //seteamos
        nom_usuario.setText(nombre);
        cod_usuario.setText(codigo);

        Log.i("CODIGO","DATO DEL CODIGO "+cod_usuario.getText().toString());

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //color al title
        toolbar.setTitleTextColor(Color.BLACK);
        //soporte para toolbar
        setSupportActionBar(toolbar);
        //muestra el nombre de la apliación
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Aplicar el boton para desplegar el drawer
        drawerToggle = new ActionBarDrawerToggle(this,  drawer, toolbar,R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.truck_fast);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        final Bundle bundle = new Bundle();
        bundle.putString("codigo", codigo);
        //Fragment_Inicio myFragment = new Fragment_Inicio ();
//Agrega bundle como argumento al fragment.
        //myFragment.setArguments(bundle);

        Log.i("CODIGO","DATO DEL BUNDLE "+bundle);

        //redirige a los fragmente, lógica para toda la funcionalidad
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.inicio:
                                fragment = new Fragment_Inicio();
                                fragment.setArguments(bundle);
                                fragmentTransaction = true;
                                break;
                            case R.id.ruta:
                                fragment = new Fragment_Ruta();
                                fragmentTransaction = true;
                                break;
                            case R.id.cliente:
                                fragment = new Fragment_Cliente();
                                fragmentTransaction = true;
                                break;
                            case R.id.documento:
                                fragment = new Fragment_Documento();
                                fragmentTransaction = true;
                                break;
                            case R.id.producto:
                                fragment = new Fragment_Producto();
                                fragmentTransaction = true;
                                break;
                            case R.id.perfil:
                                fragment = new Fragment_Perfil();
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

        /*Bundle bundle = new Bundle();
        bundle.putString("codigo", codigo);
        Fragment_Inicio myFragment = new Fragment_Inicio ();
//Agrega bundle como argumento al fragment.
        myFragment.setArguments(bundle);

        Log.i("CODIGO","DATO DEL BUNDLE "+myFragment.getArguments());*/

    }

    //FUNCION QUE RECIBIRA DATOS DEL SCAN
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            Log.d("Inicio", "Tenemos data");

            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Log.i("SCAN", "DATOS DEL SCANEO " + scanContent + " y " + scanFormat);
            if (scanContent == null || scanFormat == null) {
                Log.i("AVISO", "No registro ningun producto");
            }else{

            String[] data = sql.consultar_detalleBD(scanContent, helper);

            Log.i("DATO", "ARRAY FINAL" + Arrays.toString(data));
            //Fragment_Producto fp=new Fragment_Producto();

            String codbarra = data[0].toString();
            String idarticulo = data[1].toString();
            String nombre = data[2].toString();
            String almacen = data[3].toString();
            String cantidad = data[4].toString();

            //REvisar pasar datos de un array a un string
            Log.i("LOG_TAG", "codbarra: " + codbarra +
                    ", idarticulo: " + idarticulo +
                    ", nombre: " + nombre +
                    ", almacen: " + almacen +
                    ", cantidad: " + cantidad);

            Bundle bundle = new Bundle();//art.getNombre()
            bundle.putString("codbarra", codbarra);
            bundle.putString("idarticulo", idarticulo);
            bundle.putString("nombre", nombre);
            bundle.putString("almacen", almacen);
            bundle.putString("cantidad", cantidad);//String.valueOf(art.getCod_barra())}


            if (bundle != null) {

                //llamamos al metodo newInstance y le pasamos el bundle con datos
                Fragment_Producto.newInstance(bundle);
                Log.d("Fragment_Producto", "Tenemos data: " + bundle + ", " + bundle.getString("contenido") + ", " + bundle.getString("formato"));

            } else {
                Log.d("Fragment_Producto", "No hay data");

            }
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onFragmentListener(Bundle parameters) {

    }
}