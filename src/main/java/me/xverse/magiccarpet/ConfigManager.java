package me.xverse.magiccarpet;

import org.bukkit.Material;

public class ConfigManager {

    public static double SPEED() {
        return Main.getInstance().getConfig().getDouble("carpet.speed");
    }

    public static int SIZE() {
        return Main.getInstance().getConfig().getInt("carpet.size");
    }

    public static Material MATERIAL() {

        return Material.valueOf(
                Main.getInstance().getConfig()
                        .getString("carpet.material")
        );
    }

    public static boolean PARTICLES() {
        return Main.getInstance().getConfig().getBoolean("carpet.particles");
    }
}
