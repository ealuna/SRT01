package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.rusbellgutierrez.SRT.SQL_Columnas.ArticuloEntry;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.TransportistaEntry;
import static com.example.rusbellgutierrez.SRT.SQL_Columnas.CargaEntry;


class SQL_Helper extends SQLiteOpenHelper {

    //creamos la BD
    private static final String db_name="DB_SRT";

    /*private static final String db_alter_carga = "ALTER TABLE "
            + CargaEntry.TABLE_NAME + " ADD COLUMN " + "viaje" + " integer null;";

    /*private static final String db_alter_articulo = "ALTER TABLE "
            + ArticuloEntry.TABLE_NAME + " ADD COLUMN " + COLUMN_STADIUM + " string;";*/

    //metodo para constructor
    SQL_Helper(Context context){
        super(context,db_name,null,1);
        /*no se necesita mas*/
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        /*db.execSQL("drop table "+ArticuloEntry.TABLE_NAME);
        db.execSQL("drop table "+CargaEntry.TABLE_NAME);
        db.execSQL("drop table "+TransportistaEntry.TABLE_NAME);*/
        //CREAR TABLA CON SENTENCIAS
        //tabla Articulo
        db.execSQL("CREATE TABLE " + ArticuloEntry.TABLE_NAME + " ("
                + ArticuloEntry.idarticulo + " INTEGER PRIMARY KEY NOT NULL,"
                + ArticuloEntry.nombre + " VARCHAR(50) NOT NULL,"
                + ArticuloEntry.codbarra+ " INT16 NOT NULL)");
        //tabla Transportista
        db.execSQL("CREATE TABLE " + TransportistaEntry.TABLE_NAME+ " ("
                + TransportistaEntry.idtransportista + " INTEGER PRIMARY KEY NOT NULL,"
                + TransportistaEntry.nom_transp + " VARCHAR(50) NOT NULL,"
                + TransportistaEntry.cel_transp + " INTEGER NOT NULL,"
                + TransportistaEntry.placa + " VARCHAR(10) NOT NULL)");
        //tabla Carga
        db.execSQL("CREATE TABLE " + CargaEntry.TABLE_NAME + " ("
                + CargaEntry.idtransportista + " INTEGER NOT NULL,"
                + CargaEntry.idarticulo + " INTEGER NOT NULL,"
                + CargaEntry.almacen + " VARCHAR(50) NOT NULL,"
                + CargaEntry.cantidad + " INTEGER NOT NULL,"
                + CargaEntry.fecha + " VARCHAR(20) NOT NULL,"//AÑO-MES-DIA
                + CargaEntry.viaje + " INTEGER NOT NULL,"
                + CargaEntry.estado + " INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        //ejecutar una vez mas
        db.execSQL("drop table "+ArticuloEntry.TABLE_NAME);
        db.execSQL("drop table "+CargaEntry.TABLE_NAME);
        db.execSQL("drop table "+TransportistaEntry.TABLE_NAME);

        onCreate(db);

    }
}
