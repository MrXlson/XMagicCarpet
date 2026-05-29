package me.xverse.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CarpetManager {

    public static HashMap<UUID, CarpetData> carpets = new HashMap<>();

    public static HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    public static void spawnCarpet(Player p) {

        removeCarpet(p);

        Location center = p.getLocation().clone();

        List<BlockDisplay> displays = new ArrayList<>();

        BlockData data = Bukkit.createBlockData(
                ConfigManager.MATERIAL()
        );

        int size = ConfigManager.SIZE();

        int offset = size / 2;

        for(int x = -offset; x <= offset; x++) {

            for(int z = -offset; z <= offset; z++) {

                Location loc = center.clone().add(
                        x,
                        -1.2,
                        z
                );

                BlockDisplay display = loc.getWorld().spawn(
                        loc,
                        BlockDisplay.class
                );

                display.setBlock(data);

                display.setTransformation(
                        new Transformation(
                                new Vector3f(-0.5f, 0f, -0.5f),
                                new AxisAngle4f(),
                                new Vector3f(1f, 0.1f, 1f),
                                new AxisAngle4f()
                        )
                );

                displays.add(display);

                ParticleTask.start(display);
            }
        }

        carpets.put(
                p.getUniqueId(),
                new CarpetData(displays, center)
        );

        CarpetTask.start(p);
    }

    public static void removeCarpet(Player p) {

        UUID uuid = p.getUniqueId();

        if(tasks.containsKey(uuid)) {

            tasks.get(uuid).cancel();

            tasks.remove(uuid);
        }

        if(carpets.containsKey(uuid)) {

            CarpetData data = carpets.get(uuid);

            for(BlockDisplay display : data.getDisplays()) {

                if(display != null && display.isValid()) {

                    display.remove();
                }
            }

            carpets.remove(uuid);
        }

        if(p.getGameMode() != GameMode.CREATIVE) {

            p.setAllowFlight(false);

            p.setFlying(false);
        }
    }
}
