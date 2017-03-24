package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import java.util.Date;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Carga {

    private int idtransportista;
    private int idarticulo;
    private String almacen;
    private int cantidad;
    private String fecha;
    private int viaje;
    private int estado;

    public Clase_Carga(int idtransportista, int idarticulo, String almacen, int cantidad, String fecha, int viaje,int estado) {
        this.idtransportista = idtransportista;
        this.idarticulo = idarticulo;
        this.almacen = almacen;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.viaje = viaje;
        this.estado=estado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getViaje() {
        return viaje;
    }

    public void setViaje(int viaje) {
        this.viaje = viaje;
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
}
