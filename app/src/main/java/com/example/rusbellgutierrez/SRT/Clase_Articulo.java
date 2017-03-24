package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import java.math.BigInteger;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Articulo {

    private BigInteger idarticulo;
    private String nombre;
    private BigInteger codbarra;

    public Clase_Articulo(BigInteger idarticulo, String nombre, BigInteger codbarra) {
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

    public BigInteger getCodbarra() {
        return codbarra;
    }

    public void setCod_barra(BigInteger codbarra) {
        this.codbarra = codbarra;
    }
}
