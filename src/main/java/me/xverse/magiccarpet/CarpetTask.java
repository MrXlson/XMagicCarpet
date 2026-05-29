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

                    // Movement direction
                    Vector direction = p.getLocation()
                            .getDirection()
                            .normalize();

                    double speed = ConfigManager.SPEED();

                    Vector movement = new Vector(
                            direction.getX() * speed,
                            0,
                            direction.getZ() * speed
                    );

                    // Sprint boost
                    if(p.isSprinting()) {

                        movement.multiply(1.5);
                    }

                    // Carpet movement
                    for(BlockDisplay display : carpetData.getDisplays()) {

                        if(display == null || !display.isValid()) continue;

                        Location loc = display.getLocation();

                        loc.add(movement);

                        // Hover stabilization
                        double targetY = p.getLocation().getY() - 1.2;

                        double smoothY = loc.getY()
                                + ((targetY - loc.getY()) * 0.15);

                        loc.setY(smoothY);

                        // Jump = ascend
                        if(p.getVelocity().getY() > 0.1) {

                            loc.add(0, 0.35, 0);
                        }

                        // Sneak = descend
                        if(p.isSneaking()) {

                            loc.subtract(0, 0.25, 0);
                        }

                        // Smooth rotation
                        display.setRotation(
                                p.getLocation().getYaw(),
                                0
                        );

                        display.teleport(loc);
                    }

                    // Push player WITH carpet
                    Vector playerMove = movement.clone();

                    playerMove.setY(
                            p.getVelocity().getY()
                    );

                    p.setVelocity(playerMove);

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
