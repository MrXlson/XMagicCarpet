package me.xverse.magiccarpet;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;

import java.util.List;

public class CarpetData {

    private final List<BlockDisplay> displays;

    private Location center;

    public CarpetData(
            List<BlockDisplay> displays,
            Location center
    ) {

        this.displays = displays;

        this.center = center;
    }

    public List<BlockDisplay> getDisplays() {
        return displays;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }
}
