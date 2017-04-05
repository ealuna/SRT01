package com.example.rusbellgutierrez.SRT.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rusbellgutierrez.SRT.Clases.Clase_Articulo;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Carga;
import com.example.rusbellgutierrez.SRT.Clases.Clase_FeedItem;
import com.example.rusbellgutierrez.SRT.Clases.Clase_Transportista;
import com.example.rusbellgutierrez.SRT.SQL.SQL_Helper;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Russbell on 22/03/2017.
 */

public class SQL_Sentencias {


    public void guardar_transportistaBD(Clase_Transportista t, SQL_Helper helper){

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

    public boolean guardar_detalleBD(final Clase_Articulo art, final Clase_Carga car, final SQL_Helper helper){

        SQLiteDatabase db;
        //en esta sentencia guardaremos el detalle de la carga, asociado con sus articulos
        db=helper.getWritableDatabase();
        //primero comprobamos si la bd es null
        if (db!=null){

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
        return true;
    }

    public String[] consultar_detalleBD(String scanContent, SQL_Helper helper){
        /*Clase_Articulo art;
        Clase_Carga car;*/
        String[] array=new String[5];
        SQLiteDatabase db;
        db=helper.getReadableDatabase();

        if (db!=null){
            String[] args= new String[]{scanContent};
            Cursor c=db.rawQuery("select a.codbarra, a.idarticulo, a.nombre, c.almacen, c.cantidad from carga c, articulo a where a.idarticulo=c.idarticulo and a.codbarra=?",args);//group by a.idarticulo
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
    }
    public int existe_Registro(Context context){
        int cursor=0;
        SQLiteDatabase db;
        SQL_Helper helper=new SQL_Helper(context);
        db=helper.getReadableDatabase();

        if (db!=null){
            Cursor mcursor = db.rawQuery("SELECT count(*) FROM carga", null);
            mcursor.moveToFirst();
            cursor = mcursor.getInt(0);
        }else {
            Log.i("AVISO","D:");
        }
        return cursor;
    }

    public boolean dataRecycler(SQL_Helper helper,Context context,ArrayList<Clase_FeedItem> feed){
        helper=new SQL_Helper(context);
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select a.codbarra, a.idarticulo, a.nombre, c.almacen, c.cantidad from carga c, articulo a where a.idarticulo=c.idarticulo and c.estado like '0'",null);

        if (cursor!=null){
            if (cursor.moveToFirst()){
                do{
                    Clase_FeedItem feedItem=new Clase_FeedItem();
                    feedItem.setCodbar(cursor.getString(0));
                    feedItem.setCodprod(cursor.getString(1));
                    feedItem.setNomprod(cursor.getString(2));
                    feedItem.setAlmprod(cursor.getString(3));
                    feedItem.setCanprod(cursor.getString(4));

                    feed.add(feedItem);
                }while (cursor.moveToNext());
            }Log.i("DATOS CANTIDAD","ES "+feed.size());
        }
        return  true;
    }

    public boolean detalleRecycler(SQL_Helper helper,Context context,ArrayList<Clase_FeedItem> feed){
        helper=new SQL_Helper(context);
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select a.codbarra, a.idarticulo, a.nombre, c.almacen, c.cantidad, c.estado from carga c, articulo a where a.idarticulo=c.idarticulo and c.estado <> '0' and c.estado <> 'Completo'",null);

        if (cursor!=null){
            if (cursor.moveToFirst()){
                do{
                    Clase_FeedItem feedItem=new Clase_FeedItem();
                    feedItem.setCodbar(cursor.getString(0));
                    feedItem.setCodprod(cursor.getString(1));
                    feedItem.setNomprod(cursor.getString(2));
                    feedItem.setAlmprod(cursor.getString(3));
                    feedItem.setCanprod(cursor.getString(4));
                    feedItem.setEstado(cursor.getString(5));

                    feed.add(feedItem);
                }while (cursor.moveToNext());
            }Log.i("DATOS CANTIDAD","ES "+feed.size());
        }
        return  true;
    }
}
