package com.example.rusbellgutierrez.proyecto_oriunda;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Button;

/**
 * Created by Rusbell Gutierrez on 10/03/2017.
 */

public class Dialogo_Alerta extends DialogFragment {

    public Dialogo_Alerta() {
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
            View v = inflater.inflate(R.layout.cuerpo_dialog, null);
            //le enviamos el view al builder del dialog
            builder.setView(v);

            Button ok = (Button) v.findViewById(R.id.ok);
            Button cancel = (Button) v.findViewById(R.id.cancel);

            cancel.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Crear Cuenta...
                            dismiss();
                        }
                    }
            );

            ok.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Sale del sistema
                            Intent inicio=new Intent(getActivity(),AccesoActivity.class);
                            //limpia el intent para no regresar con back
                            startActivity(inicio);
                            System.exit(0);
                        }
                    }

            );

            return builder.create();
        }
}
