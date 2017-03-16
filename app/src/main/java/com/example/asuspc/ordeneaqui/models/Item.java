package com.example.asuspc.ordeneaqui.models;

/**
 * Created by Asus PC on 13/3/17.
 */

public class Item {
    private int id_item;
    private int id_restaurante;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int thumbnail;

    public Item(){}

    public Item(int id_item, int id_restaurante, String name, String description, double price, int quantity, int thumbnail){
        this.id_item = id_item;
        this.id_restaurante = id_restaurante;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.thumbnail = thumbnail;
    }

    public int getId_item (){
        return id_item;
    }

    public void setId_item(int id_item){
        this.id_item = id_item;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
