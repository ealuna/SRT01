package com.example.rusbellgutierrez.SRT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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


public class AccesoActivity extends AppCompatActivity {

    Transportista nom;

    Button boton_acceso;
    ImageView logo;
    CheckBox recordar;
    EditText codigo, contraseña;
    CardView carta;
    private String cod_recordar;
    //clases necesarias para realizar checkbox "remember me"
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    //JSON array para obtener los datos devueltos por el JSON
    JSONArray ja;
    //JSON object para obtener los datos del sql server
    JSONObject array_json;

    //declaraciones para la animacion
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    //declaramos el framelayout
    FrameLayout progressBarHolder;

    //URL para conexion
    String ip="192.168.1.176:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";
    //String url_pass_nom="http://10.0.2.2/ejemplologin/index.php?codigo=";
    //String url_pass_nom="http://"+ip_geny+"/ejemplologin/index.php?codigo=";

    //para mysql
    String url_pass_nom="http://"+ip_geny+"/ejemplologin/consultarusuario.php?codigo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);


        boton_acceso =(Button)findViewById(R.id.boton_acceso);
        logo =(ImageView) findViewById(R.id.logo);
        recordar =(CheckBox)findViewById(R.id.recordar);
        codigo =(EditText)findViewById(R.id.codigo);
        contraseña =(EditText)findViewById(R.id.contraseña);
        carta =(CardView)findViewById(R.id.carta);
        //declaramos el framelayout
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        //aca inicia la funciòn para recordar datos
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            codigo.setText(loginPreferences.getString("codigo", ""));
            recordar.setChecked(true);
        }
        //termina la declaracion para capturar el dato del edittext

        //inicia la accion del evento onclick
        boton_acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(codigo.getWindowToken(), 0);

                    cod_recordar = codigo.getText().toString();

                    //cuando esta checked, guarda los datos en loginPrefsEditor
                    if (recordar.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("codigo", cod_recordar);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                   //aca se inicia la URL para conectar con el JSON

                //la dirección 10.0.3.2 hace referencia al emulador de genymotion, puede variar
                //ConsultaPass("http://10.0.3.2/ejemplologin/consultarusuario.php?codigo="+codigo.getText().toString());

                //configuracion para emulador android, modificar conexion remota
                Consulta(url_pass_nom+codigo.getText().toString());
                //configuracion para emulador android, CONEXION REMOTA
                //ConsultaPass("http://10.0.2.2/ejemplologin/conectar_sql.php?codigo="+codigo.getText().toString());
            }
        });


    }

    public void Progreso_Pre(){

        //experimental-->simulando metodo OnPreExecute
        boton_acceso.setEnabled(false);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
        //termina metodo OnPreExecute
    }

    public void Progreso_Post(){

        //experimental-->simulando metodo OnPostExecute
                outAnimation = new AlphaAnimation(1f, 0f);
                outAnimation.setDuration(200);
                progressBarHolder.setAnimation(outAnimation);
                progressBarHolder.setVisibility(View.GONE);
                boton_acceso.setEnabled(true);
                //termina metodo OnPostExecute
    }

    public void Consulta(String URL) {

        Log.i("url",""+URL);
        //Log.i("url",""+URL_Nom);

        Progreso_Pre();

        //solicitud volley para realizar un get, cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this);

        //peticion para obtener la contraseña del usuario
        StringRequest requestDatos =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {//error no se puede convertir string a JSON array

                    //declarando array JSON para mysql
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);

                    nom = new Transportista(100,"Jose Perez",986861992,"A123");

                    //declarando objeto JSON para sql server
                    //array_json = new JSONObject(response);
                    //se agrego el campo .get().toString() para poder obtener el json de sql server
                    //String contra = array_json.get("0").toString();
                    //nom.setNom_transp(array_json.get("1").toString());

                    if(contra.equals(contraseña.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccesoActivity.this, CuerpoActivity.class);
                        intent.putExtra("nombre",nom.getNom_transp());
                        startActivity(intent);
                        finish();

                        Progreso_Post();

                    }else{

                        Progreso_Post();

                        Toast.makeText(getApplicationContext(),"Verifique la contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Progreso_Post();

                    Toast.makeText(getApplicationContext(),"El código no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Progreso_Post();

                Toast.makeText(getApplicationContext(),"Error al validar datos",Toast.LENGTH_LONG).show();
            }
        });


       /* //Este stringRequest obtiene el nombre del usuario, falta tratar los datos obtenidos (eliminar espacios en blanco, codigos, etc)
        StringRequest requestNom =  new StringRequest(Request.Method.GET, URL_Nom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {//error no se puede convertir string a JSON array

                    //declarando array JSON para mysql
                    //ja = new JSONArray(response);
                    //String contra = ja.getString(0);

                    //declarando objeto JSON para sql server
                    aj_nom = new JSONObject(response);
                    //se agrego el campo .get().toString() para poder obtener el json de sql server
                    String nombre = aj_nom.get("0").toString();

                    Intent datos = new Intent (AccesoActivity.this, CuerpoActivity.class);
                    datos.putExtra("nombre", nombre);
                    startActivity(datos);

                        Progreso_Post();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Progreso_Post();

                    Toast.makeText(getApplicationContext(),"El código no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Progreso_Post();

                Toast.makeText(getApplicationContext(),"Error al validar datos",Toast.LENGTH_LONG).show();
            }
        });

        //de esta forma, se agrega una peticion a la cola de peticiones
        queue.add(requestNom);*/
        queue.add(requestDatos);
    }
}
