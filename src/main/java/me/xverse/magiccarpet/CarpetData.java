package me.xverse.magiccarpet;

import org.bukkit.entity.BlockDisplay;

import java.util.List;

public class CarpetData {

    private final List<BlockDisplay> displays;

    public CarpetData(List<BlockDisplay> displays) {
        this.displays = displays;
    }

    public List<BlockDisplay> getDisplays() {
        return displays;
    }
}
