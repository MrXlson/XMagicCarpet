package me.xverse.magiccarpet;

import org.bukkit.Particle;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleTask {

    public static void start(BlockDisplay display) {

        if(!ConfigManager.PARTICLES()) return;

        new BukkitRunnable() {

            @Override
            public void run() {

                if(!display.isValid()) {

                    cancel();

                    return;
                }

                display.getWorld().spawnParticle(
                        Particle.CLOUD,
                        display.getLocation(),
                        2,
                        0.2,
                        0.1,
                        0.2,
                        0
                );
            }

        }.runTaskTimer(
                Main.getInstance(),
                0L,
                5L
        );
    }
}
