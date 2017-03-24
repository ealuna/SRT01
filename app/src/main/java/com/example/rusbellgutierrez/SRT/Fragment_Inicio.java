package com.example.rusbellgutierrez.SRT;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

public class Fragment_Inicio extends Fragment implements Interface_FragmentListener{

    ProgressBar pblinear;
    Volley_Peticiones vp=new Volley_Peticiones();

    //URL para conexion
    String ip_trabajo="192.168.1.128:80";
    String ip_casa="192.168.0.101:80";
    String ip_geny="10.0.3.2";
    String ip_android="10.0.2.2";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //String url_pass_nom="http://"+ip+"/ejemplologin/index.php?codigo=";

    //para mysql
    String url_detalle="http://"+ip_casa+"/ejemplologin/detalle.php?codigo=";


    public static Fragment_Inicio newInstance(Bundle arguments){
        Fragment_Inicio f = new Fragment_Inicio();
        if(arguments != null){
            f.setArguments(arguments);
            Log.d("RESULTADO", "Tenemos data: "+f);
        }else {
            Log.d("RESULTADO", "No tenemos data");}
        return f;
    }

    public Fragment_Inicio() {
        // Construstor para instanciar fragment, siempre vacio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_producto, container, false);
        if (v!=null) {
            //iniciamos pblinear
            pblinear = (ProgressBar) v.findViewById(R.id.pblinear);

        }

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vp.Detalle(url_detalle,getContext());
    }

    @Override
    public void onFragmentListener(Bundle parameters) {

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

            pblinear.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            pblinear.setMax(100);
            pblinear.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                Toast.makeText(getContext(), "Tarea finalizada!",
                        Toast.LENGTH_SHORT).show();
        }
    }*/
}