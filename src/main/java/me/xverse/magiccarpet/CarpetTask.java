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

                    CarpetData data = CarpetManager.carpets.get(
                            p.getUniqueId()
                    );

                    Location center = data.getCenter();

                    Vector move = p.getVelocity().clone();

                    move.setY(0);

                    move.multiply(1.2);

                    center.add(move);

                    // Jump = up
                    if(p.getVelocity().getY() > 0.15) {

                        center.add(0, 0.4, 0);
                    }

                    // Sneak = down
                    if(p.isSneaking()) {

                        center.subtract(0, 0.3, 0);
                    }

                    data.setCenter(center);

                    int size = ConfigManager.SIZE();

                    int offset = size / 2;

                    int i = 0;

                    for(int x = -offset; x <= offset; x++) {

                        for(int z = -offset; z <= offset; z++) {

                            BlockDisplay display =
                                    data.getDisplays().get(i);

                            if(display == null
                                    || !display.isValid()) {

                                i++;

                                continue;
                            }

                            Location target = center.clone().add(
                                    x,
                                    -1.2,
                                    z
                            );

                            display.teleport(target);

                            i++;
                        }
                    }

                    // Keep player on carpet
                    Location playerLoc = p.getLocation();

                    playerLoc.setY(center.getY());

                    p.teleport(playerLoc);

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
