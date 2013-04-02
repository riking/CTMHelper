package me.riking.ctmhelper;

import org.bukkit.scheduler.BukkitRunnable;

public class PeriodicConfigSaver extends BukkitRunnable {
    private final CtmHelper plugin;
    public PeriodicConfigSaver(CtmHelper p) {
        plugin = p;
    }

    public void run() {
        plugin.saveWorldStatus(world)
    }
}
