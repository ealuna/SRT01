package com.example.rusbellgutierrez.SRT;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

                Log.i("AVISO","YA EXISTEN DATOS");
                /*
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
                    ca.put("idarticulo",car.getIdarticulo().toString());
                    ca.put("almacen",car.getAlmacen());
                    ca.put("cantidad",car.getCantidad());
                    ca.put("fecha",car.getFecha());
                    ca.put("viaje",car.getViaje());
                    ca.put("estado",car.getEstado());

                    db.insert("carga",null,ca);

                    ContentValues ar = new ContentValues();
                    ar.put("idarticulo",art.getIdarticulo().toString());
                    ar.put("nombre",art.getNombre());
                    ar.put("codbarra",art.getCodbarra().toString());

                    db.insert("articulo",null,ar);
                    Log.i("AVISO","Viajes nuevos cargados");
                }*/

            }else {
                ContentValues ca = new ContentValues();
                ca.put("idtransportista",car.getIdtransportista());
                ca.put("idarticulo",car.getIdarticulo().toString());
                ca.put("almacen",car.getAlmacen());
                ca.put("cantidad",car.getCantidad());
                ca.put("fecha",car.getFecha());
                ca.put("viaje",car.getViaje());
                ca.put("estado",car.getEstado());

                db.insert("carga",null,ca);

                ContentValues ar = new ContentValues();
                ar.put("idarticulo",art.getIdarticulo().toString());
                ar.put("nombre",art.getNombre());
                ar.put("codbarra",art.getCodbarra().toString());

                db.insert("articulo",null,ar);
            }
        }
    }

    String[] consultar_detalleBD(String scanContent,SQL_Helper helper){
        /*Clase_Articulo art;
        Clase_Carga car;*/
        String[] array=new String[5];
        SQLiteDatabase db;
        db=helper.getReadableDatabase();

        if (db!=null){
            String[] args= new String[]{scanContent};
            Cursor c=db.rawQuery("select a.codbarra, a.idarticulo, a.nombre, c.almacen, SUM(c.cantidad) from carga c, articulo a where a.idarticulo=c.idarticulo and a.codbarra=? group by a.idarticulo",args);//group by a.idarticulo
            //("select codbarra,nombre,idarticulo from articulo where codbarra=?",args)
            //("select a.codbarra, a.idarticulo, a.nombre, c.almacen, SUM(c.cantidad) from carga c, articulo a where a.idarticulo=c.idarticulo and a.codbarra=? group by a.idarticulo",args)
            Log.i("INFO CURSOR", "¿EL CURSOR ES TRUE O FALSE? "+c.moveToFirst());
                if (c.moveToFirst()) {

                        array[0] = c.getString(0);
                        array[1] = c.getString(1);
                        array[2] = c.getString(2);
                        array[3] = c.getString(3);
                        array[4] = c.getString(4);

                        Log.i("DATOS DEL ARRAY", Arrays.toString(array));
                        Log.i("DATOS DEL ARRAY SUMA", array[4]);

                    c.close();

                } else Log.i("INFO", "EL CURSOR NO USA MOVETOFIRST");
        }

        return array;


        /*SQLiteDatabase db;
        db=helper.getReadableDatabase();
        if (db!=null){
            String[] args=new String[] {scanContent};
            String[] data;

            Cursor c=db.rawQuery("select a.codbarra, a.idarticulo, a.nombre, c.almacen, SUM(c.cantidad) from carga c inner join articulo a on a.idarticulo=c.idarticulo where a.codbarra=? group by a.idarticulo",args);

            if (c!=null){
                data=new String[c.getCount()];
                Log.i("AVISO","TENEMOS CURSOR "+c);
            }else
            Log.i("AVISO","NO TENEMOS CURSOR");









                    data[0]=codbarra;

            //Log.i("DATOS"," codbarra: "+codbarra+" idarticulo: "+idarticulo+" nombre: "+nombre+" almacen: "+almacen+" cantidad: "+cantidad);
        }
        if (data==null){
            Log.i("DATA","DATA NULL "+data);
        }
        Log.i("DATA","RESULTADO DE LA DATA "+ Arrays.toString(data));
        return data;*/

    }
}
