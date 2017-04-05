package com.example.rusbellgutierrez.SRT.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rusbellgutierrez.SRT.R;

import org.w3c.dom.Text;

/**
 * Created by Russbell on 28/03/2017.
 */

public class Dialogo_Observacion extends DialogFragment{

    String estado="";
    int opc=0;
    TextView oculto;
    EditText obser,caja,uni;
    TextInputLayout obser_lay,caja_lay,uni_lay;
    LinearLayout lin_lay;

    public Dialogo_Observacion(){
    }

    @NonNull

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioDialog();
    }
    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createRadioDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //se infla el dialog con el layout creado
        View v = inflater.inflate(R.layout.dialog_opcion, null);
        obser=(EditText)v.findViewById(R.id.obser);
        caja=(EditText)v.findViewById(R.id.caja);
        uni=(EditText)v.findViewById(R.id.unidad);

        obser_lay=(TextInputLayout)v.findViewById(R.id.obser_lay);
        caja_lay=(TextInputLayout)v.findViewById(R.id.caja_lay);
        uni_lay=(TextInputLayout)v.findViewById(R.id.uni_lay);

        lin_lay=(LinearLayout)v.findViewById(R.id.lin_lay);

        /*para evitar null.pointer.exception al setear datos a un textview
        debemos declarar el textview como sigue...*/
        //est=new TextView(getContext());

        //llamamos al texview que se encuentra en el fragment principal
        oculto=(TextView)getActivity().findViewById(R.id.observacion_text);

        RadioGroup rg= (RadioGroup)v.findViewById(R.id.radio_group);

        //oculto.setText(".");
        //le enviamos el view al builder del dialog
        builder.setView(v);

        //agregar listener para radio group
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //RadioButton rb1= (RadioButton)group.findViewById(R.id.rb_completo);
                RadioButton rb2= (RadioButton)group.findViewById(R.id.rb_faltante);
                RadioButton rb3= (RadioButton)group.findViewById(R.id.rb_stock);
                RadioButton rb4= (RadioButton)group.findViewById(R.id.rb_dañado);

                //boolean op1=rb1.isChecked();
                boolean op2=rb2.isChecked();
                boolean op3=rb3.isChecked();
                boolean op4=rb4.isChecked();

                /*if (op1){

                    text.setEnabled(false);
                    text.setText("");
                    estado="Completo";
                    opc=1;

                }else */if (op2){

                    obser_lay.setVisibility(View.GONE);
                    lin_lay.setVisibility(View.VISIBLE);
                    estado="Faltan:";
                    opc=2;

                }else if (op3){

                    obser_lay.setVisibility(View.INVISIBLE);
                    lin_lay.setVisibility(View.GONE);
                    estado="Sin stock";
                    opc=3;

                }else if (op4){

                    lin_lay.setVisibility(View.GONE);
                    obser_lay.setVisibility(View.VISIBLE);
                    estado="Dañado:";
                    opc=4;

                }
            }
        });

        Button ok = (Button) v.findViewById(R.id.ok);
        Button cancel = (Button) v.findViewById(R.id.cancel);

        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // No hace nada
                        dismiss();
                    }
                }
        );

        ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //captura el string
                        if (estado!=null){
                            /*if (opc==1){
                                oculto.setText(estado);
                            }else */if (opc==2){
                                oculto.setText(estado+" "+caja.getText().toString()+" cajas y "+uni.getText().toString()+" unidades");
                            }else if (opc==3){
                                oculto.setText(estado);
                            }else if (opc==4){
                                oculto.setText(estado+" "+obser.getText().toString());
                            }
                            dismiss();
                            Log.i("TEXT OCULTO","TIENE DATA "+oculto.getText().toString());
                        }else {
                            Log.i("TEXT OCULTO","NO TIENE DATA");
                            dismiss();
                        }
                    }
                }

        );

        return builder.create();
    }
}
