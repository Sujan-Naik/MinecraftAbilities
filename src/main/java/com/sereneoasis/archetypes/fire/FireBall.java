package com.sereneoasis.archetypes.fire;

import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.abilityuilities.blocks.BlockSmash;
import com.sereneoasis.abilityuilities.particles.ChargeSphere;
import com.sereneoasis.archetypes.DisplayBlock;
import com.sereneoasis.archetypes.fire.utils.FireUtils;
import com.sereneoasis.util.AbilityStatus;
import com.sereneoasis.util.enhancedmethods.EnhancedBlocksArchetypeLess;
import com.sereneoasis.util.methods.ArchetypeVisuals;
import com.sereneoasis.util.methods.Locations;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Sujan
 */
public class FireBall extends CoreAbility {

    private static final String name = "FireBall";
    private ChargeSphere chargeSphere;
    private BlockSmash blockSmash;
    private double currentAngle = 0;
    private ArchetypeVisuals.FireVisual fireVisual = new ArchetypeVisuals.FireVisual();

    public FireBall(Player player) {
        super(player, name);

        if (shouldStart()) {
            chargeSphere = new ChargeSphere(player, name, 0, new ArchetypeVisuals.FireVisual());
            abilityStatus = AbilityStatus.CHARGING;
            start();
        }


    }

    @Override
    public void progress() {
        if (abilityStatus == AbilityStatus.CHARGING) {
            if (!player.isSneaking()) {
                this.remove();
            }
            if (chargeSphere.getAbilityStatus() == AbilityStatus.CHARGED) {
                abilityStatus = AbilityStatus.CHARGED;

            }
        } else if (abilityStatus == AbilityStatus.CHARGED) {
            blockSmash = new BlockSmash(player, name,
                    DisplayBlock.FIRE,
                    chargeSphere.getLoc());
            chargeSphere.remove();
            abilityStatus = AbilityStatus.NOT_SHOT;
        } else if (abilityStatus == AbilityStatus.NOT_SHOT) {
            fireVisual.playVisual(blockSmash.getLoc(), size, radius, 0, 10, 10);
        } else if (abilityStatus == AbilityStatus.SHOT) {

            fireVisual.playShotVisual(blockSmash.getLoc(), blockSmash.getDir(), currentAngle, size, radius, 0, 1, 1);
            currentAngle += 36;
            Location facing = Locations.getFacingLocation(blockSmash.getLoc(), blockSmash.getDir(), speed * radius);

            if (!EnhancedBlocksArchetypeLess.getFacingSphereBlocks(this, facing).isEmpty()) {
                FireUtils.blockExplode(player, name, facing, radius * 2, 0.5);
            }

            if (blockSmash.getAbilityStatus() == AbilityStatus.COMPLETE) {
                this.remove();
            }
        }


    }

    public void setHasClicked() {
        if (abilityStatus == AbilityStatus.NOT_SHOT) {
            blockSmash.setHasClicked();
            abilityStatus = AbilityStatus.SHOT;
        }
    }

    @Override
    public void remove() {
        super.remove();
        if (chargeSphere != null) {
            chargeSphere.remove();
        }
        if (blockSmash != null) {
            blockSmash.remove();
        }
        sPlayer.addCooldown(name, cooldown);
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