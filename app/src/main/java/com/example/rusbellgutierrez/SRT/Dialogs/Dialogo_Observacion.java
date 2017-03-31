package com.example.rusbellgutierrez.SRT.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rusbellgutierrez.SRT.R;

/**
 * Created by Russbell on 28/03/2017.
 */

public class Dialogo_Observacion extends DialogFragment{

    String estado="";
    int opc=0;
    TextView est;
    EditText text;

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
        text=(EditText)v.findViewById(R.id.obser);
        /*para evitar null.pointer.exception al setear datos a un textview
        debemos declarar el textview como sigue...*/
        est=new TextView(getContext());
        RadioGroup rg= (RadioGroup)v.findViewById(R.id.radio_group);

        //le enviamos el view al builder del dialog
        builder.setView(v);

        //agregar listener para radio group
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb1= (RadioButton)group.findViewById(R.id.rb_completo);
                RadioButton rb2= (RadioButton)group.findViewById(R.id.rb_faltante);
                RadioButton rb3= (RadioButton)group.findViewById(R.id.rb_stock);
                RadioButton rb4= (RadioButton)group.findViewById(R.id.rb_dañado);

                boolean op1=rb1.isChecked();
                boolean op2=rb2.isChecked();
                boolean op3=rb3.isChecked();
                boolean op4=rb4.isChecked();

                if (op1){

                    text.setEnabled(false);
                    text.setText("");
                    estado="Completo";
                    opc=1;

                }else if (op2){

                    text.setHint("¿Cuanto tiene?");
                    text.setEnabled(true);
                    text.setInputType(InputType.TYPE_CLASS_NUMBER);
                    estado="Encontro:";
                    opc=2;

                }else if (op3){

                    text.setEnabled(false);
                    text.setText("");
                    estado="Sin stock";
                    opc=3;

                }else if (op4){

                    text.setHint("Indique");
                    text.setEnabled(true);
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
                            if (opc==1){
                                est.setText(estado);
                            }else if (opc==2){
                                est.setText(estado+" "+text.getText().toString());
                            }else if (opc==3){
                                est.setText(estado);
                            }else if (opc==4){
                                est.setText(estado+" "+text.getText().toString());
                            }
                            dismiss();
                            Log.i("TEXT OCULTO","TIENE DATA "+est.getText().toString());
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
