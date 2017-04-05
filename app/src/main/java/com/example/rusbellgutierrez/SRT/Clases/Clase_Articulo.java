package com.example.rusbellgutierrez.SRT.Clases;

import java.math.BigInteger;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Articulo {

    private BigInteger idarticulo;
    private String nombre;
    private String codbarra;

    public Clase_Articulo(BigInteger idarticulo, String nombre, String codbarra) {
        this.idarticulo = idarticulo;
        this.nombre = nombre;
        this.codbarra = codbarra;
    }

    public BigInteger getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(BigInteger idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodbarra() {
        return codbarra;
    }

    public void setCod_barra(String codbarra) {
        this.codbarra = codbarra;
    }
}
