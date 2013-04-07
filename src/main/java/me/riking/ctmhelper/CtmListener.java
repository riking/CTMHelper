package me.riking.ctmhelper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class CtmListener implements Listener {
    CtmHelper instance;

    public CtmListener(CtmHelper plugin) {
        instance = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void itemPickup(InventoryClickEvent event) {
        if (instance.maps.containsKey(event.getWhoClicked().getWorld().getUID())) {

        }
    }

    @EventHandler
    public void onLoad(WorldLoadEvent event) {
        if (instance.isWorldTracked(event.getWorld().getName())) {
            instance.loadWorldStatus(event.getWorld());
        }
        instance.sendMessage("&d[CtmHelper]&e Loaded config for world '" + event.getWorld().getName() + "'");
    }

    @EventHandler
    public void onUnload(WorldUnloadEvent event) {
        instance.saveWorldStatus(event.getWorld());
        instance.sendMessage("&d[CtmHelper]&e Saved config for world '" + event.getWorld().getName() + "'");
    }
}
