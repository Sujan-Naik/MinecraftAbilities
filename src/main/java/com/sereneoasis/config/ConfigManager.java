package com.sereneoasis.config;


import com.sereneoasis.ability.ComboManager;
import com.sereneoasis.archetypes.Archetype;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

/**
 * @author Sujan
 * Used to create the configs for Archetypes and initialise defaults
 */
public class ConfigManager {

    private static final Map<Archetype, ConfigFile> configs = new HashMap<>();

    public ConfigManager() {
        for (Archetype archetype : Archetype.values()) {
            ConfigFile config = new ConfigFile(archetype.toString());
            configs.put(archetype, config);
        }

        loadConfig();
    }

    public static ConfigFile getConfig(Archetype archetype) {
        return configs.get(archetype);
    }

    private void saveConfigValuesAbility(FileConfiguration config, String name, String archetype, String description, String instructions,
                                         long chargetime, long cooldown, long duration,
                                         double damage, double hitbox, double radius, double range, double speed, double sourceRange, double size) {
        String directory = archetype + ".ability." + name;
        config.addDefault(directory + ".description", description);
        config.addDefault(directory + ".instructions", instructions);
        if (chargetime != 0) {
            config.addDefault(directory + ".chargetime", chargetime);
        }
        if (cooldown != 0) {
            config.addDefault(directory + ".cooldown", cooldown);
        }
        if (duration != 0) {
            config.addDefault(directory + ".duration", duration);
        }

        if (damage != 0) {
            config.addDefault(directory + ".damage", damage);
        }

        if (hitbox != 0) {
            config.addDefault(directory + ".hitbox", hitbox);
        }

        if (radius != 0) {
            config.addDefault(directory + ".radius", radius);
        }
        if (range != 0) {
            config.addDefault(directory + ".range", range);
        }
        if (speed != 0) {
            config.addDefault(directory + ".speed", speed);
        }
        if (sourceRange != 0) {
            config.addDefault(directory + ".sourcerange", sourceRange);
        }
        if (size != 0) {
            config.addDefault(directory + ".size", size);
        }
    }

    private void saveConfigValuesCombo(FileConfiguration config, String name, String archetype, String description, String instructions,
                                       long chargetime, long cooldown, long duration,
                                       double damage, double hitbox, double radius, double range, double speed, double sourceRange, double size,
                                       ArrayList<ComboManager.AbilityInformation> abilities) {
        String directory = archetype + ".combo." + name;
        config.addDefault(directory + ".description", description);
        config.addDefault(directory + ".instructions", instructions);
        if (chargetime != 0) {
            config.addDefault(directory + ".chargetime", chargetime);
        }
        if (cooldown != 0) {
            config.addDefault(directory + ".cooldown", cooldown);
        }
        if (duration != 0) {
            config.addDefault(directory + ".duration", duration);
        }

        if (damage != 0) {
            config.addDefault(directory + ".damage", damage);
        }

        if (hitbox != 0) {
            config.addDefault(directory + ".hitbox", hitbox);
        }

        if (radius != 0) {
            config.addDefault(directory + ".radius", radius);
        }
        if (range != 0) {
            config.addDefault(directory + ".range", range);
        }
        if (speed != 0) {
            config.addDefault(directory + ".speed", speed);
        }
        if (sourceRange != 0) {
            config.addDefault(directory + ".sourcerange", sourceRange);
        }
        if (size != 0) {
            config.addDefault(directory + ".size", size);
        }
        List<String> storableAbilities = new ArrayList<>();
        for (ComboManager.AbilityInformation abilityInformation : abilities) {
            storableAbilities.add(abilityInformation.getName() + ":" + abilityInformation.getClickType().toString());
        }
        config.addDefault(directory + ".usage", storableAbilities);
    }

    private void saveAttributeValuesArchetype(FileConfiguration config, Archetype archetype, double armor, double toughness, double damage,
                                              double attackspeed, double kbres,
                                              double health, double speed) {
        String dir = archetype.toString() + ".attribute.";
        config.addDefault(dir + Attribute.GENERIC_ARMOR, armor);
        config.addDefault(dir + Attribute.GENERIC_ARMOR_TOUGHNESS, toughness);
        config.addDefault(dir + Attribute.GENERIC_ATTACK_DAMAGE, damage);
        config.addDefault(dir + Attribute.GENERIC_ATTACK_SPEED, attackspeed);
        config.addDefault(dir + Attribute.GENERIC_KNOCKBACK_RESISTANCE, kbres);
        config.addDefault(dir + Attribute.GENERIC_MAX_HEALTH, health);
        config.addDefault(dir + Attribute.GENERIC_MOVEMENT_SPEED, speed);
    }

    private void saveArchetypeBlocks(FileConfiguration config, Archetype archetype, Set<Tag<Material>> tags, Set<Material> blocks) {
        List<String> tagList = new ArrayList<>();
        tags.forEach(tag -> tagList.add(String.valueOf(tag.getKey())));
        List<String> blockList = new ArrayList<>();
        blocks.forEach(block -> blockList.add(block.toString()));
        config.addDefault(archetype.toString() + ".blocks", blockList);
        config.addDefault(archetype.toString() + ".tags", tagList);
    }

    private void saveArchetypeCosmetics(FileConfiguration config, Archetype archetype, String color) {
        String dir = archetype.toString() + ".cosmetics.";
        config.addDefault(dir + ".color", color);
    }

    public void loadConfig() {
        //Archetype config values are added on top of base values (which are below)
        /*
        saveConfigValuesArchetype(ocean, Archetype.NONE, 0, 0, 2, 0, 4, 0.4,0.0, 20, 0.13);

         */


        FileConfiguration none = getConfig(Archetype.NONE).getConfig();

        //Ability configuration

        none.addDefault(Archetype.NONE.toString() + ".blocks", "DIRT");
        saveAttributeValuesArchetype(none, Archetype.NONE, 0, 0, 0, 0,
                0.0, 0, 0);

        saveArchetypeCosmetics(none, Archetype.NONE, "#ffffff");
        none.options().copyDefaults(true);
        getConfig(Archetype.NONE).saveConfig();


        FileConfiguration fire = getConfig(Archetype.FIRE).getConfig();

        //Ability configuration

        saveConfigValuesAbility(fire, "FireBall", Archetype.FIRE.toString(), "description", "Hold sneak to charge a fire and bring it towards you, \n left click to fire and melt blocks.",
                3000, 5000, 30000,
                2, 0.5, 3, 60, 2.0, 0, 0.5);

        saveConfigValuesAbility(fire, "FlamingRays", Archetype.FIRE.toString(), "description", "Hold sneak to charge rays of fire, \nleft click to shoot these",
                3000, 5000, 0,
                2, 2.0, 1.2, 40, 1.5, 0, 0.5);


        fire.addDefault(Archetype.FIRE.toString() + ".blocks", "FIRE");
        saveAttributeValuesArchetype(fire, Archetype.FIRE, 0, 0, 0, 0,
                0.0, 0, 0);

        saveArchetypeCosmetics(fire, Archetype.FIRE, "#f9d71c");
        fire.options().copyDefaults(true);
        getConfig(Archetype.FIRE).saveConfig();


    }


}



