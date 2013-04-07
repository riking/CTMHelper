package me.riking.ctmhelper;

import java.util.HashMap;
import java.util.Map;

import me.riking.ctmhelper.util.Attainment;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;


public class CtmMap {
    public String world;
    public String name;
    public Map<DyeColor, Attainment> wools;
    public Map<Material, Attainment> metals;
    public CtmHelper plugin;

    public CtmMap(String world, CtmHelper plugin) {
        this(world, plugin, null);
    }

    public CtmMap(String world, CtmHelper plugin, ConfigurationSection mapconfig) {
        this.world = world;
        name = world;
        this.plugin = plugin;
        if (mapconfig == null) {
            loadConfig(mapconfig);
        } else {
            fillDefaults();
        }
    }

    public void fillDefaults() {
        wools = new HashMap<DyeColor, Attainment>();
        wools.put(DyeColor.WHITE, new Attainment());
        wools.put(DyeColor.ORANGE, new Attainment());
        wools.put(DyeColor.MAGENTA, new Attainment());
        wools.put(DyeColor.LIGHT_BLUE, new Attainment());
        wools.put(DyeColor.YELLOW, new Attainment());
        wools.put(DyeColor.LIME, new Attainment());
        wools.put(DyeColor.PINK, new Attainment());
        wools.put(DyeColor.GRAY, new Attainment());
        wools.put(DyeColor.SILVER, new Attainment());
        wools.put(DyeColor.CYAN, new Attainment());
        wools.put(DyeColor.PURPLE, new Attainment());
        wools.put(DyeColor.BLUE, new Attainment());
        wools.put(DyeColor.BROWN, new Attainment());
        wools.put(DyeColor.GREEN, new Attainment());
        wools.put(DyeColor.RED, new Attainment());
        wools.put(DyeColor.BLACK, new Attainment());
        metals = new HashMap<Material, Attainment>();
        metals.put(Material.IRON_BLOCK, new Attainment());
        metals.put(Material.GOLD_BLOCK, new Attainment());
        metals.put(Material.DIAMOND_BLOCK, new Attainment());
    }
    /**
     * Load the values from the ConfigurationSection into this CtmMap.
     * @param mapconfig
     */
    @SuppressWarnings("unchecked")
    private void loadConfig(ConfigurationSection mapconfig) {
        name = (String) mapconfig.get("name", name);

        if (mapconfig.isConfigurationSection("wools")) {
            ConfigurationSection woolconfig = mapconfig.getConfigurationSection("wools");
            for (Map.Entry<String, Object> status : woolconfig.getValues(true).entrySet()) {
                try {
                    DyeColor c = DyeColor.valueOf(status.getKey());
                    wools.put(c, new Attainment((Map<String, Object>) status.getValue()));
                } catch (IllegalArgumentException e1) {
                    try {
                        Material m = Material.valueOf(status.getKey());
                        metals.put(m, new Attainment((Map<String, Object>) status.getValue()));
                    } catch (IllegalArgumentException e2) {
                        IllegalArgumentException e = new IllegalArgumentException(status.getKey() + " is an unrecognized type", e1);
                        plugin.getLogger().throwing("CtmMap", "loadConfig", e);
                        continue;
                    }
                }
            }
        } else {
            // This is a template config
        }
    }

    public void saveConfig(ConfigurationSection saveto) {
        saveto.set("name", name);
        ConfigurationSection woolconfig = saveto.createSection("wools");
        for (Map.Entry<DyeColor, Attainment> entry : wools.entrySet()) {
            woolconfig.set(entry.getKey().toString(), entry.getValue().serialize());
        }
        for (Map.Entry<Material, Attainment> entry : metals.entrySet()) {
            woolconfig.set(entry.getKey().toString(), entry.getValue().serialize());
        }
    }

    /**
     * Whether this Material is a metal block that goes on the Victory Monument.
     * @param mat {@link org.bukkit.Material} to check
     * @return true if the material is tracked on this map
     */
    public boolean isMetal(Material mat) {
        return metals.containsKey(mat);
    }
}
