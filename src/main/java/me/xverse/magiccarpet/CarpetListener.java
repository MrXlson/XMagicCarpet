package me.xverse.magiccarpet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class CarpetListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        CarpetManager.removeCarpet(e.getPlayer());
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {

        Player p = e.getPlayer();

        // Double shift remove
        if(
                p.isSneaking()
                &&
                CarpetManager.carpets.containsKey(
                        p.getUniqueId()
                )
        ) {

            CarpetManager.removeCarpet(p);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if(!(e.getEntity() instanceof Player p)) return;

        if(
                e.getCause()
                        == EntityDamageEvent.DamageCause.FALL
        ) {

            if(
                    CarpetManager.carpets.containsKey(
                            p.getUniqueId()
                    )
            ) {

                e.setCancelled(true);
            }
        }
    }
}
