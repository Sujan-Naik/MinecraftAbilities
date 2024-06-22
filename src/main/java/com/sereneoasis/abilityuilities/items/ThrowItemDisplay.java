package com.sereneoasis.abilityuilities.items;

import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.util.AbilityStatus;
import com.sereneoasis.util.methods.Blocks;
import com.sereneoasis.util.methods.Display;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class ThrowItemDisplay extends CoreAbility {

    private final String name;

    private ArmorStand armorStand;

    private Location origin;

    private Vector dir;

    private boolean stick;

    private Set<ItemDisplay> displays = new HashSet<>();

    private double height;

    private double oldPitch;

    public ThrowItemDisplay(Player player, String name, Location loc, Vector dir, Material material, double width, double length, boolean stick, boolean diagonal, boolean sphere) {
        super(player, name);

        this.name = name;
        this.dir = dir.clone();
        this.stick = stick;

        abilityStatus = AbilityStatus.SHOT;

        this.origin = loc.clone().subtract(0, 1, 0);
        armorStand = Display.createArmorStand(origin);

        double height = width;


        if (sphere) {
            double scale = 1;

            double pixelWidth = width * 1 / 16;
            double distance = pixelWidth / 2;
            // 0, 1, 0, 1, 2, 2
            int[] numbers = {0, 1, 0, 0, 1};
            for (int i : numbers) {

                scale -= (double) i * 2 * pixelWidth;
                ItemDisplay leftDisplay = Display.createItemDisplayOffset(loc, material, width, height * scale, length * scale, diagonal, distance, 0, 0);
                ItemDisplay rightDisplay = Display.createItemDisplayOffset(loc, material, width, height * scale, length * scale, diagonal, -distance, 0, 0);

                displays.add(leftDisplay);
                displays.add(rightDisplay);
                armorStand.addPassenger(leftDisplay);
                armorStand.addPassenger(rightDisplay);
                distance += pixelWidth;
            }
        } else {
            double distance = 0;
            double scale = 1;
            int radius = 8;

            ItemDisplay middleDisplay = Display.createItemDisplayOffset(loc, material, width, height, length, diagonal, 0, 0, 0);
            displays.add(middleDisplay);
            armorStand.addPassenger(middleDisplay);


            for (int i = 1; i < radius; i++) {
                scale -= (double) 1 / radius;
                distance += width * scale / (radius * 2);

                ItemDisplay leftDisplay = Display.createItemDisplayOffset(loc, material, width, height * scale, length * scale, diagonal, distance, 0, 0);
                ItemDisplay rightDisplay = Display.createItemDisplayOffset(loc, material, width, height * scale, length * scale, diagonal, -distance, 0, 0);

                displays.add(leftDisplay);
                displays.add(rightDisplay);
                armorStand.addPassenger(leftDisplay);
                armorStand.addPassenger(rightDisplay);
            }
        }
        armorStand.setVelocity(dir.clone().multiply(speed));
        abilityStatus = AbilityStatus.SHOT;

        this.height = width;
        if (this.height < 2) {
            this.height = 2;
        }

        this.oldPitch = dir.toLocation(player.getWorld()).getPitch();
        start();
    }


    @Override
    public void progress() {

        if (abilityStatus != AbilityStatus.COMPLETE) {
            Location tempLoc = player.getLocation();
            double newPitch = tempLoc.setDirection(armorStand.getVelocity()).getPitch();
            double deltaPitch = newPitch - oldPitch;

            oldPitch = newPitch;

            for (ItemDisplay currentDisplay : displays) {
                Display.rotateItemDisplay(currentDisplay, 0, 0, deltaPitch);
            }

            if (armorStand.getLocation().distance(origin) > range) {
                abilityStatus = AbilityStatus.COMPLETE;
            }

            for (Block b : Blocks.getBlocksAroundPoint(armorStand.getLocation(), height / 2)) {
                if (b.getType().isSolid()) {
                    armorStand.setVelocity(new Vector(0, 0, 0));
                    armorStand.setGravity(false);
                    ((CraftArmorStand) armorStand).getHandle().noPhysics = false;
                    abilityStatus = AbilityStatus.COMPLETE;
                    if (!stick) {
                        for (ItemDisplay currentDisplay : displays) {
                            currentDisplay.remove();
                        }
                    }
                }

            }
        }
    }


    public Location getLoc() {
        return armorStand.getLocation().add(0, 1, 0);
    }

    public void setDir(Vector dir) {
        this.dir = dir;
    }


    public ArmorStand getArmorStand() {
        return armorStand;
    }


    @Override
    public void remove() {
        super.remove();
        sPlayer.addCooldown(name, cooldown);
        if (armorStand != null) {
            armorStand.remove();
        }
        displays.forEach(Entity::remove);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getName() {
        return name;
    }
}
