package com.sereneoasis.ability.superclasses;

import org.bukkit.Location;

import java.util.HashMap;

/**
 * @author Sujan
 * Controls logic for what should happen if it collides
 */
public interface CollisionAbility {

    HashMap<Location, Double> getLocs();

    void handleCollisions(HashMap<Location, Double> colliders);

    interface CollisionHandler {
        void handleCollision(Location loc);
    }

}
