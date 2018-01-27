package me.matthewe.atherial.spigot.tntcrafting.command;

import me.matthewe.atherial.spigot.tntcrafting.TnTCrafting;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Matthew E on 1/26/2018.
 */
public class TnTCraftReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TnTCrafting crafting = TnTCrafting.INSTANCE;
        if (sender.hasPermission(crafting.getReloadConfigPermission())) {
           sender.sendMessage(ChatColor.translateAlternateColorCodes('&', crafting.getReloadConfigMessage()));
            return true;
        }
        return true;
    }
}
