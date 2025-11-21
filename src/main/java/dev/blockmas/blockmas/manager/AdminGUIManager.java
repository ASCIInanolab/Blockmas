package dev.blockmas.blockmas.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Manages admin GUI for editing calendar items
 */
public class AdminGUIManager {
    /**
     * Opens the admin main GUI showing all 24 days
     */
    public void openAdminGui(Player admin) {
        int start = 1;
        int end = 24;
        int rows = 3;
        int size = rows * 9;

        Inventory inv = Bukkit.createInventory(null, size, "§c⚙ Blockmas Admin");

        for (int slot = 0; slot < size; slot++) {
            int day = slot + 1;
            
            if (day < start || day > end) {
                inv.setItem(slot, makeGlassPane(" "));
                continue;
            }

            String itemPath = "items." + day;
            String mat = "AIR";
            int amt = 1;
            long playReq = 0;

            ItemStack item = makeItem(Material.PAPER, 
                "§6Day " + day,
                Arrays.asList(
                    "§7Material: " + mat + " x" + amt,
                    "§7Play time: " + (playReq / 60) + "min",
                    "",
                    "§e[Click to edit]"
                )
            );
            inv.setItem(slot, item);
        }

        admin.openInventory(inv);
    }

    /**
     * Opens material selector for a specific day
     */
    public void openMaterialSelector(Player admin, int day) {
        Inventory inv = Bukkit.createInventory(null, 54, "§eSelect Material - Day " + day);

        // Common materials
        Material[] materials = {
            Material.DIAMOND, Material.EMERALD, Material.GOLD_INGOT, Material.IRON_INGOT,
            Material.CHEST, Material.ENCHANTED_BOOK, Material.APPLE, Material.BREAD,
            Material.DIAMOND_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.COAL,
            Material.OAK_LOG, Material.BIRCH_LOG, Material.SPRUCE_LOG, Material.NETHERITE_INGOT,
            Material.REDSTONE, Material.LAPIS_LAZULI, Material.GLOWSTONE, Material.OBSIDIAN,
            Material.AMETHYST_SHARD, Material.COPPER_INGOT, Material.COPPER_BLOCK, Material.HONEY_BLOCK,
            Material.BEACON, Material.END_ROD, Material.DARK_OAK_LOG, Material.MANGROVE_LOG
        };

        for (int i = 0; i < Math.min(materials.length, 54); i++) {
            Material mat = materials[i];
            ItemStack item = makeItem(mat, 
                "§b" + mat.name(),
                Arrays.asList("§7Click to select")
            );
            inv.setItem(i, item);
        }

        admin.openInventory(inv);
    }

    private ItemStack makeItem(Material mat, String name, java.util.List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack makeGlassPane(String name) {
        return makeItem(Material.BLACK_STAINED_GLASS_PANE, name, null);
    }
}
