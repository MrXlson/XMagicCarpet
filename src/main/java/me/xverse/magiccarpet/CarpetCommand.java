package me.xverse.magiccarpet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CarpetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        // Toggle system
        if(CarpetManager.carpets.containsKey(
                p.getUniqueId()
        )) {

            CarpetManager.removeCarpet(p);

            p.sendMessage("§cMagicCarpet disabled.");

            return true;
        }

        CarpetManager.spawnCarpet(p);

        p.sendMessage("§aMagicCarpet enabled.");

        return true;
    }
}
