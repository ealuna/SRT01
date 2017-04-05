package com.example.rusbellgutierrez.SRT.Clases;

import java.util.List;

/**
 * Created by Russbell on 30/03/2017.
 */

public class Clase_FeedItem {

    private String codbar;
    private String codprod;
    private String nomprod;
    private String almprod;
    private String cajprod;
    private String uniprod;

    //experimental
    private String estado;

    public String getEstado() {
        return estado;
    }

    public String getCajprod() {
        return cajprod;
    }

    public void setCajprod(String cajprod) {
        this.cajprod = cajprod;
    }

    public String getUniprod() {
        return uniprod;
    }

    public void setUniprod(String uniprod) {
        this.uniprod = uniprod;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodbar() {
        return codbar;
    }

    public void setCodbar(String codbar) {
        this.codbar = codbar;
    }

    public String getCodprod() {
        return codprod;
    }

    public void setCodprod(String codprod) {
        this.codprod = codprod;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public String getAlmprod() {
        return almprod;
    }

    public void setAlmprod(String almprod) {
        this.almprod = almprod;
    }
}
