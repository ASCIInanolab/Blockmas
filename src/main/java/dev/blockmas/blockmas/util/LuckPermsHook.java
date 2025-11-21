package dev.blockmas.blockmas.util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

/**
 * Optional LuckPerms integration hook
 */
public class LuckPermsHook {
    private static LuckPerms api;

    public static boolean isAvailable() {
        if (api != null) return true;
        try {
            api = LuckPermsProvider.get();
            return true;
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    public static String getPrimaryGroup(Player p) {
        if (!isAvailable()) return null;
        try {
            User u = api.getUserManager().getUser(p.getUniqueId());
            if (u != null) return u.getPrimaryGroup();
        } catch (Exception ignored) {
        }
        return null;
    }
}
