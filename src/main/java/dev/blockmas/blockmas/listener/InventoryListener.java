package dev.blockmas.blockmas.listener;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

/**
 * Handles player calendar clicks - opening doors and granting items
 * Checks playtime requirement PER DAY (not total)
 */
public class InventoryListener implements Listener {
    private final BlockmasPlugin plugin;

    public InventoryListener(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCalendarClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        if (title == null) return;
        
        // Only handle calendar inventory
        if (!title.equals(plugin.getLocaleManager().msg("calendar_title"))) return;
        e.setCancelled(true);

        int slot = e.getRawSlot();
        int size = plugin.getConfig().getInt("end-day", 24);
        if (slot < 0 || slot >= size) return;

        int day = slot + 1;
        int start = plugin.getConfig().getInt("start-day", 1);
        int end = plugin.getConfig().getInt("end-day", 24);
        if (day < start || day > end) return;

        // === Check if already claimed ===
        Set<Integer> claimed = plugin.getDataManager().getClaimed(p);
        if (claimed.contains(day)) {
            p.sendMessage(plugin.getLocaleManager().msg("already_claimed"));
            return;
        }

        // === Check if available today ===
        String tz = plugin.getConfig().getString("time-zone", "UTC");
        int today = LocalDate.now(ZoneId.of(tz)).getDayOfMonth();
        if (day > today) {
            p.sendMessage(plugin.getLocaleManager().msg("not_today"));
            return;
        }

        // === CHECK PLAYTIME FOR THIS DAY (NEW!) ===
        String dayConfigPath = "items." + day;
        long requiredSeconds = plugin.getConfig().getLong(dayConfigPath + ".min-play-seconds", 0L);
        
        if (requiredSeconds > 0) {
            long playedToday = plugin.getDataManager().getPlaytimeForDay(p, today);
            if (playedToday < requiredSeconds) {
                long needSec = requiredSeconds - playedToday;
                long minutes = needSec / 60;
                long secs = needSec % 60;
                p.sendMessage("§c✗ You need to play " + minutes + "m " + secs + "s more!");
                return;
            }
        }

        // === Grant item ===
        String matName = plugin.getConfig().getString(dayConfigPath + ".material", "AIR");
        int amount = plugin.getConfig().getInt(dayConfigPath + ".amount", 1);

        Material mat;
        try {
            mat = Material.valueOf(matName);
        } catch (IllegalArgumentException ex) {
            mat = Material.AIR;
        }

        ItemStack item = new ItemStack(mat, amount);
        p.getInventory().addItem(item);

        // === Update data ===
        claimed.add(day);
        plugin.getDataManager().setClaimed(p, claimed);

        // === Effects ===
        p.closeInventory();
        p.getWorld().spawnParticle(org.bukkit.Particle.VILLAGER_HAPPY, 
            p.getLocation().add(0, 1, 0), 20, 0.5, 0.5, 0.5, 0.1);
        p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        p.sendTitle("§a✓ Day " + day, plugin.getLocaleManager().msg("door_opened"), 5, 50, 5);
    }
}
