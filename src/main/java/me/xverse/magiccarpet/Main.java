package me.xverse.magiccarpet;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        getCommand("mc").setExecutor(new CarpetCommand());

        getServer().getPluginManager().registerEvents(
                new CarpetListener(),
                this
        );

        getLogger().info("XMagicCarpet enabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}
