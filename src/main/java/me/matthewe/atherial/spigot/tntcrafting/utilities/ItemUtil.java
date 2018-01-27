package me.matthewe.atherial.spigot.tntcrafting.utilities;

import me.matthewe.atherial.spigot.tntcrafting.TnTCrafting;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew E on 1/26/2018.
 */
public class ItemUtil {

    public static int getAmountOfItem(Player player, Material material) {
        int amount = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || (itemStack.getType() == Material.AIR)) {
                continue;
            }
            if (itemStack.getType() == material) {
                amount += itemStack.getAmount();
            }
        }
        return amount;
    }

    public static int getAmountOfTnTThatPlayerCanCraft(Player player) {
        int requiredSulfurPerTnT = TnTCrafting.INSTANCE.getRequiredSulfurPerTnT();
        int requiredSandPerTnT = TnTCrafting.INSTANCE.getRequiredSandPerTnT();
        int sulphurAmount = getAmountOfItem(player, Material.SULPHUR);
        int sandAmount = getAmountOfItem(player, Material.SAND);
        if (sulphurAmount < TnTCrafting.INSTANCE.getRequiredSulfurPerTnT()) {
            return -1;
        }
        if (sandAmount < TnTCrafting.INSTANCE.getRequiredSandPerTnT()) {
            return -1;
        }
        int amount = 0;
        while (sulphurAmount >= requiredSulfurPerTnT && sandAmount >= requiredSandPerTnT) {
            amount++;
            sulphurAmount -= requiredSulfurPerTnT;
            sandAmount -= requiredSandPerTnT;
        }
        if (amount == 0) {
            return -1;
        }
        return amount;
    }

    public static boolean craftTnT(Player player, int amount) {
        int requiredSulfurPerTnT = TnTCrafting.INSTANCE.getRequiredSulfurPerTnT();
        int requiredSandPerTnT = TnTCrafting.INSTANCE.getRequiredSandPerTnT();
        int amountOfTnTThatPlayerCanCraft = getAmountOfTnTThatPlayerCanCraft(player);
        if (amountOfTnTThatPlayerCanCraft <= 0) {
            return false;
        }
        if (amount > amountOfTnTThatPlayerCanCraft) {
            return false;
        }
        int sulphurAmount = getAmountOfItem(player, Material.SULPHUR);
        int sandAmount = getAmountOfItem(player, Material.SAND);
        if (sulphurAmount < requiredSulfurPerTnT) {
            return false;
        }
        if (sandAmount < requiredSandPerTnT) {
            return false;
        }
        int toRemoveSandAmount = 0;
        int toRemoveSulpharAmount = 0;

        while (sulphurAmount >= requiredSulfurPerTnT && sandAmount >= requiredSandPerTnT) {
            amount++;
            sulphurAmount -= requiredSulfurPerTnT;
            toRemoveSandAmount += requiredSandPerTnT;
            toRemoveSulpharAmount += requiredSulfurPerTnT;
            sandAmount -= requiredSandPerTnT;
        }
        if (!removeAmountItemFromPlayerInventory(player, toRemoveSandAmount, Material.SAND)) {
            return false;
        }
        if (!removeAmountItemFromPlayerInventory(player, toRemoveSulpharAmount, Material.SULPHUR)) {
            return false;
        }
        int stacks = amountOfTnTThatPlayerCanCraft / 64;
        int remaining;
        if (stacks > 0) {
            remaining = (amountOfTnTThatPlayerCanCraft) - stacks * 64;
        } else {
            remaining = amountOfTnTThatPlayerCanCraft;
        }
        for (int i = 0; i < stacks; i++) {
            player.getInventory().addItem(createTnT(64));
        }
        if (remaining > 0) {
            player.getInventory().addItem(createTnT(remaining));
        }
        return true;
    }

    private static ItemStack createTnT(int amount) {
        ItemStack tntItemStack = new ItemStack(Material.TNT, amount);
        if (TnTCrafting.INSTANCE.isCustomTnTItem()) {
            ItemMeta itemMeta = tntItemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', TnTCrafting.INSTANCE.getCustomTnTItemDisplayName()));
            List<String> loreStringList = new ArrayList<>();
            for (String lore : TnTCrafting.INSTANCE.getCustomTnTItemLore()) {
                loreStringList.add(ChatColor.translateAlternateColorCodes('&', lore));
            }
            itemMeta.setLore(loreStringList);
            tntItemStack.setItemMeta(itemMeta);
        }
        return tntItemStack;
    }

    public static boolean removeAmountItemFromPlayerInventory(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed) {
                player.getInventory().setItem(index, new ItemStack(Material.AIR));
            } else {
                stack.setAmount(stack.getAmount() - removed);
            }

            if (count <= 0)
                break;
        }

        player.updateInventory();
        return true;
    }
}

