package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Articulo {

    private int idarticulo;
    private String nombre;
    private int codbarra;

    public Clase_Articulo(int idarticulo, String nombre, int codbarra) {
        this.idarticulo = idarticulo;
        this.nombre = nombre;
        this.codbarra = codbarra;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodbarra() {
        return codbarra;
    }

    public void setCod_barra(int codbarra) {
        this.codbarra = codbarra;
    }
}
