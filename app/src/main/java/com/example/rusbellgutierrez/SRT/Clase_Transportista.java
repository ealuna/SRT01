package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry.*;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Transportista {

    private int idtransportista;
    private String nom_transp;
    private int cel_transp;
    private String placa;

    public Clase_Transportista(int idtransportista, String nom_transp, int cel_transp, String placa) {
        this.idtransportista = idtransportista;
        this.nom_transp = nom_transp;
        this.cel_transp = cel_transp;
        this.placa = placa;
    }

    public int getIdtransportista() {
        return idtransportista;
    }

    public void setIdtransportista(int idtransportista) {
        this.idtransportista = idtransportista;
    }

    public String getNom_transp() {
        return nom_transp;
    }

    public void setNom_transp(String nom_transp) {
        this.nom_transp = nom_transp;
    }

    public int getCel_transp() {
        return cel_transp;
    }

    public void setCel_transp(int cel_transp) {
        this.cel_transp = cel_transp;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /*PARES DE CLAVE-VALOR*/
    public ContentValues toContentValues() {
        //contenedor de valores para transportista
        ContentValues val= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val.put(SQL_Columnas.TransportistaEntry.idtransportista,idtransportista);
        val.put(SQL_Columnas.TransportistaEntry.nom_transp,nom_transp);
        val.put(SQL_Columnas.TransportistaEntry.cel_transp,cel_transp);
        val.put(SQL_Columnas.TransportistaEntry.placa,placa);
        return val;
    }
}
