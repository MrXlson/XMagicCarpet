package me.xverse.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class CarpetTask {

    public static void start(Player p) {

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Main.getInstance(),
                () -> {

                    if(!p.isOnline()) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    if(!CarpetManager.carpets.containsKey(
                            p.getUniqueId()
                    )) {
                        return;
                    }

                    CarpetData carpetData = CarpetManager.carpets.get(
                            p.getUniqueId()
                    );

                    Vector velocity = p.getVelocity();

                    double speed = ConfigManager.SPEED();

                    for(BlockDisplay display : carpetData.getDisplays()) {

                        if(display == null || !display.isValid()) continue;

                        Location loc = display.getLocation();

                        // Smooth follow
                        loc.add(
                                velocity.getX() * speed,
                                0,
                                velocity.getZ() * speed
                        );

                        // Hover stabilization
                        double targetY = p.getLocation().getY() - 1.2;

                        double smoothY = loc.getY()
                                + ((targetY - loc.getY()) * 0.3);

                        loc.setY(smoothY);

                        // Ascend
                        if(velocity.getY() > 0.05) {

                            loc.add(0, 0.4, 0);
                        }

                        // Descend
                        if(p.isSneaking()) {

                            loc.subtract(0, 0.3, 0);
                        }

                        display.teleport(loc);

                        display.setRotation(
                                p.getLocation().getYaw(),
                                0
                        );
                    }

                    // Keep flying
                    if(!p.isFlying()) {

                        p.setFlying(true);
                    }

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
