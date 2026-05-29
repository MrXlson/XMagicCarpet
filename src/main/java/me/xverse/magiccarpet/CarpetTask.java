package me.xverse.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class CarpetTask {

    public static void start(Player p, BlockDisplay display) {

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Main.getInstance(),
                () -> {

                    if(!p.isOnline()) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    if(!display.isValid()) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    Location loc = display.getLocation();

                    Vector direction = p.getLocation()
                            .getDirection()
                            .normalize();

                    double speed = 0.6;

                    Vector move = direction.multiply(speed);

                    loc.add(move);

                    if(p.isSneaking()) {
                        loc.subtract(0, 0.3, 0);
                    }

                    display.teleport(loc);

                },
                0L,
                1L
        );

        CarpetManager.tasks.put(
                p.getUniqueId(),
                task
        );
    }
}
