package com.example.asuspc.ordeneaqui.models;

import java.util.List;

/**
 * Created by Asus PC on 14/3/17.
 */

public class Student {
    private int id;
    private String name;
    private String email;

    public Student(){}

    public Student(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId (){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName (){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getEmail (){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }
}
