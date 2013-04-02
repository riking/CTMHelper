package me.riking.ctmhelper.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class Attainment implements ConfigurationSerializable {
    /**
     * The current {@link VMLocation} of this Victory Monument item with the
     * highest "stage".
     */
    public VMLocation status;
    /**
     * {@link org.bukkit.Location} of the wool box, if applicable.
     * Will be null if no player has found the wool box on a map with no
     * manifest.
     */
    public Location woolbox;
    /**
     * Name of the player who last interacted with this VM object
     */
    public String player;
    /**
     * Name of the player who first found this VM object
     */
    public String playerFound;
    /**
     * Timestamp of first finding the VM object
     */
    public long timeFound;
    /**
     * Name of the player who placed this VM object on the Victory Monument
     */
    public String playerPlaced;
    /**
     * Timestamp of placing the VM object
     */
    public long timePlaced;
    
    public Attainment() {
        this.status = VMLocation.WOOL_BOX;
        this.woolbox = null;
        this.player = null; 
        this.playerFound = null;
        this.timeFound = 0;
        this.playerPlaced = null;
        this.timePlaced = 0;
    }

    /**
     * Serialized constructor
     */
    public Attainment(Map<String, Object> map) {
        this.status = (VMLocation) map.get("status");
        this.woolbox = (Location) map.get("woollocation");
        this.player = (String) map.get("lastplayer");
        this.playerFound = (String) map.get("findingplayer");
        this.playerPlaced = (String) map.get("finalplayer");
        // autounboxed
        this.timeFound = (Long) map.get("timefound");
        // autounboxed
        this.timePlaced = (Long) map.get("timeplaced");
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("woollocation", woolbox);
        map.put("lastplayer", player);
        map.put("findingplayer", playerFound);
        map.put("finalplayer", playerPlaced);
        map.put("timefound", timeFound);
        map.put("timeplaced", timePlaced);
        return map;
    }

    /**
     * Sets the state of this VM block.
     */
    public void setLocation(VMLocation state) {
        status = state;
    }

    public void setWoolBoxLocation(Location loc) {
        woolbox = loc;
    }
    
    public void setFound(Player p) {
        player = p.getName();
        playerFound = p.getName();
        timeFound = System.currentTimeMillis();
    }

    public void setPlaced(Player p) {
        player = p.getName();
        playerPlaced = p.getName();
        timePlaced = System.currentTimeMillis();
        status = VMLocation.MONUMENT;
    }

    public enum VMLocation {
        WOOL_BOX,
        UNCRAFTED,
        FOUND,
        GROUND,
        CHEST,
        ENDER_CHEST,
        PLAYER,
        MONUMENT,
        ;
    }
}
