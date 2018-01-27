package me.matthewe.atherial.spigot.tntcrafting;

import me.matthewe.atherial.spigot.tntcrafting.command.TnTCommand;
import me.matthewe.atherial.spigot.tntcrafting.command.TnTCraftReloadCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Matthew E on 1/26/2018.
 */
public class TnTCrafting extends JavaPlugin {
    private Logger log = Logger.getLogger("Minecraft");
    private String craftTntMessage;
    private String noMaterialMessage;
    private String usePermission;
    private String reloadConfigPermission;
    private String reloadConfigMessage;
    private int requiredSandPerTnT;
    private int requiredSulfurPerTnT;
    private boolean customTnTItem;
    private String customTnTItemDisplayName;
    private List<String> customTnTItemLore;
    public static TnTCrafting INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        log.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));

        this.getCommand("tnt").setExecutor(new TnTCommand());
        this.getCommand("breantntreload").setExecutor(new TnTCraftReloadCommand());

        this.saveDefaultConfig();
        this.reloadTnTCraftConfig();
    }

    public String getCustomTnTItemDisplayName() {
        return customTnTItemDisplayName;
    }

    public List<String> getCustomTnTItemLore() {
        return customTnTItemLore;
    }

    public int getRequiredSandPerTnT() {
        return requiredSandPerTnT;
    }

    public int getRequiredSulfurPerTnT() {
        return requiredSulfurPerTnT;
    }

    public void reloadTnTCraftConfig() {
        this.craftTntMessage = this.getConfig().getString("craftTntMessage");
        this.noMaterialMessage = this.getConfig().getString("noMaterialMessage");
        this.usePermission = this.getConfig().getString("usePermission");
        this.reloadConfigPermission = this.getConfig().getString("reloadConfigPermission");
        this.reloadConfigMessage = this.getConfig().getString("reloadConfigMessage");
        this.requiredSulfurPerTnT = this.getConfig().getInt("requiredSulfurPerTnT");
        this.requiredSandPerTnT = this.getConfig().getInt("requiredSandPerTnT");
        this.customTnTItem = this.getConfig().getBoolean("customTnTItem");
        this.customTnTItemDisplayName = this.getConfig().getString("tnt.displayName");
        this.customTnTItemLore = this.getConfig().getStringList("tnt.lore");
    }


    public String getUsePermission() {
        return usePermission;
    }

    public String getReloadConfigPermission() {
        return reloadConfigPermission;
    }

    public String getReloadConfigMessage() {
        return reloadConfigMessage;
    }
    public String getCraftTntMessage() {
        return craftTntMessage;
    }

    public String getNoMaterialMessage() {
        return noMaterialMessage;
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    public boolean isCustomTnTItem() {
        return customTnTItem;
    }
}
