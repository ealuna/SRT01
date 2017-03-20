package com.example.rusbellgutierrez.SRT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FragmentProducto extends Fragment implements FragmentIterationListener,View.OnClickListener{

    Button boton_escaner, boton_verificar;
    TextView cod_barra,cod_articulo,nom_articulo,almacen,cantidad;
    EditText can_real;
    CardView carta_articulo;
    ImageView fondo;

    //objeto de la interface que invoca los metodos de este
    private FragmentIterationListener mCallback=null;

    //metodo para intercambiar datos entre fragments y activitys con fragment
    @Override
    public void onFragmentIteration(Bundle parameters){

        //recibimos los datos del scan desde el activity por la interface

    }

    public FragmentProducto() {
        // Required empty public constructor
    }

    //Se adjunta el fragment con el activity para poder pasar datos entre ellos
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (FragmentIterationListener) context;
        }catch(Exception e){
            Log.e("ExampleFragment", "El Activity debe implementar la interfaz FragmentIterationListener");
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
            cantidad =(TextView)v.findViewById(R.id.cantidad);
            carta_articulo =(CardView)v.findViewById(R.id.carta_articulo);
            fondo =(ImageView)v.findViewById(R.id.fondo);

            boton_escaner =(Button)v.findViewById(R.id.boton_escaner);
            boton_verificar =(Button)v.findViewById(R.id.boton_verificar);
            can_real =(EditText)v.findViewById(R.id.can_real);
        }

        boton_escaner.setOnClickListener(this);
        boton_verificar.setOnClickListener(this);
        // Carga el layout en el fragment
        return v;
    }

    @Override
    public void onClick(View view){
        if (view.getId()== R.id.boton_escaner){

            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
            scanIntegrator.initiateScan();

        }else if (view.getId()== R.id.boton_verificar){

            //implementar la funcion para guardar en sqlite
            cod_barra.setText("");
            cod_articulo.setText("");
            nom_articulo.setText("");
            almacen.setText("");
            cantidad.setText("");
            can_real.setEnabled(false);
            boton_verificar.setEnabled(false);
        }
    }

    //aca se establecen estados de las vistas y parametros
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        can_real.setEnabled(false);
        boton_verificar.setEnabled(false);
    }

    //metodo que obtiene el dato del scaneo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);

        startActivityForResult(intent,requestCode);

        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Toast toast = Toast.makeText(getContext(),
                    scanContent+" "+scanFormat, Toast.LENGTH_LONG);
            toast.show();

            nom_articulo.setText(scanFormat);
            cod_barra.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

        //primero activamos los botones
        can_real.setEnabled(true);
        boton_verificar.setEnabled(true);
    }
}
