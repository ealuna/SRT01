package com.example.rusbellgutierrez.SRT.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rusbellgutierrez.SRT.Clases.Clase_Carga;
import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Observacion;
import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Sesion;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.R;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Sentencias;
import com.example.rusbellgutierrez.SRT.Volley.Volley_Peticiones;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONObject;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

public class Fragment_Producto extends Fragment implements View.OnClickListener{

    public static final String TAG="Producto";

    Button boton_escaner, boton_verificar;
    TextView cod_barra,cod_articulo,nom_articulo,almacen,caja,unidad;
    //invisibles
    TextView obs_text,viaje,transp;
    CheckBox ch_estado;
    CardView carta_articulo;

    SQL_Sentencias sql= new SQL_Sentencias();

    String codoculto="";

    //creamos un objetos de la interface
    OnFragmentListener mCallback=null;

    Volley_Peticiones vp=new Volley_Peticiones();

    //URL para conexion
    String ip_trabajo_lap="192.168.1.128:80";
    String ip_trabajo_pc="192.168.1.62:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";
    String ip_sql="192.168.1.204:80";

    //SQLSERVER
    String url_post="http://"+ip_sql+"/REST/obsCarga";

    /*NOTA: para poder usar los datos provenientes del bundle del activity, se usa este metodo static y de ahi se puede instanciar
    * o llamar o enviar SOLAMENTE a campos y metodos del tipo static*/

    /*NOTA2: usar views de tipo static ocasionan fuga de memoria*/
    public static Fragment_Producto newInstance(Bundle arguments){
        Fragment_Producto f = new Fragment_Producto();
        if(arguments != null){
            f.setArguments(arguments);
            Log.d("RESULTADO", "Tenemos data: "+f);
        }else {
            Log.d("RESULTADO", "No tenemos data");}
        return f;
    }

    public Fragment_Producto() {
        // Construstor para instanciar fragment, siempre vacio
    }


    /*COMENZAMOS CON LOS EVENTOS DEL FRAGMENT*/

