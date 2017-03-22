package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.*;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Articulo {

    private int idarticulo;
    private String nombre;
    private Long cod_barra;

    public Clase_Articulo(int idarticulo, String nombre, Long cod_barra) {
        this.idarticulo = idarticulo;
        this.nombre = nombre;
        this.cod_barra = cod_barra;
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

    public Long getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(Long cod_barra) {
        this.cod_barra = cod_barra;
    }

    /*PARES DE CLAVE-VALOR*/
    public ContentValues toContentValues() {
        //contenedor de valores para articulo
        ContentValues val= new ContentValues();
        //en el put, el primer valor es la etiqueta, el segundo el tipo
        val.put(SQL_Columnas.ArticuloEntry.idarticulo,idarticulo);
        val.put(SQL_Columnas.ArticuloEntry.nombre,nombre);
        val.put(SQL_Columnas.ArticuloEntry.cod_barra,cod_barra);
        return val;
    }
}
