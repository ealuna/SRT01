package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import java.util.Date;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry.*;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Carga {

    private int idtransportista;
    private int idarticulo;
    private String almacen;
    private int cantidad;
    private String fecha;

    public Clase_Carga(int idtransportista, int idarticulo, String almacen, int cantidad, String fecha) {
        this.idtransportista = idtransportista;
        this.idarticulo = idarticulo;
        this.almacen = almacen;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getIdtransportista() {
        return idtransportista;
    }

    public void setIdtransportista(int idtransportista) {
        this.idtransportista = idtransportista;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /*PARES DE CLAVE-VALOR*/
    public ContentValues toContentValues() {
        //contenedor de valores para carga
        ContentValues val= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val.put(SQL_Columnas.CargaEntry.idtransportista,idtransportista);
        val.put(SQL_Columnas.CargaEntry.idarticulo,idarticulo);
        val.put(SQL_Columnas.CargaEntry.almacen,almacen);
        val.put(SQL_Columnas.CargaEntry.cantidad,cantidad);
        val.put(SQL_Columnas.CargaEntry.fecha,fecha);
        return val;
    }
}
