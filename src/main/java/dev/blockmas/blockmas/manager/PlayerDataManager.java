package dev.blockmas.blockmas.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages persistent player data:
 * - Claimed doors (which days they've opened)
 * - Playtime per day (how long they've played each day)
 */
public class PlayerDataManager {
    private final File dataFolder;

    public PlayerDataManager(File pluginFolder) {
        this.dataFolder = new File(pluginFolder, "playerdata");
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
     * Set claimed days for a player (preserves playtime data)
     */
    public void setClaimed(Player p, Set<Integer> claimed) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
        // Preserve existing playtime data
        Map<String, Object> playtimeData = cfg.getConfigurationSection("playtime") != null ?
            new HashMap<>(cfg.getConfigurationSection("playtime").getValues(false)) : new HashMap<>();
        
        // Update claimed
        cfg.set("claimed", claimed.stream().toList());
        
        // Restore playtime
        if (!playtimeData.isEmpty()) {
            for (Map.Entry<String, Object> entry : playtimeData.entrySet()) {
                cfg.set("playtime." + entry.getKey(), entry.getValue());
            }
        }
        
        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get playtime for a specific day (in seconds)
     * Returns 0 if player hasn't played that day yet
     */
    public long getPlaytimeForDay(Player p, int day) {
        File f = getFile(p.getUniqueId().toString());
        if (!f.exists()) return 0L;
        
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        return cfg.getLong("playtime." + day, 0L);
    }

    /**
     * Add playtime to a specific day
     */
    public void addPlaytimeForDay(Player p, int day, long seconds) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
        long current = cfg.getLong("playtime." + day, 0L);
        cfg.set("playtime." + day, current + seconds);
        
        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set playtime for a specific day
     */
    public void setPlaytimeForDay(Player p, int day, long seconds) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        
        cfg.set("playtime." + day, seconds);
        
        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset all playtime for a player (for testing)
     */
    public void resetPlaytime(Player p) {
        File f = getFile(p.getUniqueId().toString());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("playtime", null);
        try {
            cfg.save(f);
        } catch (IOException e) {
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
    }
}
