package com.example.rusbellgutierrez.SRT.Clases;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rusbellgutierrez.SRT.R;

/**
 * Created by Russbell on 30/03/2017.
 */

public class SetViewHolder extends RecyclerView.ViewHolder {

    public TextView codbar;
    public TextView codprod;
    public TextView nomprod;
    public TextView almprod;
    public TextView canprod;

    public FloatingActionButton fab_edit;

    public SetViewHolder(View itemView) {
        super(itemView);

        codbar=(TextView)itemView.findViewById(R.id.codbar);
        codprod=(TextView)itemView.findViewById(R.id.codprod);
        nomprod=(TextView)itemView.findViewById(R.id.nomprod);
        almprod=(TextView)itemView.findViewById(R.id.almprod);
        canprod=(TextView)itemView.findViewById(R.id.canprod);

        fab_edit=(FloatingActionButton)itemView.findViewById(R.id.fab_edit);
    }

    public void bind(Clase_FeedItem feedItem) {

        codbar.setText(feedItem.getCodbar());
        codprod.setText(feedItem.getCodprod());
        nomprod.setText(feedItem.getNomprod());
        almprod.setText(feedItem.getAlmprod());
        canprod.setText(feedItem.getCanprod());
    }
}
