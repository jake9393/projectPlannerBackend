package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Card {

    public Card(String title, String description, int id, String color) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.color = color;
    }

    public final int id;
    public String title;
    public String description;
    public String color;
}