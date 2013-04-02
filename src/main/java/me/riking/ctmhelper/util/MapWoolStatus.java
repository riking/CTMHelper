package me.riking.ctmhelper.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.riking.ctmhelper.CtmHelper;
import me.riking.ctmhelper.util.Attainment.VMLocation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.DyeColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class MapWoolStatus implements ConfigurationSerializable {
    public Map<DyeColor, Attainment> wool;
    public Map<Material, Attainment> metalblocks;
    public transient CtmHelper plugin;

    public MapWoolStatus(CtmHelper plugin) {
        wool = new HashMap<DyeColor, Attainment>();
        metalblocks = new HashMap<Material, Attainment>();
        this.plugin = plugin;
    }

    /**
     * Serialized constructor
     */
    @SuppressWarnings("unchecked")
    public MapWoolStatus(Map<String, Object> map) {
        wool = (Map<DyeColor, Attainment>) map.get("wool");
        metalblocks = (Map<Material, Attainment>) map.get("metal");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> s = new HashMap<String, Object>();
        s.put("wool", wool);
        s.put("metal", metalblocks);
        return s;
    }

    public Attainment get(DyeColor color) {
        if (wool.containsKey(color)) {
            return wool.get(color);
        } else {
            Attainment newatt = new Attainment();
            newatt.setLocation(VMLocation.WOOL_BOX);
            wool.put(color, newatt);
            return newatt;
        }
    }

    public Attainment get(Material metal) {
        if (!((metal == Material.IRON_BLOCK) || (metal == Material.GOLD_BLOCK) || (metal == Material.DIAMOND_BLOCK) || (metal == Material.REDSTONE_BLOCK))) {
            throw new IllegalArgumentException("Materials are restricted to the 3 metal blocks");
        }
        if (metalblocks.containsKey(metal)) {
            return metalblocks.get(metal);
        } else {
            Attainment newatt = new Attainment();
            newatt.setLocation(VMLocation.UNCRAFTED);
            metalblocks.put(metal, newatt);
            return newatt;
        }
    }

    public List<Attainment> getAllStatuses() {
        ArrayList<Attainment> list = new ArrayList<Attainment>(19);
        list.addAll(wool.values());
        list.addAll(metalblocks.values());
        return Collections.unmodifiableList(list);
    }
    /*
     * Goal list
     *  - First to find wool box
     *    - Opens wool box
     *    - Breaks wool box
     *  - First to pick up wool
     *    - Inventory move
     *    - ItemEntity pickup
     *  - First to place wool on VM
     *    - BlockPlace
     *  - First to get metal block
     *    - Crafts metal block
     *    - Gets from chest
     *    - Gets from ground
     *  - Losing a wool
     *    - Player Death (warning)
     *    - Item Despawn
     *    - Item Death
     */

    public void onWoolBoxOpen(Player p, Location chestLoc, Wool color) {
        String msg = "p found the c Wool Fleecey Box";
    }
    
    public void onWoolBoxBreak(Player p, Location chestLoc, Wool color) {
        String msg = "p found the c Wool Fleecey Box";
    }
    
    public void onPlayerGrabWool(Player p, Location chestLoc, Wool color) {
        if (get(color.getColor()).playerFound == p.getName()) {
            return;
        }
        String msg = "p has grabbed the c Wool";
    }
    
    public void onPlayerPickupWool(Player p, Item item, Wool color) {
        
    }

    public void onPlayerVMPlaceWool(Player p, Wool color) {
        
    }
    
    public void onPlayerDeath(Player p, List<Wool> colors, List<Material> metals) {
        
    }

    public void onItemDeath(Item item) {
        // TODO Event does not exist
    }
    
    public void onItemDespawn(Item item) {
        
    }
    
    public void onPlayerCraftMetal(Player p, Material metal) {
        
    }
    
    public void onPlayerCraftWool(Player p, ItemStack crafted) {
        // Check for cheating?
    }

    public void onPlayerGrabMetal(Player p, ItemStack crafted) {
        
    }
    
    public void onPlayerPickupMetal(Player p, Item item) {
        
    }
}
