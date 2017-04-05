package com.example.rusbellgutierrez.SRT.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Articulo;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Carga;
import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Alerta;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.R;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Helper;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Sentencias;
import com.example.rusbellgutierrez.SRT.Volley.Volley_Peticiones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;

public class Fragment_Dato extends Fragment implements OnFragmentListener,View.OnClickListener{

    private int progressStatus = 0;
    private Handler handler = new Handler();

    public static final String TAG="Dato";

    CardView carta;
    ProgressBar pb;
    TextView tv,oculto_resp,contador;
    FloatingActionButton btn;
    String cod_transp="";
    TextView fecha;

    Volley_Peticiones vp=new Volley_Peticiones();
    SQL_Sentencias sql=new SQL_Sentencias();

    private OnFragmentListener mCallback=null;

    //URL para conexion
    String ip_trabajo_lap="192.168.1.128:80";
    String ip_trabajo_pc="192.168.1.62:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";
    String ip_sql="192.168.1.204:80";

    //SQLSERVER
    String url_carga="http://"+ip_sql+"/REST/generarCarga/";

    //para mysql
    String url_detalle="http://"+ip_trabajo_lap+"/ejemplologin/detalle.php?codigo=";
    String url_verificar="http://"+ip_trabajo_lap+"/ejemplologin/obtener.php?codigo=";


    public static Fragment_Dato newInstance(Bundle arguments){
        Fragment_Dato f = new Fragment_Dato();
        if(arguments != null){
            f.setArguments(arguments);
            Log.d("RESULTADO", "Tenemos data: "+f);
            //oculto_cod.setText(f.getArguments().getString("codigo"));
            //contex=f.getArguments().get("context");
            //Log.d("RESULTADO", "Data: "+oculto_cod.getText().toString());
        }else {
            Log.d("RESULTADO", "No tenemos data");}
        return f;
    }

    public Fragment_Dato() {
        // Construstor para instanciar fragment, siempre vacio
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_dato, container, false);
        if (v!=null) {

            carta=(CardView)v.findViewById(R.id.carta);
            //iniciamos pblinear
            pb = (ProgressBar)v.findViewById(R.id.pb);
            tv=(TextView)v.findViewById(R.id.tv);
            btn = (FloatingActionButton) v.findViewById(R.id.btn);
            contador =(TextView)v.findViewById(R.id.contador);
            oculto_resp= (TextView)v.findViewById(R.id.oculto_cod);
            fecha= (TextView)v.findViewById(R.id.fecha);

            //validacion para saber si tiene datos
            vp.consultarDetalle(url_carga + cod_transp, getActivity(), oculto_resp);

            //hacemos visible el menu
            //setHasOptionsMenu(true);

            //pequeña funcion para poner fecha
            fecha_Actual();

            btn.setOnClickListener(this);

        }

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onFragmentListener(Bundle parameters) {

    }

    @Override
    public void onSetTitle(String title) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //se inicia para obtener los datos del activity mediante bundle
        if(getArguments()!= null) {
            cod_transp=getArguments().getString("codigo");
            //respuesta=getArguments().getString("respuesta");
            //oculto_cod.setText(String.valueOf(result));
        } else {
            Log.i("AVISO","No se obtuvo arguments");
            //oculto_cod.setText("result not included");
        }
    }

    @Override
    public void onClick(View v) {

        //validacion para saber si tiene datos
        //vp.consultarDetalle(url_verificar + cod_transp, getActivity(), oculto_resp);

        if (oculto_resp.getText().toString().equals("No tiene data")) {

            new Dialogo_Alerta().show(getFragmentManager(),"SimpleAlert");

        } else if (oculto_resp.getText().toString().equals("Tiene data")) {

        int c = sql.existe_Registro(getContext());

        //instruccion para verificar la tabla antes de hacer una peticion volley
            if (c > 0) {
                Hilo();
            } else {

                pb.setVisibility(View.VISIBLE);
                Detalle(url_carga + cod_transp, getActivity());
            }
        }
    }
    public void Hilo(){
        // Visible the progress bar and text view
        pb.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);

        //SENTENCIA PARA EJECUTAR EN SEGUNDO PLANO
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Try to sleep the thread for 20 milliseconds
                try {
                    Log.i("AVISO", ":D");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the progress bar from layout after finishing task
                        pb.setVisibility(View.GONE);
                        contador.setVisibility(View.VISIBLE);
                        contador.setTextColor(Color.BLUE);
                        // Set a message of completion
                        contador.setText("Productos fueron cargados...");

                    }
                });

            }
        }).start();
    }

    //METODO VOLLEY  PARA OBTENER EL DETALLE (POR EL MOMENTO DEBE DECLARARSE EN EL FRAGMENT)
    public void Detalle(String url, Context context){

        final SQL_Helper helper= new SQL_Helper(context);

        Log.i("url",""+url);
        //solicitud volley para realizar un get, cola de peticiones
        final RequestQueue queue = Volley.newRequestQueue(context);
        //peticion para obtener datos del json
        StringRequest requestDetalle =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("DATA","La data es "+response);
                try {
                    //SQLSERVER
                    JSONArray jo = new JSONArray(response);
                    for(int i=0; i<jo.length(); i++){
                        JSONObject jdata = jo.getJSONObject(i);

                        final Clase_Articulo art= new Clase_Articulo(BigInteger.valueOf(jdata.getInt("idArticulo")),jdata.getString("descripcionArticulo"),jdata.getString("codigoBarraUnidad"));
                        final Clase_Carga car= new Clase_Carga(jdata.getInt("idTransportista"),BigInteger.valueOf(jdata.getInt("idArticulo")),jdata.getString("almacen"),jdata.getInt("cajas"),jdata.getInt("unidades"),jdata.getString("fechaDocumento"),jdata.getInt("numeroReparto"),"0");//jdata.getString("estado")

                        //SE DEBE EJECUTAR EL HILO EN SEGUNDO PLANO
                        pb.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                boolean estado=sql.guardar_detalleBD(art,car, helper);
                                //SI "estado" ES TRUE, EJECUTA EL HANDLER
                                if (estado){
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            // If task execution completed

                                            // Hide the progress bar from layout after finishing task
                                            pb.setVisibility(View.GONE);
                                            contador.setVisibility(View.VISIBLE);
                                            contador.setText("Datos de productos listo...");

                                        }
                                    });
                                }

                            }
                        }).start();
                    }Log.i("JSON CANTIDAD","ES "+jo.length());

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(),"Error al cargar datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ERROR_VOLLEY","El error es "+error);

            }
        });
        queue.add(requestDetalle);
    }


    public void fecha_Actual() {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        int mes = c.get(Calendar.MONTH); //obtenemos el mes

        //Los meses se presentan del 0 al 11, representando el cero a Enero y el once a diciembre, por lo cual para su presentación

        //sumaremos 1 a la variable entera MES.
        mes = mes + 1;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        if (String.valueOf(dia).length()==1){
            if (String.valueOf(mes).length() == 1) {
                // obtemos el día.
                fecha.setText("0"+dia + " : 0" + mes + " : " + anio);
            } else {
                fecha.setText("0"+dia + " : " + mes + " : " + anio); //cambiamos el texto que tiene el TextView por la fecha actual.
            }
        }else {
            if (String.valueOf(mes).length() == 1) {
                // obtemos el día.
                fecha.setText(dia + " : 0" + mes + " : " + anio);
            } else {
                fecha.setText(dia + " : " + mes + " : " + anio); //cambiamos el texto que tiene el TextView por la fecha actual.
            }
        }

    }
}