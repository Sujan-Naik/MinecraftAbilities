package com.sereneoasis.displays;

import com.sereneoasis.AbilitiesPlayer;
import com.sereneoasis.util.methods.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Sujan
 * Handles scoreboards for abilities.
 * Used to store, create, and modify them.
 */
public class AbilitiesBoard {

    private static HashMap<UUID, AbilitiesBoard> players = new HashMap<>();
    private ChatColor color;
    private AbilitiesPlayer sPlayer;
    private Scoreboard scoreboard;
    private Objective sidebar;

    private AbilitiesBoard(Player player, AbilitiesPlayer sPlayer) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("Abilities", Criteria.DUMMY, "Abilities");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        sidebar.setDisplayName(Colors.hexToString("#d99856 Abilities"));

        this.sPlayer = sPlayer;
        color = ChatColor.of(sPlayer.getStringColor());
        // Create Teams

        //Abilities
        for (int i = 1; i <= 9; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + -i);
            team.addEntry(genEntry(i));
        }
        // Above text
        for (int i = 10; i <= 14; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }

        // Combos and text
        for (int i = 15; i <= 19; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + -i);
            team.addEntry(genEntry(i));
        }
        player.setScoreboard(scoreboard);
        players.put(player.getUniqueId(), this);
    }

    public static AbilitiesBoard createScore(Player player, AbilitiesPlayer sPlayer) {
        return new AbilitiesBoard(player, sPlayer);
    }

    public static AbilitiesBoard getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static AbilitiesBoard removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }

//    public void setTitle(String title) {
//        title = ChatColor.translateAlternateColorCodes('&', title);
//        sidebar.setDisplayName(title.length()>32 ? title.substring(0, 32) : title);
//    }

    public void setAllAbilitySlots(HashMap<Integer, String> abilities) {
        for (int slot : abilities.keySet()) {
            String ability = abilities.get(slot);
            setAbilitySlot(slot, ability);
        }
    }

    public void setAbilitySlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + -slot);
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(-slot);
        }
        //text = Colors.hexToString(sPlayer.getColor()  + " " + text);

        String pre = color + getFirstSplit(text);
        String suf = color + getSecondSplit(text);

        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void setAboveSlot(int slot, String text) {
        slot += 9;
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot - 9);
        }

        String pre = color + getFirstSplit(text);
        String suf = color + getSecondSplit(text);
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void setBelowSlot(int slot, String text) {

        slot += 14;
        Team team = scoreboard.getTeam("SLOT_" + -slot);
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(-9 - (slot - 14));
        }


        String pre = color + getFirstSplit(text);
        String suf = color + getSecondSplit(text);


        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void strikeThroughBelowSlot(int slot, String text) {

        slot += 14;
        Team team = scoreboard.getTeam("SLOT_" + -slot);
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(-9 - (slot - 14));
        }

        text = color + text;


        String pre = color + getFirstSplit(text);
        String suf = color + getSecondSplit(text);


        team.setPrefix(pre);
        team.setSuffix(suf);
    }


    public String getBelowComboSlot(int slot) {
        slot += 14;
        Team team = scoreboard.getTeam("SLOT_" + -slot);
        if (team.getPrefix() != null && team.getSuffix() != null) {
            return team.getPrefix() + team.getSuffix();
        }
        return "";
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if (scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }


    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length() > 16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if (s.length() > 32) {
            s = s.substring(0, 32);
        }
        return s.length() > 16 ? s.substring(16) : "";
    }


}