    //Este metodo obtendrá los datos del activity_central a través del bundle
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //se inicia para obtener los datos del activity mediante bundle
        if(getArguments()!= null) {
            codoculto=getArguments().getString("codigo");
            Log.i("AVISO FRAGMENT_PRODUCTO","CODIGO "+codoculto);
            //oculto_cod.setText(String.valueOf(result));
        } else {
            Log.i("AVISO","No se obtuvo arguments");
            //oculto_cod.setText("result not included");
        }
    }

    //Se adjunta el fragment con el activity para poder pasar datos entre ellos
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //revisar el attach, original mCallback = (FragmentIterarionListener) context;
            mCallback = (OnFragmentListener) context;
        }catch(Exception e){
            Log.e("ExampleFragment", "El Activity debe implementar la interfaz OnFragmentListener");
        }
    }

    //En este metodo se instancia todos los elementos visuales
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v =  inflater.inflate(R.layout.fragment_producto, container, false);
        //condiciona para crear los objetos si es que existe el layout
        if(v != null){

            //para poder crear los elementos en fragment, se debe vincular a un view con el layout
            cod_barra =(TextView)v.findViewById(R.id.cod_barra);
            cod_articulo =(TextView)v.findViewById(R.id.cod_articulo);
            nom_articulo =(TextView)v.findViewById(R.id.nom_articulo);
            almacen =(TextView)v.findViewById(R.id.almacen);
            caja =(TextView)v.findViewById(R.id.caja);
            unidad=(TextView)v.findViewById(R.id.unidad);
            carta_articulo =(CardView)v.findViewById(R.id.carta_articulo);
            //campo invisible que obtiene el estado
            obs_text =(TextView)v.findViewById(R.id.observacion_text);
            //campo invisible que obtiene el viaje
            viaje =(TextView)v.findViewById(R.id.viaje);
            //campo invisible que obtiene el idtransportista
            transp=(TextView)v.findViewById(R.id.transp);

            boton_escaner =(Button)v.findViewById(R.id.boton_escaner);
            boton_verificar =(Button)v.findViewById(R.id.boton_verificar);
            ch_estado =(CheckBox)v.findViewById(R.id.ch_estado);

            obs_text.setText("Completo");
            //transp.setText(codoculto);

            ch_estado.setEnabled(false);

            //hacemos visible el menu
            setHasOptionsMenu(true);
        }

        boton_escaner.setOnClickListener(this);
        boton_verificar.setOnClickListener(this);

        ch_estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //llamamos al dialogo de observacion
                    new Dialogo_Observacion().show(getFragmentManager(),"RadioDialog");
                }
            }
        });
        // retornamos el view para visualizar el fragment
        return v;
    }

        //metodos onClick
            @Override
            public void onClick(View view){
                if (view.getId()== R.id.boton_escaner){

                    //scan
                    IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                    scanIntegrator.initiateScan();


                }else if (view.getId()== R.id.boton_verificar) {

                        Date d=new Date();
                        CharSequence fecha_sistema  = DateFormat.format("MMMM d, yyyy ", d.getTime());

                        //implementar la funcion para guardar en sqlite
                        //Clase_Carga carga=new Clase_Carga(transp.getText().toString(),cod_articulo.getText().toString(),almacen.getText().toString(),caja.getText().toString(),unidad.getText().toString(),fecha_sistema,viaje.getText().toString(),obs_text.getText().toString());

                        BigInteger codarticulo = new BigInteger(cod_articulo.getText().toString());
                        String alm =almacen.getText().toString();
                        String caj = caja.getText().toString();
                        String un =unidad.getText().toString();
                        String obs = obs_text.getText().toString();
                        String reparto = viaje.getText().toString();
                        String idtransp=transp.getText().toString();
                        //Clase_Carga car=new Clase_Carga()

                    Clase_Carga carga=new Clase_Carga(Integer.parseInt(idtransp), codarticulo,alm,Integer.parseInt(caj),Integer.parseInt(un),String.valueOf(fecha_sistema),Integer.parseInt(reparto),obs);

                        HashMap<String, String> parametros = new HashMap();
                        parametros.put("fecha",carga.getFecha());
                        parametros.put("numeroReparto",String.valueOf(carga.getViaje()));
                        parametros.put("idTransportista",String.valueOf(carga.getIdtransportista()));
                        parametros.put("almacen",carga.getAlmacen());
                        parametros.put("idArticulo",String.valueOf(carga.getIdarticulo()));
                        parametros.put("cajas",String.valueOf(carga.getCaja()));
                        parametros.put("unidades",String.valueOf(carga.getUnidad()));
                        parametros.put("cajasCargadas",null);
                        parametros.put("unidadesCargadas",null);
                        parametros.put("estado",carga.getEstado());
                        parametros.put("detalleObservacion",null);

                    //JSONObject jsondata= new JSONObject(parametros);

                    Log.i("DATOS A GUARDAR", "SON " + carga.getIdarticulo() + " Y " + carga.getEstado() + " EL VIAJE ES " + carga.getViaje()+" EL CODTRANSP ES "+carga.getIdtransportista());
                    Log.i("DATOS JSON","DATOS "+new JSONObject(parametros).toString());

                    //llamamos a la consulta que hará update a las tabla carga
                    boolean upd = sql.updateCarga(getActivity(), carga);

                    //sentencia para enviar a SQLSERVER
                    vp.guardarSQL(url_post,getActivity(),parametros);

                    if (upd) {

                        cod_barra.setText("");
                        cod_articulo.setText("");
                        nom_articulo.setText("");
                        almacen.setText("");
                        caja.setText("");
                        unidad.setText("");
                        viaje.setText("");
                        obs_text.setText("Completo");
                        ch_estado.setEnabled(false);
                        ch_estado.setChecked(false);
                        boton_verificar.setEnabled(false);

                        Log.i("DATOS", "SE GUARDARON");
                    } else {
                        Log.i("DATOS", "NO SE GUARDARON");
                    }
                }

            }

    //aca se establecen estados de las vistas y parametros
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onResume(){
        super.onResume();
        if(getArguments()!= null) {
            cod_barra.setText(getArguments().getString("codbarra"));
            cod_articulo.setText(getArguments().getString("idarticulo"));
            nom_articulo.setText(getArguments().getString("nombre"));
            almacen.setText(getArguments().getString("almacen"));
            //cantidad.setText(getArguments().getString("cantidad"));
            caja.setText(getArguments().getString("caja"));
            unidad.setText(getArguments().getString("unidad"));
            //recibimos el viaje
            viaje.setText(getArguments().getString("viaje"));
            transp.setText(getArguments().getString("idtransportista"));

        } else {
            Log.i("AVISO","No se obtuvo arguments");
        }
        if (cod_barra.getText().equals("")){

            ch_estado.setEnabled(false);
            boton_verificar.setEnabled(false);

        }else {

            ch_estado.setEnabled(true);
            boton_verificar.setEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_producto, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //funciona
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.m_buscar:

                mCallback.onSetTitle("Productos");

                /*Bundle bun =new Bundle();
                bun.putString("codtransp",codoculto);*/

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment_Busqueda fb=new Fragment_Busqueda();
                //fb.setArguments(bun);
                ft.replace(R.id.content_frame, fb);
                ft.addToBackStack(null);
                ft.commit();


                return true;
            case R.id.m_detalle:

                mCallback.onSetTitle("Productos");

                FragmentTransaction f = getFragmentManager().beginTransaction();
                Fragment_detBusq fdb=new Fragment_detBusq();
                f.replace(R.id.content_frame, fdb);
                f.addToBackStack(null);
                f.commit();


                return true;
            case R.id.m_inicio:

                mCallback.onSetTitle("S.R.T");
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                Fragment_Inicio fi=new Fragment_Inicio();
                ftr.replace(R.id.content_frame, fi);
                ftr.addToBackStack(null);
                ftr.commit();

                return true;
            case R.id.m_sesion:
                new Dialogo_Sesion().show(getFragmentManager(), "SimpleDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
