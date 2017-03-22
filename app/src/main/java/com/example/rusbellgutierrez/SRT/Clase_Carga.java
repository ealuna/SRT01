package com.example.rusbellgutierrez.SRT;

import java.util.Date;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Carga {

    private int idtransportista;
    private int idarticulo;
    private String almacen;
    private int cantidad;
    private Date fecha;

    public Clase_Carga() {

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
