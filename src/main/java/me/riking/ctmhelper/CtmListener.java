package me.riking.ctmhelper;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Event;
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
        World world = event.getWhoClicked().getWorld();
        CtmMap data = instance.maps.get(world.getUID());
        if (data != null) {
            if (event.getResult() != Event.Result.DENY) {
                if (event.getCurrentItem().getType() == Material.WOOL) {
                    // PICKING UP
                    instance.getLogger().info("Picking up wool");
                } else if (data.isMetal(event.getCurrentItem().getType())) {
                    instance.getLogger().info("Picking up metal");

                } else if (event.getCursor().getType() == Material.WOOL) {
                    // PUTTING DOWN
                    instance.getLogger().info("putting down wool");
                } else if (data.isMetal(event.getCursor().getType())) {
                    instance.getLogger().info("putting dwon metal");

                } else {
                    instance.getLogger().info("something else" + event.getCurrentItem() + event.getCursor() + event.isLeftClick() + event.isRightClick() + event.isShiftClick());

                }
            }
        }
    }

    @EventHandler
    public void onLoad(WorldLoadEvent event) {
        instance.getLogger().info("Caught world load:" + event.getWorld().getName());
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
