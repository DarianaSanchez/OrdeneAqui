package com.example.asuspc.ordeneaqui.models;

import java.util.List;

/**
 * Created by Asus PC on 13/3/17.
 */

public class Orden {
    private int id_orden;
    private int id_user;
    private List<Item> items;
    private double monto;

    public Orden(){}

    public Orden(int id_orden, int id_user, List<Item> items, double monto){
        this.id_orden = id_orden;
        this.id_user = id_user;
        this.items = items;
        this.monto = monto;
    }

    public int getId_orden (){
        return id_orden;
    }

    public void setId_orden(int id_orden){
        this.id_orden = id_orden;
    }

    public int getId_user (){
        return id_user;
    }

    public void setId_user(int id_user){
        this.id_user = id_user;
    }

    public List<Item> getItems(){
        return items;
    }

    public void setItems(List<Item> items){
        this.items = items;
    }

    public double getMonto(){
        return monto;
    }

    public void setMonto(double monto){
        this.monto = monto;
    }
}
