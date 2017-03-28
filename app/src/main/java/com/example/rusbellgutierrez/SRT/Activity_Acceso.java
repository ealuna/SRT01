package com.example.rusbellgutierrez.SRT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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


public class Activity_Acceso extends AppCompatActivity implements Interface_FragmentListener {

    static Button boton_acceso;
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

    Volley_Peticiones vp=new Volley_Peticiones();

    /**RECUERDA* SI VES QUE SALTA UN ERROR DE JAVA.LANG.NULL.POINTER ES POSIBLE QUE NECESITES INSTANCIAR UN OBJETO QUE ESTES USANDO*/



    //SQLITE
    Context context=this;
    SQL_Helper helper=new SQL_Helper(this);
    SQLiteDatabase db;
    SQL_Sentencias sql=new SQL_Sentencias();


    //declaraciones para la animacion
    static AlphaAnimation animation;
    //declaramos el framelayout
    static FrameLayout progressBarHolder;

    //URL para conexion
    String ip_trabajo_lap="192.168.1.128:80";
    String ip_trabajo_pc="192.168.1.62:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //para mysql
    String url_pass_nom="http://"+ip_trabajo_lap+"/ejemplologin/consultarusuario.php?codigo=";
    String url_detalle="http://"+ip_trabajo_lap+"/ejemplologin/detalle.php?codigo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        //BORRA LA BD, cada que inicie la aplicacion, se borra la base de datos
        this.context.deleteDatabase("DB_SRT");

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
        if (saveLogin) {
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
                        loginPrefsEditor.apply();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    //enviamos el campo del edittext a un string
                String pass=contraseña.getText().toString();


                   //aca se inicia la URL para conectar con el JSON


                vp.Consulta(url_pass_nom+codigo.getText().toString(),context,pass,progressBarHolder,boton_acceso,animation);

            }
        });
    }

    @Override
    public void onFragmentListener(Bundle parameters) {

    }
}
