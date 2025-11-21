package dev.blockmas.blockmas;

import dev.blockmas.blockmas.command.AdminCommand;
import dev.blockmas.blockmas.command.CalendarCommand;
import dev.blockmas.blockmas.listener.AdminEditListener;
import dev.blockmas.blockmas.listener.InventoryListener;
import dev.blockmas.blockmas.listener.PlaytimeManager;
import dev.blockmas.blockmas.manager.AdminGUIManager;
import dev.blockmas.blockmas.manager.GUIManager;
import dev.blockmas.blockmas.manager.PlayerDataManager;
import dev.blockmas.blockmas.util.LocaleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Main Blockmas advent calendar plugin class
 */
public class BlockmasPlugin extends JavaPlugin {
    private static BlockmasPlugin instance;
    private PlayerDataManager dataManager;
    private GUIManager guiManager;
    private AdminGUIManager adminGUIManager;
    private LocaleManager localeManager;
    private Map<UUID, AdminEditListener.AdminEdit> pendingEdits = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        this.localeManager = new LocaleManager(this);
        this.dataManager = new PlayerDataManager(getDataFolder());
        this.guiManager = new GUIManager(this);
        this.adminGUIManager = new AdminGUIManager();

        // Register commands
        getCommand("calendar").setExecutor(new CalendarCommand(this));
        getCommand("blockmas").setExecutor(new AdminCommand(this));
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlaytimeManager(this), this);
        getServer().getPluginManager().registerEvents(new AdminEditListener(this), this);
        
        getLogger().info("✓ Blockmas loaded successfully!");
    }

    @Override
    public void onDisable() {
        if (dataManager != null) {
            dataManager.saveAll();
        }
        getLogger().info("✓ Blockmas disabled");
    }

    public static BlockmasPlugin getInstance() {
        return instance;
    }

    public PlayerDataManager getDataManager() {
        return dataManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public AdminGUIManager getAdminGUIManager() {
        return adminGUIManager;
    }

    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    public Map<UUID, AdminEditListener.AdminEdit> getPendingEdits() {
        return pendingEdits;
    }
}
