package com.example.rusbellgutierrez.SRT.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rusbellgutierrez.SRT.Network.ConectivityReceiver;
import com.example.rusbellgutierrez.SRT.R;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Helper;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Sentencias;
import com.example.rusbellgutierrez.SRT.Volley.Volley_Peticiones;


public class Activity_Acceso extends AppCompatActivity implements ConectivityReceiver.ConnectivityReceiverListener {

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
    String ip_sql="192.168.1.204:80";

    //para mysql
    //String url_pass_nom="http://"+ip_trabajo_lap+"/ejemplologin/consultarusuario.php?codigo=";

    //para SQLSERVER
    String url_pass_nom="http://"+ip_sql+"/REST/usuarioTransporte/";
    String pass;

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

        contraseña.setInputType(InputType.TYPE_CLASS_NUMBER);

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
                pass=contraseña.getText().toString();

                recordar.setEnabled(false);
                boton_acceso.setEnabled(false);

                   //aca se inicia la URL para conectar con el JSON
                //vp.Consulta(url_pass_nom+codigo.getText().toString(),context,pass,progressBarHolder,boton_acceso,animation);

                //metodo que verifica la conexion a internet antes de hacer algo
                checkConexion();

                recordar.setEnabled(true);
                boton_acceso.setEnabled(true);


            }
        });
    }

    /*@Override
    public void onResume(){
        super.onResume();
        VerifNetwork.getInstance().setConnectivityListener(this);
    }*/

    //permite verificar la conexion a internet, todos los metodos que se declaran lineas abajo
    private void checkConexion(){
        boolean isConnected= ConectivityReceiver.isConnected(this);
        accionesNetwork(isConnected);
    }

    private void accionesNetwork(boolean isConnected){
        String mesg=null;
        int color=0;
        if (isConnected){
            vp.Consulta(url_pass_nom+codigo.getText().toString(),context,pass,progressBarHolder,boton_acceso,animation);
        }else {

            mesg="No existe conexion a internet";
            color= Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.carta), mesg, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    public void onNetworkConnectionChanged(boolean isConnected) {
        String msg=null;
        int color=0;
        if(isConnected){
            String nada="nada";
        }else {
            msg="No existe conexion a internet";
            color= Color.WHITE;
        }
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.carta), msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}
