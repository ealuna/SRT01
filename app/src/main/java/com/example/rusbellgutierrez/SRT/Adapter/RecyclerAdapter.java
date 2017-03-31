package com.example.rusbellgutierrez.SRT.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rusbellgutierrez.SRT.Clases.Clase_FeedItem;
import com.example.rusbellgutierrez.SRT.Clases.SetViewHolder;
import com.example.rusbellgutierrez.SRT.Fragments.Fragment_Producto;
import com.example.rusbellgutierrez.SRT.Interfaces.OnFragmentListener;
import com.example.rusbellgutierrez.SRT.Interfaces.OnTapListener;
import com.example.rusbellgutierrez.SRT.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Russbell on 30/03/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<SetViewHolder> {
    private Activity activity;
    List<Clase_FeedItem> feed= Collections.emptyList();
    private OnTapListener onTapListener;

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
        holder.codbar.setText(feed.get(position).getCodbar());
        holder.codprod.setText(feed.get(position).getCodprod());
        holder.nomprod.setText(feed.get(position).getNomprod());
        holder.almprod.setText(feed.get(position).getAlmprod());
        holder.canprod.setText(feed.get(position).getCanprod());

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
}
