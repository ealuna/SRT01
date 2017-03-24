package com.example.rusbellgutierrez.SRT;

import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.rey.material.widget.ProgressView;
import com.rey.material.widget.RelativeLayout;

import org.w3c.dom.Text;

import java.io.InterruptedIOException;
import java.util.logging.LogRecord;

public class Fragment_Inicio extends Fragment implements Interface_FragmentListener,View.OnClickListener{

    private int progressStatus = 0;
    private Handler handler = new Handler();

    CardView carta;
    ProgressBar pb;
    static TextView tv,oculto_cod,contador;
    Button btn;
    String cod_transp="";

    Volley_Peticiones vp=new Volley_Peticiones();

    String data;

    private Interface_FragmentListener mCallback=null;

    //URL para conexion
    String ip_trabajo="192.168.1.128:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //para mysql
    String url_detalle="http://"+ip_trabajo+"/ejemplologin/detalle.php?codigo=";


    public static Fragment_Inicio newInstance(Bundle arguments){
        Fragment_Inicio f = new Fragment_Inicio();
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

    public Fragment_Inicio() {
        // Construstor para instanciar fragment, siempre vacio
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //revisar el attach, original mCallback = (FragmentIterarionListener) context;
            mCallback = (Interface_FragmentListener) context;
        }catch(Exception e){
            Log.e("ExampleFragment", "El Activity debe implementar la interfaz Interface_FragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_inicio, container, false);
        if (v!=null) {

            carta=(CardView)v.findViewById(R.id.carta);
            //iniciamos pblinear
            pb = (ProgressBar)v.findViewById(R.id.pb);
            tv=(TextView)v.findViewById(R.id.tv);
            btn = (Button)v.findViewById(R.id.btn);
            contador =(TextView)v.findViewById(R.id.contador);
            oculto_cod= (TextView)v.findViewById(R.id.oculto_cod);
            /*if(getArguments()!= null) {
                Float result=getArguments().getFloat("codigo");
                oculto_cod.setText(String.valueOf(result));
            } else {
                oculto_cod.setText("result not included");
            }*/

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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments()!= null) {
            cod_transp=getArguments().getString("codigo");
            //oculto_cod.setText(String.valueOf(result));
        } else {
            Log.i("AVISO","No se obtuvo arguments");
            //oculto_cod.setText("result not included");
        }
        //Bundle extras =this.getArguments();

        //cod_transp=extras.getString("codigo");
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                vp.Detalle(url_detalle+"100",getActivity());
            }
        }).start();*/
    }

    @Override
    public void onClick(View v) {

        progressStatus = 0;
        // Visible the progress bar and text view
        pb.setVisibility(View.VISIBLE);
        tv.setVisibility(View.VISIBLE);

        //SENTENCIA PARA EJECUTAR EN SEGUNDO PLANO
        new Thread(new Runnable() {
            @Override
            public void run() {

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        vp.Detalle(url_detalle+cod_transp,getActivity());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                    // If task execution completed

                        // Hide the progress bar from layout after finishing task
                        pb.setVisibility(View.GONE);
                        contador.setVisibility(View.VISIBLE);
                        // Set a message of completion
                        contador.setText("Datos de productos listo...");

                        }
                        });

                    // Update the progress bar
                   /* handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBar pb=new ProgressBar(getContext());
                            TextView tv=new TextView(getContext());
                            pb.setProgress(progressStatus);
                            // Show the progress on TextView
                            tv.setText(progressStatus+"");
                            // If task execution completed
                            if(progressStatus == 100){
                                // Hide the progress bar from layout after finishing task
                                pb.setVisibility(View.GONE);
                                // Set a message of completion
                                tv.setText("Operation completed...");
                            }
                        }
                    });*/

            }
        }).start();
        //FINAL
    }

    /*class Asincrono extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i=1; i<=10; i++) {
                vp.Detalle(url_detalle,getContext());

                        publishProgress(i*10);
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pb.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            pb.setMax(100);
            pb.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                Toast.makeText(getContext(), "Tarea finalizada!",
                        Toast.LENGTH_SHORT).show();
        }
    }*/
}