package dev.blockmas.blockmas.listener;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * Handles admin GUI interactions - material selection and item configuration
 */
public class AdminEditListener implements Listener {
    private final BlockmasPlugin plugin;

    public AdminEditListener(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onAdminClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        if (title == null) return;
        e.setCancelled(true);

        // === Admin main GUI ===
        if (title.equals("§c⚙ Blockmas Admin")) {
            int slot = e.getRawSlot();
            if (slot < 0 || slot >= 27) return;
            
            int day = slot + 1;
            if (day < 1 || day > 24) return;

            plugin.getAdminGUIManager().openMaterialSelector(p, day);
            return;
        }

        // === Material selector ===
        if (title.startsWith("§eSelect Material - Day ")) {
            int slot = e.getRawSlot();
            if (slot < 0 || slot >= 54) return;
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

            Material selectedMat = e.getCurrentItem().getType();
            String day_str = title.replace("§eSelect Material - Day ", "");
            int day;
            try {
                day = Integer.parseInt(day_str);
            } catch (NumberFormatException ex) {
                return;
            }

            UUID uuid = p.getUniqueId();
            plugin.getPendingEdits().put(uuid, new AdminEdit(day, selectedMat.name()));
            p.closeInventory();
            p.sendMessage("§e[Blockmas] Enter amount (1-64):");
            return;
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        
        if (!plugin.getPendingEdits().containsKey(uuid)) return;
        e.setCancelled(true);

        AdminEdit edit = plugin.getPendingEdits().remove(uuid);
        String input = e.getMessage().trim();

        int amount;
        try {
            amount = Integer.parseInt(input);
            if (amount < 1 || amount > 64) {
                p.sendMessage("§cAmount must be 1-64");
                return;
            }
        } catch (NumberFormatException ex) {
            p.sendMessage("§cInvalid number");
            return;
        }

        String path = "items." + edit.day;
        plugin.getConfig().set(path + ".material", edit.material);
        plugin.getConfig().set(path + ".amount", amount);
        plugin.saveConfig();

        p.sendMessage("§a✓ Day " + edit.day + ": " + edit.material + " x" + amount);
    }

    /**
     * Data holder for pending admin edits
     */
    public static class AdminEdit {
        public int day;
        public String material;

        public AdminEdit(int day, String material) {
            this.day = day;
            this.material = material;
        }
    }
}
