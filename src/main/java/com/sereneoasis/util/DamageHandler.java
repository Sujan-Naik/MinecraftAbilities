package com.sereneoasis.util;

import com.sereneoasis.AbilitiesPlayer;
import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.archetypes.Archetype;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * @author Sujan
 * Handles where abilities damage entities
 */
public class DamageHandler {
    static ResourceKey<DamageType> EATING_SHIT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("eating_shit"));

    public static boolean damageEntity(Entity entity, Player source, CoreAbility ability, double damage) {
        if (entity != null) {
            if (entity instanceof LivingEntity livingEntity) {
                // needd to add some damage cooldown
                livingEntity.damage(damage, source);
                if (AbilitiesPlayer.getAbilitiesPlayer(source).getArchetype().equals(Archetype.FIRE)) {
                    livingEntity.setFireTicks(20);
                }
                return true;
//                net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer)source).getHandle();
//                net.minecraft.world.entity.LivingEntity nmsTarget = ((CraftLivingEntity)livingEntity).getHandle();
//                nmsTarget.setHealth((float) (nmsTarget.getHealth() - damage));


                //Event abilityDamageEntityEvent = new AbilityDamageEntityEvent(source, livingEntity, ability, damage);
                //Bukkit.getServer().getPluginManager().callEvent(abilityDamageEntityEvent);
            }
        }
        return false;
    }
}
