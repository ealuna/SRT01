package com.example.rusbellgutierrez.SRT.Activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Sesion;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Cliente;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Dato;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Perfil;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Producto;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Ruta;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.R;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Helper;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Sentencias;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;


public class Activity_Cuerpo extends AppCompatActivity implements OnFragmentListener {

    //elementos para crear un menu_drawer layout
    ListView lista_drawer;
    NavigationView nav;
    DrawerLayout drawer;
    //toolbar
    Toolbar toolbar;
    //boton del toolbar
    Context context=this;
    ActionBarDrawerToggle drawerToggle;

    OnFragmentListener mCallback=null;

    //Volley_Peticiones vp= new Volley_Peticiones();

    String ip_trabajo_lap="192.168.1.128:80";
    String ip_trabajo_pc="192.168.1.62:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";

    //para mysql
    //String url_verificar="http://"+ip_casa+"/ejemplologin/obtener.php?codigo=";

    SQL_Sentencias sql=new SQL_Sentencias();
    SQL_Helper helper=new SQL_Helper(this);

    //Int para guardar la posicion
    int position=0;


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
        TextView oculto= (TextView)header.findViewById(R.id.oculto);
        //seteamos
        nom_usuario.setText(nombre);
        cod_usuario.setText(codigo);

        //vp.consultarDetalle(url_verificar + codigo, this, oculto);

        Log.i("CODIGO","DATO DEL CODIGO "+cod_usuario.getText().toString());

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //color al title
        toolbar.setTitleTextColor(Color.BLACK);
        //soporte para toolbar
        setSupportActionBar(toolbar);
        //muestra el nombre de la apliación
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Aplicar el boton para desplegar el menu_drawer
        drawerToggle = new ActionBarDrawerToggle(this,  drawer, toolbar,R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.truck_fast);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();

        final Bundle bundle = new Bundle();
        bundle.putString("codigo", codigo);
        //bundle.putString("respuesta",oculto.getText().toString());
        //Fragment_Dato myFragment = new Fragment_Dato ();
//Agrega bundle como argumento al fragment.
        //myFragment.setArguments(bundle);

        //enviar el codigo al fragment de busqueda


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
                                position=0;
                                finish();
                                startActivity(getIntent());
                                break;
                            case R.id.data:
                                position=1;
                                fragment = new Fragment_Dato();
                                fragment.setArguments(bundle);
                                fragmentTransaction = true;
                                break;
                            case R.id.ruta:
                                position=2;
                                fragment = new Fragment_Ruta();
                                fragmentTransaction = true;
                                break;
                            case R.id.cliente:
                                position=3;
                                fragment = new Fragment_Cliente();
                                fragmentTransaction = true;
                                break;
                            case R.id.producto:
                                position=5;
                                fragment = new Fragment_Producto();
                                fragmentTransaction = true;
                                break;
                            case R.id.perfil:
                                position=6;
                                fragment = new Fragment_Perfil();
                                fragmentTransaction = true;
                                break;
                            case R.id.sesion:
                                new Dialogo_Sesion().show(getSupportFragmentManager(), "SimpleDialog");
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

            if (data[0]==null){
                //si el array es null, en lugar de lanzar error, redirige al fragment
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment_Producto fp=new Fragment_Producto();
                ft.replace(R.id.content_frame, fp);
                ft.addToBackStack(null);
                ft.commit();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Producto no pertenece al pedido", Toast.LENGTH_SHORT);
                toast.show();

                Log.i("DATO", "ARRAY FINAL" + Arrays.toString(data));
            }else{
            Log.i("DATO", "ARRAY FINAL" + Arrays.toString(data));
            //Fragment_Producto fp=new Fragment_Producto();

            String codbarra = data[0].toString();
            String idarticulo = data[1].toString();
            String nombre = data[2].toString();
            String almacen = data[3].toString();
            //String cantidad = data[4].toString();
            String caja = data[4].toString();
            String unidad = data[5].toString();

            //REvisar pasar datos de un array a un string
            Log.i("LOG_TAG", "codbarra: " + codbarra +
                    ", idarticulo: " + idarticulo +
                    ", nombre: " + nombre +
                    ", almacen: " + almacen +
                    ", caja: " + caja +
                    ", unidad: " + unidad);

            Bundle bundle = new Bundle();//art.getNombre()
            bundle.putString("codbarra", codbarra);
            bundle.putString("idarticulo", idarticulo);
            bundle.putString("nombre", nombre);
            bundle.putString("almacen", almacen);
            bundle.putString("caja", caja);
            bundle.putString("unidad", unidad);
            //bundle.putString("cantidad", cantidad);//String.valueOf(art.getCod_barra())}


            if (bundle != null) {

                //llamamos al metodo newInstance y le pasamos el bundle con datos
                //Fragment_Producto.newInstance(bundle);
                //codigo opcional para evitar fuga de memoria por contenedores statics
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment_Producto fp=new Fragment_Producto();
                ft.replace(R.id.content_frame, fp.newInstance(bundle));
                ft.addToBackStack(null);
                ft.commit();

                Log.d("Fragment_Producto", "Tenemos data: " + bundle + ", " + bundle.getString("contenido") + ", " + bundle.getString("formato"));

            } else {
                Log.d("Fragment_Producto", "No hay data");

            }}
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    //INICIO DE METODOS DE LA INTERFACE
    @Override
    public void onFragmentListener(Bundle parameters) {

    }

    @Override
    public void onSetTitle(String title) {
        toolbar.setTitle(title);
    }
    //FIN DE LOS METODOS DE LA INTERFACE

    //funcion para refrescar activity
    @Override
    public void onBackPressed(){

        if (position>0){
            finish();
            startActivity(getIntent());
        }else{
            new Dialogo_Sesion().show(getSupportFragmentManager(), "SimpleDialog");
        }
    }
}