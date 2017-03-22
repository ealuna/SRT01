package com.example.rusbellgutierrez.SRT;

import android.provider.BaseColumns;

/**
 * Created by Russbell on 21/03/2017.
 */

public class SQL_Columnas {

    public static abstract class ArticuloEntry implements BaseColumns {
        public static final String TABLE_NAME ="articulo";

        public static final String idarticulo = "idarticulo";
        public static final String nombre = "nombre";
        public static final String cod_barra = "cod_barra";
    }

    public static abstract class TransportistaEntry implements BaseColumns{
        public static final String TABLE_NAME ="transportista";

        public static final String idtransportista = "idtransportista";
        public static final String nom_transp = "nom_transp";
        public static final String cel_transp = "cel_transp";
        public static final String placa = "placa";
    }

    public static abstract class CargaEntry implements BaseColumns{
        public static final String TABLE_NAME ="carga";

        public static final String idtransportista = "idtransportista";
        public static final String idarticulo = "idarticulo";
        public static final String almacen = "almacen";
        public static final String cantidad = "cantidad";
        public static final String fecha = "fecha";
    }
}
