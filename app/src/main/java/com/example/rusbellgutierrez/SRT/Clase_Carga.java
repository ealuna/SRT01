package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import java.math.BigInteger;
import java.util.Date;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Carga {

    private int idtransportista;
    private BigInteger idarticulo;
    private String almacen;
    private int cantidad;
    private String fecha;
    private int viaje;
    private String estado;

    public Clase_Carga(int idtransportista, BigInteger idarticulo, String almacen, int cantidad, String fecha, int viaje,String estado) {
        this.idtransportista = idtransportista;
        this.idarticulo = idarticulo;
        this.almacen = almacen;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.viaje = viaje;
        this.estado=estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public BigInteger getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(BigInteger idarticulo) {
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
