package me.xverse.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class CarpetTask {

    public static void start(Player p, BlockDisplay display) {

        // Povolení létání
        p.setAllowFlight(true);

        p.setFlying(true);

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Main.getInstance(),
                () -> {

                    if (!p.isOnline()) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    if (!display.isValid()) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    // Pokud hráč sesedne
                    if (!display.getPassengers().contains(p)) {

                        CarpetManager.removeCarpet(p);

                        return;
                    }

                    Location loc = p.getLocation().clone();

                    // Carpet pod hráčem
                    loc.subtract(0, 1.2, 0);

                    Vector velocity = p.getVelocity();

                    // Movement podle chůze
                    loc.add(
                            velocity.getX() * 2,
                            0,
                            velocity.getZ() * 2
                    );

                    // Shift = dolů
                    if (p.isSneaking()) {

                        loc.subtract(0, 0.3, 0);
                    }

                    // Jump/fly = nahoru
                    if (velocity.getY() > 0.05) {

                        loc.add(0, 0.5, 0);
                    }

                    // Smooth teleport
                    display.teleport(loc);

                    // Neustálé udržení letu
                    if (!p.isFlying()) {

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
