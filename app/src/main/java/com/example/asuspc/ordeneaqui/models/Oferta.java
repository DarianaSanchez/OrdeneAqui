package com.example.asuspc.ordeneaqui.models;

/**
 * Created by Asus PC on 13/3/17.
 */

public class Oferta {
    private String ofertaTitle;
    private String restauranteName;
    private int thumbnail;

    public Oferta() {
    }

    public Oferta(String ofertaTitle, String restauranteName, int thumbnail) {
        this.ofertaTitle = ofertaTitle;
        this.restauranteName = restauranteName;
        this.thumbnail = thumbnail;
    }

    public String getOfertaTitle() {
        return ofertaTitle;
    }

    public void setOfertaTitle(String ofertaTitle) {
        this.ofertaTitle = ofertaTitle;
    }

    public String getRestauranteName() {
        return restauranteName;
    }

    public void setRestauranteName(String restauranteName) {
        this.restauranteName = restauranteName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
