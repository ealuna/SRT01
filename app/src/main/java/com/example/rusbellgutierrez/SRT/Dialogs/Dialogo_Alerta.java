package com.example.rusbellgutierrez.SRT.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.rusbellgutierrez.SRT.R;

/**
 * Created by Russbell on 30/03/2017.
 */

public class Dialogo_Alerta extends DialogFragment {

    public Dialogo_Alerta() {
    }

    @NonNull

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleAlert();
    }
    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //se infla el dialog con el layout creado
        View v = inflater.inflate(R.layout.dialog_alert, null);
        //le enviamos el view al builder del dialog
        builder.setView(v);

        Button ok = (Button) v.findViewById(R.id.aceptar);

        ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }

        );

        return builder.create();
    }
}
