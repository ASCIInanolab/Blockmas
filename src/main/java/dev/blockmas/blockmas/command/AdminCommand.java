package dev.blockmas.blockmas.command;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Admin commands for Blockmas management
 * /blockmas admin reset-playtime <player>
 * /blockmas admin reset-doors <player>
 * /blockmas admin reset-all <player>
 */
public class AdminCommand implements CommandExecutor {
    private final BlockmasPlugin plugin;

    public AdminCommand(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("blockmas.admin")) {
            sender.sendMessage("§cNo permission");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /blockmas admin <reset-playtime|reset-doors|reset-all> <player>");
            return true;
        }

        String subcommand = args[0].toLowerCase();
        
        if (subcommand.equals("reset-playtime")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /blockmas admin reset-playtime <player>");
                return true;
            }
            resetPlaytime(sender, args[1]);
            return true;
        }

        if (subcommand.equals("reset-doors")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /blockmas admin reset-doors <player>");
                return true;
            }
            resetDoors(sender, args[1]);
            return true;
        }

        if (subcommand.equals("reset-all")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /blockmas admin reset-all <player>");
                return true;
            }
            resetAll(sender, args[1]);
            return true;
        }

        sender.sendMessage("§cUnknown subcommand: " + subcommand);
        return true;
    }

    /**
     * Reset playtime for a player (today only)
     */
    private void resetPlaytime(CommandSender sender, String playerName) {
        Player p = Bukkit.getPlayer(playerName);
        if (p == null) {
            sender.sendMessage("§cPlayer not found: " + playerName);
            return;
        }

        plugin.getDataManager().resetPlaytime(p);
        sender.sendMessage("§a✓ Reset playtime for " + p.getName());
        p.sendMessage("§6[Blockmas] Admin reset your playtime");
    }

    /**
     * Reset opened doors for a player
     */
    private void resetDoors(CommandSender sender, String playerName) {
        Player p = Bukkit.getPlayer(playerName);
        if (p == null) {
            sender.sendMessage("§cPlayer not found: " + playerName);
            return;
        }

        plugin.getDataManager().setClaimed(p, java.util.Collections.emptySet());
        sender.sendMessage("§a✓ Reset opened doors for " + p.getName());
        p.sendMessage("§6[Blockmas] Admin reset your opened doors");
    }

    /**
     * Reset everything for a player
     */
    private void resetAll(CommandSender sender, String playerName) {
        Player p = Bukkit.getPlayer(playerName);
        if (p == null) {
            sender.sendMessage("§cPlayer not found: " + playerName);
            return;
        }

        plugin.getDataManager().resetPlaytime(p);
        plugin.getDataManager().setClaimed(p, java.util.Collections.emptySet());
        sender.sendMessage("§a✓ Reset all data for " + p.getName());
        p.sendMessage("§6[Blockmas] Admin reset all your data");
    }
}
