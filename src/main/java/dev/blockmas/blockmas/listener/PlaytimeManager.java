package dev.blockmas.blockmas.listener;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Tracks playtime per day for each player
 * Resets daily at midnight (timezone-based)
 */
public class PlaytimeManager implements Listener {
    private final BlockmasPlugin plugin;
    // Maps UUID + day to join time
    private final Map<String, Long> sessionTimes = new HashMap<>();

    public PlaytimeManager(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get current day key for playtime tracking
     */
    private String getDayKey(UUID uuid) {
        String tz = plugin.getConfig().getString("time-zone", "UTC");
        String date = LocalDate.now(ZoneId.of(tz)).toString();
        return uuid + ":" + date;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String dayKey = getDayKey(p.getUniqueId());
        sessionTimes.put(dayKey, System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        String dayKey = getDayKey(uuid);

        Long joinTime = sessionTimes.remove(dayKey);
        if (joinTime == null) return;

        // Calculate session duration in seconds
        long sessionSeconds = (System.currentTimeMillis() - joinTime) / 1000L;
        if (sessionSeconds < 0) return;

        // Get current day number
        String tz = plugin.getConfig().getString("time-zone", "UTC");
        int dayOfMonth = LocalDate.now(ZoneId.of(tz)).getDayOfMonth();

        // Add playtime to TODAY's count
        long previousSeconds = plugin.getDataManager().getPlaytimeForDay(p, dayOfMonth);
        plugin.getDataManager().setPlaytimeForDay(p, dayOfMonth, previousSeconds + sessionSeconds);
    }
}
