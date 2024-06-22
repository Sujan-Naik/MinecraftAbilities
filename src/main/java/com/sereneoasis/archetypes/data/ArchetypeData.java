package com.sereneoasis.archetypes.data;

import com.sereneoasis.util.methods.Colors;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Sujan
 * Represents all configuration data for a specific archetype
 */
public class ArchetypeData {

    private Map<Attribute, Double> ARCHETYPE_ATTRIBUTES = new HashMap<>();

    private Set<Material> blocks = new HashSet<>();

    private String color;

    public ArchetypeData(Map<Attribute, Double> attributes, Set<Material> blocks, String color) {
        this.ARCHETYPE_ATTRIBUTES = attributes;
        this.blocks = blocks;
        this.color = color;
    }

    public Map<Attribute, Double> getArchetypeAttributes() {
        return ARCHETYPE_ATTRIBUTES;
    }

    public Set<Material> getBlocks() {
        return blocks;
    }

    public String getColor() {
        return color;
    }

    public Color getRealColor() {
        return Colors.hexToColor(this.getColor());
    }
}
