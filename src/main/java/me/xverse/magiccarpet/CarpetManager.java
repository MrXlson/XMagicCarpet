package me.xverse.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.UUID;

public class CarpetManager {

    public static HashMap<UUID, BlockDisplay> carpets = new HashMap<>();

    public static HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    public static void spawnCarpet(Player p) {

        removeCarpet(p);

        Location loc = p.getLocation();

        BlockData data = Bukkit.createBlockData(Material.RED_CARPET);

        BlockDisplay display = loc.getWorld().spawn(
                loc,
                BlockDisplay.class
        );

        display.setBlock(data);

        display.setTransformation(
                new Transformation(
                        new Vector3f(-1.5f, -0.1f, -1.5f),
                        new AxisAngle4f(),
                        new Vector3f(3f, 0.1f, 3f),
                        new AxisAngle4f()
                )
        );

        display.addPassenger(p);

        carpets.put(p.getUniqueId(), display);

        CarpetTask.start(p, display);
    }

    public static void removeCarpet(Player p) {

        UUID uuid = p.getUniqueId();

        if(tasks.containsKey(uuid)) {

            tasks.get(uuid).cancel();

            tasks.remove(uuid);
        }

        if(carpets.containsKey(uuid)) {

            carpets.get(uuid).remove();

            carpets.remove(uuid);
        }

        // Odebrání flightu
        if(p.getGameMode() != GameMode.CREATIVE) {

            p.setFlying(false);

            p.setAllowFlight(false);
        }
    }
}
