package com.example.rusbellgutierrez.SRT.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rusbellgutierrez.SRT.Clases.Clase_FeedItem;
import com.example.rusbellgutierrez.SRT.Clases.SetViewHolder;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Producto;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.Interfaces.OnTapListener;
import com.example.rusbellgutierrez.SRT.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by Russbell on 30/03/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<SetViewHolder> {
    private Activity activity;
    List<Clase_FeedItem> feed= Collections.emptyList();
    private OnTapListener onTapListener;
    //string para obtener la cantidad de caracteres que coinciden
    public String searchText="";

    private OnFragmentListener onFragmentListener;

    public RecyclerAdapter(Activity activity,List<Clase_FeedItem> feed){
        this.activity=activity;
        this.feed=feed;
    }
    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_busqueda,parent,false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SetViewHolder holder, final int position) {

        final Clase_FeedItem item =feed.get(position);
        holder.bind(item);
        /**EXPERIMENTAL*/
        /*if (feed.get(position).getEstado()!=null){
            String barra = feed.get(position).getCodbar();
            String codigo = feed.get(position).getCodprod();
            String nombre = feed.get(position).getNomprod();
            String almacen = feed.get(position).getAlmprod();
            String cantidad = feed.get(position).getCanprod();
            String estado = feed.get(position).getEstado();

            holder.codbar.setText(barra);
            holder.codprod.setText(codigo);
            holder.nomprod.setText(nombre);
            holder.almprod.setText(almacen);
            holder.canprod.setText(cantidad);
            holder.estado.setText(estado);
        }else {

            String barra = feed.get(position).getCodbar();
            String codigo = feed.get(position).getCodprod();
            String nombre = feed.get(position).getNomprod();
            String almacen = feed.get(position).getAlmprod();
            String cantidad = feed.get(position).getCanprod();

            holder.codbar.setText(barra);
            holder.codprod.setText(codigo);
            holder.nomprod.setText(nombre);
            holder.almprod.setText(almacen);
            holder.canprod.setText(cantidad);
        }

        Clase_FeedItem txt= feed.get(position);
        String cobar=txt.getCodbar().toLowerCase(Locale.getDefault());
        if (cobar.contains(searchText)){

            int startPos=cobar.indexOf(searchText);
            int endPos=startPos+searchText.length();

            Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.codbar.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.codbar.setText(spanString);
        }
        String prod=txt.getCodprod().toLowerCase(Locale.getDefault());
        if (prod.contains(searchText)){

            int startPos=prod.indexOf(searchText);
            int endPos=startPos+searchText.length();

            Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.codprod.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.codprod.setText(spanString);
        }
        String name=txt.getNomprod().toLowerCase(Locale.getDefault());
        if (name.contains(searchText)){

            int startPos=name.indexOf(searchText);
            int endPos=startPos+searchText.length();

            Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.nomprod.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nomprod.setText(spanString);
        }
        String alm=txt.getAlmprod().toLowerCase(Locale.getDefault());
        if (alm.contains(searchText)){

            int startPos=alm.indexOf(searchText);
            int endPos=startPos+searchText.length();

            Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.almprod.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.almprod.setText(spanString);
        }
        String can=txt.getCanprod().toLowerCase(Locale.getDefault());
        if (can.contains(searchText)){

            int startPos=can.indexOf(searchText);
            int endPos=startPos+searchText.length();

            Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.canprod.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.canprod.setText(spanString);
        }*/

        /**EXPERIMENTAL*/
        /*if (txt.getEstado()!=null){
            String est=txt.getEstado().toLowerCase(Locale.getDefault());
            if (est.contains(searchText)){

                int startPos=est.indexOf(searchText);
                int endPos=startPos+searchText.length();

                Spannable spanString=Spannable.Factory.getInstance().newSpannable(holder.estado.getText());
                spanString.setSpan(new ForegroundColorSpan(Color.RED),startPos,endPos,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.estado.setText(spanString);
            }
        }*/

        //realizamos el listener para los floatingactionbuton de cada item del recyclerview
        holder.fab_edit.setOnClickListener(new View.OnClickListener() {

            /**NOTA: SI QUEREMOS QUE RECONOZCA LOS STRING DEL BUNDLE, DEBEN TENER EL MISMO IDENTIFICADOR QUE EL DESTINO*/
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("codbarra",String.valueOf(holder.codbar.getText()));
                bundle.putString("idarticulo",String.valueOf(holder.codprod.getText()));
                bundle.putString("nombre",String.valueOf(holder.nomprod.getText()));
                bundle.putString("almacen",String.valueOf(holder.almprod.getText()));
                bundle.putString("cantidad",String.valueOf(holder.canprod.getText()));
                //atrapamos todos los text y ponemos en un bundle que pasaremos por la interfas
                onFragmentListener.onFragmentListener(bundle);
                //lo recibira el fragmentbusqueda
                //debemos implementar un metodo set para la interfas
            }
        });
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }
    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener=onTapListener;
    }

    public void setOnFragmentListener(OnFragmentListener onFragmentListener){
        this.onFragmentListener=onFragmentListener;
    }

    //Nuevo, para el searchview
    public void setFilter(List<Clase_FeedItem> clase_feedItems) {
        feed = new ArrayList<>();
        feed.addAll(clase_feedItems);
        this.searchText = searchText;
        notifyDataSetChanged();
    }
}
