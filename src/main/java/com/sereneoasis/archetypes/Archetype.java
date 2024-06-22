package com.sereneoasis.archetypes;

/**
 * @author Sujan
 * Enums to represent different archetypes
 */
public enum Archetype {
    NONE("none", 0),

    FIRE("fire", 1);


    private String name;
    private int value;


    Archetype(String name, int value) {
        this.name = name;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

}
