package com.sereneoasis.listeners;

import com.sereneoasis.Abilities;
import com.sereneoasis.AbilitiesPlayer;
import com.sereneoasis.ability.BendingManager;
import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.archetypes.fire.FireBall;
import com.sereneoasis.displays.AbilitiesBoard;
import com.sereneoasis.util.methods.ChatMessage;
import com.sereneoasis.util.temp.TempBlock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.*;

import static com.sereneoasis.AbilitiesPlayer.getAbilitiesPlayer;
import static com.sereneoasis.AbilitiesPlayer.removeAttributePlayer;

/**
 * @author Sujan
 * Main listener for all abilities events
 */
public class AbilitiesListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        AbilitiesPlayer.loadPlayer(player.getUniqueId(), player);

        Bukkit.getScheduler().runTaskLater(Abilities.getPlugin(), () -> {
            AbilitiesPlayer.initialisePlayer(player);

        }, 150L);

    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        AbilitiesBoard.removeScore(player);

        AbilitiesPlayer abilitiesPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);

        Abilities.getComboManager().removePlayer(player);

        AbilitiesPlayer.upsertPlayer(abilitiesPlayer);

        removeAttributePlayer(player, abilitiesPlayer);


        AbilitiesPlayer.removePlayerFromMap(player);

    }

    @EventHandler
    public void onHitEntity(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getDamager();

        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        if (sPlayer == null || !sPlayer.isOn()) {
            return;
        }
        String ability = sPlayer.getHeldAbility();

//        switch (ability) {
//            case "Ability":
//                perform logic
//                break;
//        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        if (player == null) {
            return;
        }


        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        if (sPlayer == null || !sPlayer.isOn()) {
            return;
        }
        String ability = sPlayer.getHeldAbility();

        if (ability != null) {

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Abilities.getComboManager().addRecentlyUsed(player, ability, ClickType.RIGHT);
                switch (ability) {
                }
            }
        }


    }

    @EventHandler
    public void onSwing(PlayerAnimationEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }

        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        if (sPlayer == null || !sPlayer.isOn()) {
            return;
        }
        String ability = sPlayer.getHeldAbility();


        if (ability == null) {
            return;
        }

        Abilities.getComboManager().addRecentlyUsed(player, ability, ClickType.LEFT);

        BendingManager.getInstance().handleRedirections(player, ClickType.LEFT);


        switch (ability) {
            case "FireBall":
                if (CoreAbility.hasAbility(e.getPlayer(), FireBall.class)) {
                    CoreAbility.getAbility(e.getPlayer(), FireBall.class).setHasClicked();
                }
                break;
        }

    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }

        if (player.isSneaking()) {
            return;
        }
        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        if (sPlayer == null || !sPlayer.isOn()) {
            return;
        }
        String ability = sPlayer.getHeldAbility();

        if (ability == null) {
            return;
        }

        Abilities.getComboManager().addRecentlyUsed(player, ability, ClickType.SHIFT_LEFT);

        switch (ability) {
            case "FireBall":
                new FireBall(player);
                break;
        }
    }

    @EventHandler
    public void stopTempLiquidFlow(BlockFromToEvent event) {
        if (event.getBlock().isLiquid() && TempBlock.isTempBlock(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            AbilitiesPlayer sPlayer = getAbilitiesPlayer(player);
            // Example of how to trigger an ability that occurs when a player falls a height
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

//                if (sPlayer.getArchetype() == Archetype.EARTH && sPlayer.getHeldAbility().equals("EarthQuake")) {
//                    if (!CoreAbility.hasAbility(player, EarthQuake.class)) {
//                        EarthQuake earthQuake = new EarthQuake(player);
//                        earthQuake.setCharged();
//                    }
//                }
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        if (sPlayer == null) {
            return;
        }
        AbilitiesBoard board = AbilitiesBoard.getByPlayer(player);
        if (board == null) {
            Bukkit.broadcastMessage("this board is null");
            return;
        }
        if (sPlayer.isOn()) {
            for (int i = 1; i <= 9; i++) {
                String ability = sPlayer.getAbilities().get(i);
                board.setAbilitySlot(i, ChatColor.STRIKETHROUGH + ability);
            }
            ChatMessage.sendPlayerMessage(player, "Your powers have been turned off");
            sPlayer.setOn(false);
        } else {
            for (int i = 1; i <= 9; i++) {
                String ability = sPlayer.getAbilities().get(i);
                board.setAbilitySlot(i, ability);
            }
            ChatMessage.sendPlayerMessage(player, "Your powers have been turned on");
            sPlayer.setOn(true);
        }
        event.setCancelled(true);
    }
}




























