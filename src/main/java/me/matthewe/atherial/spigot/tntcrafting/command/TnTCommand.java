package me.matthewe.atherial.spigot.tntcrafting.command;

import me.matthewe.atherial.spigot.tntcrafting.TnTCrafting;
import me.matthewe.atherial.spigot.tntcrafting.utilities.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 1/26/2018.
 */
public class TnTCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TnTCrafting crafting = TnTCrafting.INSTANCE;
        if (sender instanceof Player && (sender.hasPermission(crafting.getUsePermission()))) {
            Player player = (Player) sender;
            int amount = ItemUtil.getAmountOfTnTThatPlayerCanCraft(player);
            if (ItemUtil.craftTnT(player, amount)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', crafting.getCraftTntMessage()).replaceAll("%amount%", String.valueOf(amount)));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', crafting.getNoMaterialMessage()).replaceAll("%amount%", String.valueOf(amount)));
            }
        }
        return true;
    }
}
