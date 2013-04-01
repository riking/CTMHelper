package me.riking.ctmhelper;

import org.bukkit.plugin.java.JavaPlugin;

public final class CtmHelper extends JavaPlugin {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CtmListener(), this);
    }

    public void onDisable() {
        
    }
}
