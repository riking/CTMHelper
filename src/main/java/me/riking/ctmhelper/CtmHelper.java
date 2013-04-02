package me.riking.ctmhelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.riking.ctmhelper.util.Attainment;
import me.riking.ctmhelper.util.MapWoolStatus;

import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class CtmHelper extends JavaPlugin {
    public Map<UUID, MapWoolStatus> woolstatus = new HashMap<UUID, MapWoolStatus>();
    YamlConfiguration worldConfig = new YamlConfiguration();
    //public static CtmHelper instance;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CtmListener(this), this);
        ConfigurationSerialization.registerClass(MapWoolStatus.class);
        ConfigurationSerialization.registerClass(Attainment.class);
        
        worldConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "woolstatus.yml"));
        
        
        //instance = this;
    }

    public void onDisable() {
        //instance = null;
    }

    public MapWoolStatus getWoolStatus(UUID world) {
        return woolstatus.get(world);
    }
    
    public void loadWorldStatus(World world) {
        Object config = worldConfig.get(world.getName());
        System.out.println(config.getClass());
    }

    public void saveWorldStatus(World world) {
        worldConfig.set(world.getName(), getWoolStatus(world.getUID()));
    }
}
