package dev.blockmas.blockmas;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages persistent player data:
 * - Claimed doors (which days they've opened)
 * - Playtime tracking (in seconds)
 */
public class PlayerDataManager {
    private final BlockmasPlugin plugin;
    private final File dataFolder;

    public PlayerDataManager(BlockmasPlugin plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    /**
     * Get set of claimed days for a player
     */
    public Set<Integer> getClaimed(Player p) {
        File f = getFile(p.getUniqueId().toString());
        if (!f.exists()) return new HashSet<>();
        
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        return new HashSet<>(cfg.getIntegerList("claimed"));
    }

    /**
     * Set claimed days for a player (preserves playtime)
     */
    public void setClaimed(Player p, Set<Integer> claimed) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
        // Preserve existing playtime
        long existingPlaytime = cfg.getLong("playtime", 0L);
        
        // Update claimed
        cfg.set("claimed", claimed.stream().toList());
        
        // Restore playtime
        cfg.set("playtime", existingPlaytime);
        
        try {
            cfg.save(f);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save claimed doors for " + p.getName());
            e.printStackTrace();
        }
    }

    /**
     * Get playtime in seconds for a player
     */
    public long getPlaytimeSeconds(Player p) {
        File f = getFile(p.getUniqueId().toString());
        if (!f.exists()) return 0L;
        
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        return cfg.getLong("playtime", 0L);
    }

    /**
     * Set playtime in seconds for a player (preserves claimed doors)
     */
    public void setPlaytimeSeconds(Player p, long seconds) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
        // Preserve existing claimed doors
        Set<Integer> claimed = new HashSet<>(cfg.getIntegerList("claimed"));
        
        // Update playtime
        cfg.set("playtime", seconds);
        
        // Restore claimed
        if (!claimed.isEmpty()) {
            cfg.set("claimed", claimed.stream().toList());
        }
        
        try {
            cfg.save(f);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save playtime for " + p.getName());
            e.printStackTrace();
        }
    }

    /**
     * Get player data file
     */
    private File getFile(String uuid) {
        return new File(dataFolder, uuid + ".yml");
    }

    /**
     * Save all data (called on plugin disable)
     */
    public void saveAll() {
        // All data is saved immediately on change
        // This method is here for future batch operations if needed
    }
}
