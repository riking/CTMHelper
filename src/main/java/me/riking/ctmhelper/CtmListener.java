package me.riking.ctmhelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.riking.ctmhelper.util.Attainment;
import me.riking.ctmhelper.util.MapWoolStatus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class CtmListener implements Listener {
    CtmHelper instance;
    
    public CtmListener(CtmHelper plugin) {
        this.instance = plugin;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getClickedBlock();
        BlockState state = block.getState();
        if (state instanceof Chest) {
            Chest ch = (Chest)state;
            Inventory inv = ch.getBlockInventory();
            // Check for fleecy box
            if (inv.contains(Material.WOOL)) {
                ItemStack i = inv.getItem(inv.first(Material.WOOL));
                MapWoolStatus status = instance.getWoolStatus(block.getWorld().getUID());
                MaterialData mdata = i.getData();
                if (!(mdata instanceof Wool))
                {
                    throw new AssertionError("ItemStack with Material.WOOL did not give Wool-type MaterialData");
                }
                Wool wool = (Wool)mdata;
                
            }
        }
    }
    
    @EventHandler
    public void onLoad(WorldLoadEvent event) {

    }

    @EventHandler
    public void onUnload(WorldUnloadEvent event) {
        try {
            instance.saveWorldStatus(event.getWorld());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
