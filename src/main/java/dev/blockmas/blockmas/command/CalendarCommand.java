package dev.blockmas.blockmas.command;

import dev.blockmas.blockmas.BlockmasPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles /calendar and /calendar admin commands
 */
public class CalendarCommand implements CommandExecutor {
    private final BlockmasPlugin plugin;

    public CalendarCommand(BlockmasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            // Player calendar
            plugin.getGuiManager().openCalendar(p);
            return true;
        }

        if (args[0].equalsIgnoreCase("admin")) {
            if (!p.hasPermission("blockmas.admin")) {
                p.sendMessage("§cNo permission");
                return true;
            }
            plugin.getAdminGUIManager().openAdminGui(p);
            return true;
        }

        p.sendMessage("§cUsage: /calendar [admin]");
        return true;
    }
}
