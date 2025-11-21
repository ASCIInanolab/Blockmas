package dev.blockmas.blockmas.util;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized multilingual message handler (EN/DE)
 */
public class LocaleManager {
    private final BlockmasPlugin plugin;
    private String locale;
    private final Map<String, Map<String, String>> messages = new HashMap<>();

    public LocaleManager(BlockmasPlugin plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        FileConfiguration cfg = plugin.getConfig();
        this.locale = cfg.getString("locale", "en").toLowerCase();

        // ========== ENGLISH ==========
        Map<String, String> en = new HashMap<>();
        en.put("calendar_title", "§6§lBlockmas Calendar");
        en.put("calendar_footer", "Click a door to open your gift");
        en.put("door_locked", "§cDay %d - Locked");
        en.put("door_available", "§eDay %d - Available");
        en.put("door_opened", "§aOpened");
        en.put("already_claimed", "§cYou already opened this door");
        en.put("not_today", "§cThis door is not available yet");
        en.put("need_playtime", "You need to play %s more today");
        en.put("admin_title", "§6§lBlockmas Admin");
        en.put("admin_click", "Click a day to edit");

        // ========== GERMAN ==========
        Map<String, String> de = new HashMap<>();
        de.put("calendar_title", "§6§lBlockmas Adventskalender");
        de.put("calendar_footer", "Klick auf ein Türchen für dein Geschenk");
        de.put("door_locked", "§cTür %d - Verschlossen");
        de.put("door_available", "§eTür %d - Verfügbar");
        de.put("door_opened", "§aGeöffnet");
        de.put("already_claimed", "§cDu hast dieses Türchen bereits geöffnet");
        de.put("not_today", "§cDieses Türchen ist noch nicht verfügbar");
        de.put("need_playtime", "Du musst noch %s heute spielen");
        de.put("admin_title", "§6§lBlockmas Admin");
        de.put("admin_click", "Klick auf ein Türchen zum Bearbeiten");

        messages.put("en", en);
        messages.put("de", de);
    }

    /**
     * Get localized message
     */
    public String msg(String key, Object... args) {
        Map<String, String> langMap = messages.getOrDefault(locale, messages.get("en"));
        String text = langMap.getOrDefault(key, key);
        
        if (args.length > 0) {
            try {
                text = String.format(text, args);
            } catch (Exception e) {
                // Fall back if format fails
            }
        }
        
        return text;
    }

    public String getLocale() {
        return locale;
    }
}
