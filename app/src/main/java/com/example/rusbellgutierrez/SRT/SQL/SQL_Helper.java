package com.example.rusbellgutierrez.SRT.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.rusbellgutierrez.SRT.SQL.SQL_Columnas.ArticuloEntry;
import static com.example.rusbellgutierrez.SRT.SQL.SQL_Columnas.TransportistaEntry;
import static com.example.rusbellgutierrez.SRT.SQL.SQL_Columnas.CargaEntry;


public class SQL_Helper extends SQLiteOpenHelper {

    //creamos la BD
    private static final String db_name="DB_SRT";

    /*private static final String db_alter_carga = "ALTER TABLE "
            + CargaEntry.TABLE_NAME + " ADD COLUMN " + "viaje" + " integer null;";

    /*private static final String db_alter_articulo = "ALTER TABLE "
            + ArticuloEntry.TABLE_NAME + " ADD COLUMN " + COLUMN_STADIUM + " string;";*/

    //metodo para constructor
    public SQL_Helper(Context context1){
        super(context1,db_name,null,1);
        //context1.deleteDatabase("DB_SRT");
        /*no se necesita mas*/
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("drop table if exists "+ArticuloEntry.TABLE_NAME);
        db.execSQL("drop table if exists "+CargaEntry.TABLE_NAME);
        db.execSQL("drop table if exists "+TransportistaEntry.TABLE_NAME);
        //CREAR TABLA CON SENTENCIAS
        //tabla Articulo
        db.execSQL("CREATE TABLE " + ArticuloEntry.TABLE_NAME + " ("
                + ArticuloEntry.idarticulo + " INTEGER PRIMARY KEY NOT NULL,"
                + ArticuloEntry.nombre + " TEXT NOT NULL,"
                + ArticuloEntry.codbarra+ " INTEGER NOT NULL)");
        //tabla Transportista
        db.execSQL("CREATE TABLE " + TransportistaEntry.TABLE_NAME+ " ("
                + TransportistaEntry.idtransportista + " INTEGER PRIMARY KEY NOT NULL,"
                + TransportistaEntry.nom_transp + " TEXT NOT NULL,"
                + TransportistaEntry.cel_transp + " INTEGER NOT NULL,"
                + TransportistaEntry.placa + " TEXT NOT NULL)");
        //tabla Carga
        db.execSQL("CREATE TABLE " + CargaEntry.TABLE_NAME + " ("
                + CargaEntry.idtransportista + " INTEGER NOT NULL,"
                + CargaEntry.idarticulo + " INTEGER NOT NULL,"
                + CargaEntry.almacen + " TEXT NOT NULL,"
                + CargaEntry.caja + " INTEGER NOT NULL,"
                + CargaEntry.unidad + " INTEGER NOT NULL,"
                + CargaEntry.fecha + " TEXT NOT NULL,"//AÑO-MES-DIA
                + CargaEntry.viaje + " INTEGER NOT NULL,"
                + CargaEntry.estado + " TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        //ejecutar una vez mas
        //db.execSQL("delete from "+ArticuloEntry.TABLE_NAME);
       // db.execSQL("delete from "+CargaEntry.TABLE_NAME);
       // db.execSQL("delete from "+TransportistaEntry.TABLE_NAME);



    }
}
