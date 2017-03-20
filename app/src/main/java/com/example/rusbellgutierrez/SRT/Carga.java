package com.example.rusbellgutierrez.SRT;

import java.util.Date;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Carga {

    private int idtransportista;
    private int idarticulo;
    private String almacen;
    private int cantidad;
    private Date fecha;

    public Carga(int idtransportista, int idarticulo, String almacen, int cantidad, Date fecha) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
