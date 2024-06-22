package com.sereneoasis.util.methods;

import com.sereneoasis.Abilities;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Scheduler {

    public static void performTaskLater(long time, Task task) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(Abilities.getPlugin(), task::doTask, time);
    }

    public interface Task {
        void doTask();
    }
}
