package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Russbell on 21/03/2017.
 */

public class SQL_Crear extends SQLiteOpenHelper {

    //creamos la BD
    public static final String db_name="DB_SRT.db";

    //metodo para constructor
    public SQL_Crear(Context context){
        super(context,db_name,null,1);
        /*no se necesita mas*/
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        //CREAR TABLA CON SENTENCIAS
        //tabla Articulo
        db.execSQL("CREATE TABLE " + SQL_Columnas.ArticuloEntry.TABLE_NAME + " ("
                + SQL_Columnas.ArticuloEntry.idarticulo + " INTEGER PRIMARY KEY NOT NULL,"
                + SQL_Columnas.ArticuloEntry.nombre + " VARCHAR(50) NOT NULL,"
                + SQL_Columnas.ArticuloEntry.cod_barra+ " NUMBER NOT NULL)");
        //tabla Transportista
        db.execSQL("CREATE TABLE " + SQL_Columnas.TransportistaEntry.TABLE_NAME+ " ("
                + SQL_Columnas.TransportistaEntry.idtransportista + " INTEGER PRIMARY KEY NOT NULL,"
                + SQL_Columnas.TransportistaEntry.nom_transp + " VARCHAR(50) NOT NULL,"
                + SQL_Columnas.TransportistaEntry.cel_transp + " INT NOT NULL,"
                + SQL_Columnas.TransportistaEntry.placa + " VARCHAR(10) NOT NULL)");
        //tabla Carga
        db.execSQL("CREATE TABLE " + SQL_Columnas.CargaEntry.TABLE_NAME + " ("
                + SQL_Columnas.CargaEntry.idtransportista + " INTEGER NOT NULL,"
                + SQL_Columnas.CargaEntry.idarticulo + " INTEGER NOT NULL,"
                + SQL_Columnas.CargaEntry.almacen + " VARCHAR(50) NOT NULL,"
                + SQL_Columnas.CargaEntry.cantidad + " INTEGER NOT NULL,"
                + SQL_Columnas.CargaEntry.fecha + " DATE NOT NULL)");

        //insertar datos ficticios
        arregloData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    private void arregloData(SQLiteDatabase sql){
        //revisar tamaño de int
        //arregloArticulo(sql, new Clase_Articulo(100,"Sapolio 500gr",1324567891457));
    }
    public long arregloArticulo(SQLiteDatabase db, Clase_Articulo ca) {
        return db.insert(
                SQL_Columnas.ArticuloEntry.TABLE_NAME,
                null,
                ca.toContentValues());
    }
}
