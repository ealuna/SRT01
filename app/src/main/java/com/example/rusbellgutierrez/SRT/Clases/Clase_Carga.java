package com.example.rusbellgutierrez.SRT.Clases;

import java.math.BigInteger;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Carga {

    private int idtransportista;
    private BigInteger idarticulo;
    private String almacen;
    private int caja;
    private int unidad;
    private String fecha;
    private int viaje;
    private String estado;

    public Clase_Carga(int idtransportista, BigInteger idarticulo, String almacen, int caja, int unidad, String fecha, int viaje,String estado) {
        this.idtransportista = idtransportista;
        this.idarticulo = idarticulo;
        this.almacen = almacen;
        this.caja = caja;
        this.unidad = unidad;
        this.fecha = fecha;
        this.viaje = viaje;
        this.estado=estado;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
