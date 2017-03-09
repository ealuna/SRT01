package com.example.rusbellgutierrez.proyecto_oriunda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class AccesoActivity extends AppCompatActivity {

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
    JSONArray array_json;


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
                ConsultaPass("http://10.0.2.2/ejemplologin/consultarusuario.php?codigo="+codigo.getText().toString());
            }
        });

    }
    public void ConsultaPass(String URL) {
        Log.i("url",""+URL);

        //solicitud volley para realizar un get, cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    array_json = new JSONArray(response);
                    String contra = array_json.getString(0);
                    if(contra.equals(contraseña.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccesoActivity.this, CuerpoActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"Verifique la contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"El código no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error al validar datos",Toast.LENGTH_LONG).show();
            }
        });

        //de esta forma, se agrega una peticion a la cola de peticiones
        queue.add(stringRequest);
    }
}
