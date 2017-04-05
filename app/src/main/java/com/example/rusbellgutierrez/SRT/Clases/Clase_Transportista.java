package com.example.rusbellgutierrez.SRT.Clases;

/**
 * Created by Rusbell Gutierrez on 17/03/2017.
 */

public class Clase_Transportista {

    private int idtransportista;
    private String nom_transp;
    private String cel_transp;
    private String placa;

    public Clase_Transportista(int idtransportista, String nom_transp, String cel_transp, String placa) {
        this.idtransportista = idtransportista;
        this.nom_transp = nom_transp;
        this.cel_transp = cel_transp;
        this.placa = placa;
    }

    public int getIdtransportista() {
        return idtransportista;
    }

    public void setIdtransportista(int idtransportista) {
        this.idtransportista = idtransportista;
    }

    public String getNom_transp() {
        return nom_transp;
    }

    public void setNom_transp(String nom_transp) {
        this.nom_transp = nom_transp;
    }

    public String getCel_transp() {
        return cel_transp;
    }

    public void setCel_transp(String cel_transp) {
        this.cel_transp = cel_transp;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
