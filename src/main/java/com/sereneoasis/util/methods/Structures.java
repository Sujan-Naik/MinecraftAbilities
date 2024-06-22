package com.sereneoasis.util.methods;

import com.sereneoasis.Abilities;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.Structure;

import java.util.Random;

public class Structures {

    // https://minecraft.fandom.com/wiki/Ancient_City
    public static void spawnStructure(Location loc, String name) {
        Abilities.getPlugin().getServer().getStructureManager().loadStructure(NamespacedKey.minecraft(name)).place(loc, false, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
    }

    public static void spawnStructureFill(Location loc, String name) {
        Structure structure = Abilities.getPlugin().getServer().getStructureManager().loadStructure(NamespacedKey.minecraft(name));
        structure.fill(loc.clone().subtract(24, 24, 24), loc.clone().add(24, 24, 24), false);
        structure.place(loc, false, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());

    }
}
