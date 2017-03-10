package com.example.rusbellgutierrez.proyecto_oriunda;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Rusbell Gutierrez on 10/03/2017.
 */

public class Dialogo_Alerta extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Salir del Sistema");
        builder.setMessage("Seguro que quiere salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Sale del sistema
                        Intent inicio=new Intent(getActivity(),AccesoActivity.class);
                        //limpia el intent para no regresar con back
                        startActivity(inicio.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                })//no realiza ninguna accion
                .setNegativeButton("No", null);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
