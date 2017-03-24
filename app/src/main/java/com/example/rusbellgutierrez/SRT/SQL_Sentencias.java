package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Russbell on 22/03/2017.
 */

class SQL_Sentencias {


    void guardar_transportistaBD(Clase_Transportista t, SQL_Helper helper){

        String codigo="";

        SQLiteDatabase db;

        db=helper.getWritableDatabase();
        //comenzamos revisando si la bd es null
        if (db!=null) {
            //creamos un string array para realizar el select condicional
            String[] args=new String[] {String.valueOf(t.getIdtransportista())};
            //con esto aseguramos que solo encuentre al transportista solicitado
            Cursor c=db.rawQuery("SELECT idtransportista from transportista where idtransportista=?",args);
            if (c.moveToFirst()){
                do{
                    codigo=c.getString(0);
                    //siempre un log para revisar que llego el dato
                    Log.i("DATA","La data de codigo es: "+codigo);
                }while (c.moveToNext());
            }

            //luego de obtener el codigo, comparamos con el enviado
            if (codigo.equals(String.valueOf(t.getIdtransportista()))){
                //de ser igual, se indica que ya existe
                Log.i("AVISO","El transportista ya existe");
            }else {
                //sino se procede a guardar en bd
                ContentValues val = new ContentValues();

                // Procesamos cada elemento de la matriz.
                val.put("idtransportista", t.getIdtransportista());
                val.put("nom_transp", t.getNom_transp());
                val.put("cel_transp", t.getCel_transp());
                val.put("placa", t.getPlaca());

                db.insert("transportista", null, val);
            }
        }
    }

    void guardar_detalleBD(Clase_Articulo art,Clase_Carga car,SQL_Helper helper){

        SQLiteDatabase db;

        //en esta sentencia guardaremos el detalle de la carga, asociado con sus articulos
        db=helper.getWritableDatabase();
        //primero comprobamos si la bd es null
        if (db!=null){
            //aca creamos pequeños artefactos para jugar con las sentencias
            String[] args=new String[] {String.valueOf(car.getViaje())};

            Cursor mcursor = db.rawQuery("SELECT count(*) FROM carga", null);
            //la sentencia de arriba, verifica que la tabla tenga datos

            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);

            //se revisa si las tablas estan pobladas
            if (icount>0){

                //esta sentencia revisa que sea el primer viaje del transportista
                Cursor vcursor = db.rawQuery("SELECT MAX(viaje) FROM carga", null);

                //cursor para viaje
                vcursor.moveToFirst();
                int via = vcursor.getInt(0);

                //aca se juega con el viaje, para no volver a cargar datos iguales
                if (via==1){
                    Log.i("AVISO","Este viaje ya está cargado");
                }else {
                    ContentValues ca = new ContentValues();
                    ca.put("idtransportista",car.getIdtransportista());
                    ca.put("idarticulo",car.getIdarticulo());
                    ca.put("almacen",car.getAlmacen());
                    ca.put("cantidad",car.getCantidad());
                    ca.put("fecha",car.getFecha());
                    ca.put("viaje",car.getViaje());
                    ca.put("estado",car.getEstado());

                    db.insert("carga",null,ca);

                    ContentValues ar = new ContentValues();
                    ar.put("idarticulo",art.getIdarticulo());
                    ar.put("nombre",art.getNombre());
                    ar.put("codbarra",art.getCodbarra());

                    db.insert("articulo",null,ar);
                }

            }else {
                ContentValues ca = new ContentValues();
                ca.put("idtransportista",car.getIdtransportista());
                ca.put("idarticulo",car.getIdarticulo());
                ca.put("almacen",car.getAlmacen());
                ca.put("cantidad",car.getCantidad());
                ca.put("fecha",car.getFecha());
                ca.put("viaje",car.getViaje());
                ca.put("estado",car.getEstado());

                db.insert("carga",null,ca);

                ContentValues ar = new ContentValues();
                ar.put("idarticulo",art.getIdarticulo());
                ar.put("nombre",art.getNombre());
                ar.put("codbarra",art.getCodbarra());

                db.insert("articulo",null,ar);
            }
        }
    }
}
