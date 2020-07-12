package net.williammck.nope.commands;

import net.williammck.nope.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class YepCommand implements CommandExecutor {
    Configuration config;

    public YepCommand(Configuration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This cannot be run from the console!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            config.removeNoper(player);
            player.sendMessage(String.format("%sEndermen drops and sounds are now on.", ChatColor.GOLD));
            return true;
        }

        return false;
    }
}
