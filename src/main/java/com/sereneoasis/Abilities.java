package com.sereneoasis;

import com.nivixx.ndatabase.api.NDatabase;
import com.nivixx.ndatabase.api.repository.Repository;
import com.sereneoasis.ability.BendingManager;
import com.sereneoasis.ability.ComboManager;
import com.sereneoasis.ability.data.AbilityDataManager;
import com.sereneoasis.archetypes.data.ArchetypeDataManager;
import com.sereneoasis.command.AbilitiesCommand;
import com.sereneoasis.command.TabAutoCompletion;
import com.sereneoasis.config.ConfigManager;
import com.sereneoasis.listeners.AbilitiesListener;
import com.sereneoasis.storage.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author Sujan
 * Main class used to create singletons and instantiate the plugin functionalities
 */
public class Abilities extends JavaPlugin {

    private static Abilities plugin;
    private static Logger log;
    private static Repository<UUID, PlayerData> repository;
    private static AbilityDataManager abilityDataManager;
    private static ArchetypeDataManager archetypeDataManager;
    private static ScoreboardManager scoreBoardManager;
    private static ComboManager comboManager;
    private static ConfigManager configManager;
    private static WorldGuardManager worldGuardManager;
    private static boolean isFlagRegistered = false;

    public static Abilities getPlugin() {
        return plugin;
    }

    public static Repository<UUID, PlayerData> getRepository() {
        return repository;
    }

    public static AbilityDataManager getAbilityDataManager() {
        return abilityDataManager;
    }

    public static ArchetypeDataManager getArchetypeDataManager() {
        return archetypeDataManager;
    }

    public static ScoreboardManager getScoreBoardManager() {
        return scoreBoardManager;
    }

    public static ComboManager getComboManager() {
        return comboManager;
    }

    public static WorldGuardManager getWorldGuardManager() {
        return worldGuardManager;
    }

    public static void main(String[] args) {

    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!isFlagRegistered) {
            WorldGuardManager.registerFlag();
            isFlagRegistered = true;
        }

    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        Abilities.log = this.getLogger();

        this.getServer().getPluginManager().registerEvents(new AbilitiesListener(), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BendingManager(), 0, 1);

        configManager = new ConfigManager();
        abilityDataManager = new AbilityDataManager();
        archetypeDataManager = new ArchetypeDataManager();
        comboManager = new ComboManager();
        repository = NDatabase.api().getOrCreateRepository(PlayerData.class);
        this.getCommand("abilities").setExecutor(new AbilitiesCommand());
        this.getCommand("abilities").setTabCompleter(new TabAutoCompletion());

        worldGuardManager = new WorldGuardManager();

    }

    @Override
    public void onDisable() {
        super.onDisable();
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            AbilitiesBoard.removeScore(player);
//
//            AbilitiesPlayer abilitiesPlayer = AbilitiesPlayer.getSereneAbilitiesPlayer(player);
//
//            Abilities.getComboManager().removePlayer(player);
//
//            AbilitiesPlayer.upsertPlayer(abilitiesPlayer);
//
//            removeAttributePlayer(player, abilitiesPlayer);
//
//
//            AbilitiesPlayer.removePlayerFromMap(player);
//        });


    }


}