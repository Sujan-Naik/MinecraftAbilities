package com.sereneoasis.archetypes;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum DisplayBlock {


    WATER(new ArrayList<>(List.of(new Material[]{Material.BLUE_STAINED_GLASS}))),
//    WATER(new ArrayList<>(List.of(new Material[]{Material.TUBE_CORAL_WALL_FAN, Material.TUBE_CORAL}))),

    ICE(new ArrayList<>(List.of(new Material[]{Material.ICE, Material.BLUE_ICE, Material.FROSTED_ICE, Material.PACKED_ICE}))),

    SNOW(new ArrayList<>(List.of(new Material[]{Material.SNOW}))),

    FIRE(new ArrayList<>(List.of(new Material[]{Material.RAW_GOLD_BLOCK, Material.RAW_GOLD_BLOCK,
            Material.RAW_GOLD_BLOCK, Material.NETHER_WART_BLOCK, Material.CRIMSON_HYPHAE,
            Material.NETHERRACK, Material.NETHER_GOLD_ORE, Material.MAGMA_BLOCK})));



    List<Material> blocks = new ArrayList<>();

    DisplayBlock(List<Material> blocks) {
        this.blocks = blocks;
    }

    public List<Material> getBlocks() {
        return blocks;
    }
}
