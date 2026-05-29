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

                    // PLAYER WALK MOVEMENT
                    Vector velocity = p.getVelocity().clone();

                    // Ignore vertical
                    velocity.setY(0);

                    // Smooth movement
                    Vector movement = velocity.multiply(1.8);

                    for(BlockDisplay display : carpetData.getDisplays()) {

                        if(display == null || !display.isValid()) continue;

                        Location loc = display.getLocation();

                        // Move carpet WITH player walking
                        loc.add(movement);

                        // Keep under player
                        loc.setY(
                                p.getLocation().getY() - 1.2
                        );

                        // Rotation only visual
                        display.setRotation(
                                p.getLocation().getYaw(),
                                0
                        );

                        display.teleport(loc);
                    }

                    // Hover effect
                    Vector hover = p.getVelocity().clone();

                    hover.setY(0);

                    p.setVelocity(hover);

                    // Ascend
                    if(p.isJumping()) {

                        p.setVelocity(
                                p.getVelocity().setY(0.4)
                        );
                    }

                    // Descend
                    if(p.isSneaking()) {

                        p.setVelocity(
                                p.getVelocity().setY(-0.3)
                        );
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
