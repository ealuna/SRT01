package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry.cod_barra;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry.nombre;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry.idarticulo;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry.almacen;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry.cantidad;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry.fecha;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry.cel_transp;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry.idtransportista;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry.nom_transp;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry.placa;

/**
 * Created by Russbell on 21/03/2017.
 */

public class SQL_Sentencias {

    /*PARES DE CLAVE-VALOR*/

    public ContentValues toContentValues() {
        //contenedor de valores para articulo
        ContentValues val_art= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val_art.put(SQL_Columnas.ArticuloEntry.idarticulo,idarticulo);
        val_art.put(SQL_Columnas.ArticuloEntry.nombre,nombre);
        val_art.put(SQL_Columnas.ArticuloEntry.cod_barra,cod_barra);
        return val_art;
    }

    public ContentValues ContTra() {
        //contenedor de valores para transportista
        ContentValues val_tra= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val_tra.put(SQL_Columnas.TransportistaEntry.idtransportista,idtransportista);
        val_tra.put(SQL_Columnas.TransportistaEntry.nom_transp,nom_transp);
        val_tra.put(SQL_Columnas.TransportistaEntry.cel_transp,cel_transp);
        val_tra.put(SQL_Columnas.TransportistaEntry.placa,placa);
        return val_tra;
    }

    public ContentValues ContCar() {
        //contenedor de valores para carga
        ContentValues val_car= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val_car.put(idtransportista,idtransportista);
        val_car.put(idarticulo,idarticulo);
        val_car.put(almacen,almacen);
        val_car.put(cantidad,cantidad);
        val_car.put(fecha,fecha);
        return val_car;
    }

}
