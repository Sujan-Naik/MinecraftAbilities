package com.sereneoasis.archetypes.fire.utils;

import com.sereneoasis.abilityuilities.blocks.BlockExplodeSphere;
import com.sereneoasis.archetypes.DisplayBlock;
import com.sereneoasis.util.methods.Blocks;
import com.sereneoasis.util.methods.Scheduler;
import com.sereneoasis.util.temp.TempBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class FireUtils {

    public static void blockExplode(Player player, String name, Location loc, double radius, double increment) {
        new BlockExplodeSphere(player, name, loc, radius, increment);

        Scheduler.performTaskLater(5, () -> {
            Blocks.getBlocksAroundPoint(loc, radius + 1).forEach(block -> {
                if (!block.isPassable()) {
                    new TempBlock(block, DisplayBlock.FIRE, 60000);
//                    new TB(block, 60000, DisplayBlock.SUN);
                }
            });
        });
    }
}
