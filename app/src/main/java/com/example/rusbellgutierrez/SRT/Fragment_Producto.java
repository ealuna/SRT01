package com.example.rusbellgutierrez.SRT;

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

import java.lang.reflect.Array;

public class Fragment_Producto extends Fragment implements View.OnClickListener{

    Button boton_escaner, boton_verificar;
    private static TextView cod_barra,cod_articulo,nom_articulo,almacen,cantidad;
    EditText can_real;
    CardView carta_articulo;
    ImageView fondo;
    String format,content;

    //creamos un objetos de la interface
    private Interface_FragmentListener mCallback=null;

    /*NOTA: para poder usar los datos provenientes del bundle del activity, se usa este metodo static y de ahi se puede instanciar
    * o llamar o enviar SOLAMENTE a campos y metodos del tipo static*/
    public static Fragment_Producto newInstance(Bundle arguments){
        Fragment_Producto f = new Fragment_Producto();
        if(arguments != null){
            f.setArguments(arguments);
            cod_barra.setText(f.getArguments().getString("contenido"));
            nom_articulo.setText(f.getArguments().getString("formato"));
            Log.d("RESULTADO", "Tenemos data: "+f);
        }else {
            Log.d("RESULTADO", "No tenemos data");}
        return f;
    }

    public Fragment_Producto() {
        // Construstor para instanciar fragment, siempre vacio
    }


    /*COMENZAMOS CON LOS EVENTOS DEL FRAGMENT*/

    //Este metodo obtendrá los datos del activity_central a través del bundle
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        //realizaremos una condicional para ver si recibimos datos del actitivy_central
        /*if (getArguments()!=null){

            Bundle extras= this.getArguments();
            String contenido= extras.getString("contenido");
            String formato= extras.getString("formato");
            cod_barra.setText(extras.getString("contenido"));
            nom_articulo.setText(extras.getString("formato"));
            Toast toast = Toast.makeText(getContext(),
                    "contenido: "+contenido+" y formato: "+formato, Toast.LENGTH_LONG);
            toast.show();

            can_real.setEnabled(true);
            boton_verificar.setEnabled(true);

        }else {

        }*/
    }

    //Se adjunta el fragment con el activity para poder pasar datos entre ellos
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
        // retornamos el view para visualizar el fragment
        return v;

        /*if (Fragment_Producto.newInstance(savedInstanceState).getArguments()!=null){
            String hola=Fragment_Producto.newInstance(savedInstanceState).getArguments().getString("contenido");
        }*/
    }

        //metodos onClick
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

            can_real.setEnabled(true);
            boton_verificar.setEnabled(true);

    }
    @Override
    public void onResume(){
        super.onResume();
        if (cod_barra.getText().equals("")){

            can_real.setEnabled(false);
            boton_verificar.setEnabled(false);

        }else {

            can_real.setEnabled(true);
            boton_verificar.setEnabled(true);
        }
    }

    //quitamos el fragment del activity despues de ejecutar
    /*@Override
    public void onDetach(){
        mCallback=null;
        super.onDetach();
    }*/

}
