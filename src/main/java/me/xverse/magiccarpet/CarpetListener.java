package me.xverse.magiccarpet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

        if(!e.isSneaking()) return;

        CarpetManager.removeCarpet(p);
    }
}
