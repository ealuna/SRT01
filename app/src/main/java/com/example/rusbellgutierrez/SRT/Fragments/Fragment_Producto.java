package com.example.rusbellgutierrez.SRT.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Observacion;
import com.example.rusbellgutierrez.SRT.Dialogs.Dialogo_Sesion;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class Fragment_Producto extends Fragment implements View.OnClickListener{

    public static final String TAG="Producto";

    Button boton_escaner, boton_verificar;
    TextView cod_barra,cod_articulo,nom_articulo,almacen,cantidad,obs_text,cod_oculto;
    CheckBox ch_estado;
    CardView carta_articulo;

    String codoculto="";

    //creamos un objetos de la interface
    OnFragmentListener mCallback=null;

    /*NOTA: para poder usar los datos provenientes del bundle del activity, se usa este metodo static y de ahi se puede instanciar
    * o llamar o enviar SOLAMENTE a campos y metodos del tipo static*/

    /*NOTA2: usar views de tipo static ocasionan fuga de memoria*/
    public static Fragment_Producto newInstance(Bundle arguments){
        Fragment_Producto f = new Fragment_Producto();
        if(arguments != null){
            f.setArguments(arguments);
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

        //se inicia para obtener los datos del activity mediante bundle
        if(getArguments()!= null) {
            codoculto=getArguments().getString("codigo");

            //oculto_cod.setText(String.valueOf(result));
        } else {
            Log.i("AVISO","No se obtuvo arguments");
            //oculto_cod.setText("result not included");
        }
    }

    //Se adjunta el fragment con el activity para poder pasar datos entre ellos
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //revisar el attach, original mCallback = (FragmentIterarionListener) context;
            mCallback = (OnFragmentListener) context;
        }catch(Exception e){
            Log.e("ExampleFragment", "El Activity debe implementar la interfaz OnFragmentListener");
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
            //campo invisible que obtiene el estado
            obs_text =(TextView)v.findViewById(R.id.observacion_text);

            boton_escaner =(Button)v.findViewById(R.id.boton_escaner);
            boton_verificar =(Button)v.findViewById(R.id.boton_verificar);
            ch_estado =(CheckBox)v.findViewById(R.id.ch_estado);



            //hacemos visible el menu
            setHasOptionsMenu(true);
        }

        boton_escaner.setOnClickListener(this);
        boton_verificar.setOnClickListener(this);

        ch_estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //llamamos al dialogo de observacion
                    new Dialogo_Observacion().show(getFragmentManager(), "RadioDialog");
                }
            }
        });
        // retornamos el view para visualizar el fragment
        return v;
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
                    ch_estado.setEnabled(false);
                    ch_estado.setChecked(false);
                    boton_verificar.setEnabled(false);
                }
            }

    //aca se establecen estados de las vistas y parametros
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onResume(){
        super.onResume();
        if(getArguments()!= null) {
            cod_barra.setText(getArguments().getString("codbarra"));
            cod_articulo.setText(getArguments().getString("idarticulo"));
            nom_articulo.setText(getArguments().getString("nombre"));
            almacen.setText(getArguments().getString("almacen"));
            cantidad.setText(getArguments().getString("cantidad"));

        } else {
            Log.i("AVISO","No se obtuvo arguments");
        }
        if (cod_barra.getText().equals("")){

            ch_estado.setEnabled(false);
            boton_verificar.setEnabled(false);

        }else {

            ch_estado.setEnabled(true);
            boton_verificar.setEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_producto, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //funciona
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.m_buscar:

                mCallback.onSetTitle("Productos");

                /*Bundle bun =new Bundle();
                bun.putString("codtransp",codoculto);*/

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment_Busqueda fb=new Fragment_Busqueda();
                //fb.setArguments(bun);
                ft.replace(R.id.content_frame, fb);
                ft.addToBackStack(null);
                ft.commit();


                return true;
            case R.id.m_inicio:

                mCallback.onSetTitle("S.R.T");
                FragmentTransaction f = getFragmentManager().beginTransaction();
                Fragment_Inicio fi=new Fragment_Inicio();
                f.replace(R.id.content_frame, fi);
                f.addToBackStack(null);
                f.commit();

                return true;
            case R.id.m_sesion:
                new Dialogo_Sesion().show(getFragmentManager(), "SimpleDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
