package com.example.cillin.lewis;

/**
 * Created by cillin on 27/07/2015.
 */
public class Location
{
    private int id;
    private String name;

    public Location(){}

    public Location(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
