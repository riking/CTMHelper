package me.riking.ctmhelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import me.riking.ctmhelper.util.Attainment;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class CtmHelper extends JavaPlugin {
    public Map<UUID, CtmMap> maps = new HashMap<UUID, CtmMap>();
    public HashSet<String> trackedWorlds;
    ConfigurationSection worldConfig;
    //public static CtmHelper instance;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(Attainment.class);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CtmListener(this), this);
        getDataFolder().mkdir();

        worldConfig = getConfig().getConfigurationSection("worlds");
        if (worldConfig == null) {
            worldConfig = getConfig().createSection("worlds");
        }
        trackedWorlds = new HashSet<String>(worldConfig.getKeys(false));
        trackedWorlds.add("world"); //XXX

        //instance = this;
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public void saveConfig() {
        for (World w : getServer().getWorlds()) {
            saveWorldStatus(w);
        }
        super.saveConfig();
    }

    public CtmMap getMapInfo(UUID world) {
        return maps.get(world);
    }

    public boolean isWorldTracked(String world) {
        return trackedWorlds.contains(world);
    }

    public void loadWorldStatus(World world) {
        ConfigurationSection config = worldConfig.getConfigurationSection(world.getName());
        maps.put(world.getUID(), new CtmMap(world.getName(), this, config));
    }

    public void saveWorldStatus(World world) {
        if (!isWorldTracked(world.getName())) {
            return;
        }
        ConfigurationSection sec = worldConfig.createSection(world.getName());
        getMapInfo(world.getUID()).saveConfig(sec);
    }

    public void sendMessage(String string) {
        String processed = ChatColor.translateAlternateColorCodes('&', string);
        getServer().broadcastMessage(processed);
        getServer().getConsoleSender().sendMessage(processed);
    }
}
