package com.example.asuspc.ordeneaqui.models;

/**
 * Created by Asus PC on 13/3/17.
 */

public class Restaurante {
    private int id_restaurante;
    private String name;
    private String eslogan;
    private String location;
    private int thumbnail;

    public Restaurante() {
    }

    public Restaurante(int id_restaurante, String name, String eslogan, String location, int thumbnail) {
        this.id_restaurante = id_restaurante;
        this.name = name;
        this.eslogan = eslogan;
        this.location = location;
        this.thumbnail = thumbnail;
    }

    public int getId_restaurante (){
        return id_restaurante;
    }

    public void setId_restaurante(int id_restaurante){
        this.id_restaurante = id_restaurante;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEslogan() {
        return eslogan;
    }

    public void setEslogan(String eslogan) {
        this.eslogan = eslogan;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
