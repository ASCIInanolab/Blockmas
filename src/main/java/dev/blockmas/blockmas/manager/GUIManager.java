package dev.blockmas.blockmas.manager;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Set;

/**
 * Manages the player calendar GUI
 */
public class GUIManager {
    private final BlockmasPlugin plugin;

    public GUIManager(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Opens the advent calendar for a player
     */
    public void openCalendar(Player player) {
        String title = plugin.getLocaleManager().msg("calendar_title");
        int rows = 3;
        int size = rows * 9;
        Inventory inv = Bukkit.createInventory(null, size, title);

        String tz = plugin.getConfig().getString("time-zone", "UTC");
        int today = LocalDate.now(ZoneId.of(tz)).getDayOfMonth();
        int start = plugin.getConfig().getInt("start-day", 1);
        int end = plugin.getConfig().getInt("end-day", 24);
        Set<Integer> claimed = plugin.getDataManager().getClaimed(player);

        for (int slot = 0; slot < size; slot++) {
            int day = slot + 1;

            if (day < start || day > end) {
                inv.setItem(slot, makeGlassPane(" "));
                continue;
            }

            String itemPath = "items." + day;
            String mat = plugin.getConfig().getString(itemPath + ".material", "AIR");
            int amt = plugin.getConfig().getInt(itemPath + ".amount", 1);
            long playReq = plugin.getConfig().getLong(itemPath + ".min-play-seconds", 0L);

            if (claimed.contains(day)) {
                // Already opened - show green
                inv.setItem(slot, makeItem(Material.GREEN_STAINED_GLASS_PANE,
                    "§a✓ Day " + day,
                    Arrays.asList(
                        "§7" + mat + " x" + amt,
                        "§6Already opened"
                    )
                ));
            } else if (day <= today) {
                // Available - show orange
                String playInfo = playReq > 0 ? 
                    "§7Play time: " + (playReq / 60) + "m" : 
                    "";
                
                inv.setItem(slot, makeItem(Material.ORANGE_WOOL,
                    "§e◆ Day " + day,
                    Arrays.asList(
                        "§7" + mat + " x" + amt,
                        playInfo,
                        "§e[Click to open]"
                    )
                ));
            } else {
                // Locked - show gray
                inv.setItem(slot, makeItem(Material.GRAY_CONCRETE,
                    "§8◇ Day " + day,
                    Arrays.asList(
                        "§7" + mat + " x" + amt,
                        "§8Coming soon..."
                    )
                ));
            }
        }

        // Footer
        inv.setItem(size - 1, makeItem(Material.PAPER, "§fℹ Info",
            Arrays.asList(plugin.getLocaleManager().msg("calendar_footer"))
        ));
        player.openInventory(inv);
    }

    /**
     * Creates a styled item with display name and lore
     */
    private ItemStack makeItem(Material mat, String name, java.util.List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            if (lore != null) {
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates a black glass pane separator
     */
    private ItemStack makeGlassPane(String name) {
        return makeItem(Material.BLACK_STAINED_GLASS_PANE, name, null);
    }
}
