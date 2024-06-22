package com.sereneoasis.ability.superclasses;


import org.bukkit.entity.Player;

/**
 * @author Sujan
 * Interface to define the function of all abilities.
 */
public interface Ability {

    public void progress() throws ReflectiveOperationException;

    public void remove();

    public Player getPlayer();

    public String getName();

}
