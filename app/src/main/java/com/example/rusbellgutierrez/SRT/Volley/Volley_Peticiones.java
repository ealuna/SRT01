package com.example.rusbellgutierrez.SRT.Volley;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rusbellgutierrez.SRT.Activitys.Activity_Cuerpo;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Articulo;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Carga;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Transportista;
import com.example.rusbellgutierrez.SRT.Misc.Progress_Bar;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Helper;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Sentencias;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by Russbell on 22/03/2017.
 */

public class Volley_Peticiones {

    SQL_Sentencias sql=new SQL_Sentencias();

    public void Consulta(String URL, final Context context, final String contraseña, final FrameLayout frame, final Button button, final AlphaAnimation animation) {

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

                    String contra = ja.getJSONObject(0).getString("pass");
                    String cod = ja.getJSONObject(0).getString("idTransportista");
                    String nom = ja.getJSONObject(0).getString("nombreTransportista");
                    String cel = ja.getJSONObject(0).getString("telefonoTransportista");
                    String placa = ja.getJSONObject(0).getString("matriculaVehiculo");


                    /*String contra = ja.getString(0);
                    String cod=ja.getString(1);
                    String nom=ja.getString(2);
                    String cel=ja.getString(3);
                    String placa=ja.getString(4);*/


                    //PARA MYSQL
                    Clase_Transportista t=new Clase_Transportista(Integer.parseInt(cod),nom,cel,placa);

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
                        Intent intent = new Intent(context, Activity_Cuerpo.class);
                        intent.putExtra("nombre",t.getNom_transp());
                        intent.putExtra("codigo",String.valueOf(t.getIdtransportista()));
                        context.startActivity(intent);
                        ((Activity)context).finish();

                        Log.i("DATOS DE CODIGO","cod: "+cod+" y t.getIdTransportista: "+t.getIdtransportista());

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

    public void consultarDetalle(String URL, final Context context, final TextView oculto){
        Log.i("URL: ",URL);

        //final int[] data = {0};

        RequestQueue queue = Volley.newRequestQueue(context);

        //peticion para obtener la contraseña del usuario
        StringRequest requestDetTrans =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //declarando array JSON para mysql
                    JSONArray ja = new JSONArray(response);
                    int data=ja.length();
                    //data[0] = Integer.parseInt(ja.getString(0));

                    if(data>0/*data[0] >0*/){

                        oculto.setText("Tiene data");

                    }else if (data==0/*data[0] ==0*/){

                        oculto.setText("No tiene data");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context,"El código no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.print(error);
                Toast.makeText(context,"Error al validar datos ",Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(requestDetTrans);
    }

    public void guardarSQL(String URL, Context context,HashMap<String,String> parametros){
        Log.i("URL: ",URL);

        RequestQueue queue=Volley.newRequestQueue(context);

        JsonObjectRequest postDatos= new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(parametros), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(postDatos);
    }
}
