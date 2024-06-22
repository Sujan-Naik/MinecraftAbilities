package com.sereneoasis.ability;

import com.sereneoasis.AbilitiesPlayer;
import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.ability.superclasses.RedirectAbility;
import com.sereneoasis.util.methods.RayTracing;
import com.sereneoasis.util.temp.TempBlock;
import com.sereneoasis.util.temp.TempDisplayBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.util.BoundingBox;
import oshi.util.tuples.Pair;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Sujan
 * Runnable to handle all ability progression and cooldowns.
 * Reverts {@link TempBlock temporary blocks} and {@link TempDisplayBlock temporary display blocks}
 */
public class BendingManager implements Runnable {

    private static BendingManager instance;
    private static Stream<Pair<CoreAbility, Stream<BoundingBox>>> redirectBoundingBoxes;
    private long time;

    public BendingManager() {
        instance = this;

    }

    public static BendingManager getInstance() {
        return instance;
    }

    public void handleRedirections(Player player, ClickType clickType) {
        AbilitiesPlayer abilitiesPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        CoreAbility.getAllRedirectInstances()
                .filter(coreAbilityStreamPair -> {
                    CoreAbility abil = coreAbilityStreamPair.getA();
                    return abil.getName().equals(abilitiesPlayer.getHeldAbility()) &&
                            player.getWorld() == abil.getPlayer().getWorld()
                            && player.getLocation().distanceSquared(abil.getPlayer().getLocation()) < 1000;
                })
                .forEach(coreAbilityStreamPair -> {
                    CoreAbility abil = coreAbilityStreamPair.getA();
                    RedirectAbility redirectAbility = (RedirectAbility) abil;
                    Stream<BoundingBox> boxes = coreAbilityStreamPair.getB();
                    double redirectRange = abil.getSourceRange();
                    boxes.forEach(boundingBox -> {
                        if (player.getLocation().distanceSquared(boundingBox.getCenter().toLocation(player.getWorld())) < redirectRange * redirectRange) {

                            if (RayTracing.playerLookingAt(player, boundingBox, redirectRange)) {
                                if (!redirectAbility.hasCustomRedirect()) {
                                    redirectAbility.setDir(player.getEyeLocation().getDirection());
                                } else {
                                    redirectAbility.handleRedirects(player, clickType);
                                }
                            }

                        }
                    });
                });


    }


    @Override
    public void run() {
        try {
            CoreAbility.progressAll();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        this.handleCooldowns();

        TempBlock.checkBlocks();
//        while (!TempBlock.getRevertQueue().isEmpty()) {
//            final TempBlock tempBlock = TempBlock.getRevertQueue().peek(); //Check if the top TempBlock is ready for reverting
//            if (tempBlock.getRevertTime() < System.currentTimeMillis()) {
//                tempBlock.automaticRevert();
//            } else {
//                break;
//            }
//        }
        while (!TempDisplayBlock.getRevertQueue().isEmpty()) {
            final TempDisplayBlock tempDisplayBlock = TempDisplayBlock.getRevertQueue().peek(); //Check if the top TempBlock is ready for reverting
            if (tempDisplayBlock.getRevertTime() < System.currentTimeMillis()) {
                tempDisplayBlock.automaticRevert();
            } else {
                break;
            }
        }

//        TempDisplayBlock.getTempDisplayBlockSet().forEach(tempDisplayBlock -> {
//            if (!tempDisplayBlock.getBlockDisplay().isGlowing() && tempDisplayBlock.getLoc().getBlock().getType().isSolid()){
//                tempDisplayBlock.setInvisible();
//            } else {
//                tempDisplayBlock.setVisible();
//            }
//        });
    }

    public void handleCooldowns() {
        for (Map.Entry<UUID, AbilitiesPlayer> entry : AbilitiesPlayer.getSereneAbilitiesPlayerMap().entrySet()) {
            AbilitiesPlayer sPlayer = entry.getValue();

            sPlayer.removeOldCooldowns();
        }
    }
}
