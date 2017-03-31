package com.example.rusbellgutierrez.SRT.Dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.example.rusbellgutierrez.SRT.Activitys.Activity_Acceso;
import com.example.rusbellgutierrez.SRT.R;

/**
 * Created by Rusbell Gutierrez on 10/03/2017.
 */

public class Dialogo_Sesion extends DialogFragment {

    public Dialogo_Sesion() {
    }

    @NonNull


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            return createSimpleDialog();
    }
        /**
         * Crea un diálogo de alerta sencillo
         * @return Nuevo diálogo
         */
        public AlertDialog createSimpleDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            //se infla el dialog con el layout creado
            View v = inflater.inflate(R.layout.dialog_sesion, null);
            //le enviamos el view al builder del dialog
            builder.setView(v);

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
                            // Sale del sistema
                            Intent inicio=new Intent(getActivity(),Activity_Acceso.class);
                            //limpia el intent para no regresar con back
                            startActivity(inicio);
                            System.exit(0);
                        }
                    }

            );

            return builder.create();
        }
}
