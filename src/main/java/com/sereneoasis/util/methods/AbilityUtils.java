package com.sereneoasis.util.methods;

import com.sereneoasis.AbilitiesPlayer;
import com.sereneoasis.ability.superclasses.CoreAbility;
import com.sereneoasis.util.AbilityStatus;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class AbilityUtils {

    public static void sendActionBar(Player player, String message, ChatColor chatColor) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message, chatColor));
    }

    public static void showCharged(CoreAbility coreAbility) {
        AbilityStatus abilityStatus = coreAbility.getAbilityStatus();
        Player player = coreAbility.getPlayer();
        String name = coreAbility.getName();
        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        String heldAbilityName = sPlayer.getHeldAbility();
        if (abilityStatus == AbilityStatus.CHARGED && name.equals(heldAbilityName)) {
            sendActionBar(player, "READY", ChatColor.GREEN);
        }
    }

    public static void showShots(CoreAbility coreAbility, int current, int total) {
        AbilityStatus abilityStatus = coreAbility.getAbilityStatus();
        Player player = coreAbility.getPlayer();
        String name = coreAbility.getName();
        AbilitiesPlayer sPlayer = AbilitiesPlayer.getAbilitiesPlayer(player);
        String heldAbilityName = sPlayer.getHeldAbility();
        if (abilityStatus == AbilityStatus.SHOOTING && name.equals(heldAbilityName)) {
            sendActionBar(player, current + "/" + total, ChatColor.WHITE);
        }
    }
}
