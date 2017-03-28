package com.example.rusbellgutierrez.SRT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Russbell on 22/03/2017.
 */

 class Volley_Peticiones {

    SQL_Sentencias sql=new SQL_Sentencias();

     void Detalle(String url, final Context context){

        final SQL_Helper helper= new SQL_Helper(context);

        Log.i("url",""+url);

        //solicitud volley para realizar un get, cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(context);

        //peticion para obtener datos del json
        StringRequest requestDetalle =  new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("DATA","La data es "+response);
                try {

                    //declarando object JSON para mysql
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("detalle");

                    //int c=sql.existe_Registro(helper);

                    System.out.println("*****JARRAY*****  " + ja.length());

                    /*if (c>0){
                        Log.i("INFORMACION"," LA TABLA YA TIENE DATOS");
                    }else{*/

                    for(int i=0; i<ja.length(); i++){
                        JSONObject jdata = ja.getJSONObject(i);

                        Clase_Articulo art= new Clase_Articulo(BigInteger.valueOf(jdata.getInt("idarticulo")),jdata.getString("nombre"),BigInteger.valueOf(Long.parseLong(jdata.getString("codbarra"))));
                        Clase_Carga car= new Clase_Carga(jdata.getInt("idtransportista"),BigInteger.valueOf(jdata.getInt("idarticulo")),jdata.getString("almacen"),jdata.getInt("cantidad"),jdata.getString("fecha"),jdata.getInt("viaje"),jdata.getInt("estado"));
                        Log.i("DATOS DE CLASES","DATOS ART "+art.getIdarticulo()+", "+art.getNombre()+", "+art.getCodbarra()+"  "+
                        "DATOS CAR "+car.getIdtransportista()+", "+car.getIdarticulo()+", "+car.getAlmacen()+", "+car.getCantidad()+", "+car.getFecha()+", "+car.getViaje()+", "+car.getEstado());

                        Log.i("LOG_TAG", "idarticulo: " + jdata.getInt("idarticulo") +
                                ", nombre: " + jdata.getString("nombre") +
                                ", codbarra: " + jdata.getString("codbarra") +
                                ", idtransportista: " + jdata.getInt("idtransportista") +
                                ", almacen: " + jdata.getString("almacen") +
                                ", cantidad: " + jdata.getInt("cantidad") +
                                ", viaje: " + jdata.getInt("viaje") +
                                ", fecha: " + jdata.getString("fecha") +
                                ", estado: " + jdata.getInt("estado"));

                        sql.guardar_detalleBD(art,car,helper);

                        //Toast.makeText(context,"Los datos de los productos se cargaron",Toast.LENGTH_SHORT).show();
                    }
                //}

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context,"Error al cargar datos",Toast.LENGTH_LONG).show();
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

    void Consulta(String URL, final Context context, final String contraseña, final FrameLayout frame, final Button button, final AlphaAnimation animation) {

        final Progress_Bar pb= new Progress_Bar();

        final SQL_Helper helper= new SQL_Helper(context);

        Log.i("url",""+URL);
        //Log.i("url",""+URL_Nom);

        pb.Progreso_Pre(frame,button,animation);

        //solicitud volley para realizar un get, cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(context);

        //peticion para obtener la contraseña del usuario
        StringRequest requestDatos =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //declarando array JSON para mysql
                    JSONArray ja = new JSONArray(response);
                    String contra = ja.getString(0);
                    String cod=ja.getString(1);
                    String nom=ja.getString(2);
                    String cel=ja.getString(3);
                    String placa=ja.getString(4);


                    //PARA MYSQL
                    Clase_Transportista t=new Clase_Transportista(Integer.parseInt(cod),nom,Integer.parseInt(cel),placa);

                    //revisar si se instancio el objeto cuando salte el errror java.lang.null.point
                    sql.guardar_transportistaBD(t,helper);

                    /*INICIA
                    //declarando objeto JSON para sql server
                    array_json = new JSONObject(response);
                    //se agrego el campo .get().toString() para poder obtener el json de sql server
                    contra = array_json.get("0").toString();
                    nom.setNom_transp(array_json.get("1").toString());
                    TERMINA*/

                    if(contra.equals(contraseña)){

                        Toast.makeText(context,"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Activity_Central.class);
                        intent.putExtra("nombre",t.getNom_transp());
                        intent.putExtra("codigo",cod);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                        pb.Progreso_Post(frame,button,animation);

                    }else{

                        pb.Progreso_Post(frame,button,animation);

                        Toast.makeText(context,"Verifique la contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    pb.Progreso_Post(frame,button,animation);

                    Toast.makeText(context,"El código no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pb.Progreso_Post(frame,button,animation);

                System.out.print(error);
                Toast.makeText(context,"Error al validar datos ",Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(requestDatos);
    }
}
