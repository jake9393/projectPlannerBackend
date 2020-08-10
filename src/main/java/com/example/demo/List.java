package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class List {

    public List(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public ArrayList<Card> cards = new ArrayList<>();


    public final int id;
    public String title;
    public String description;


}
