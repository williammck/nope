package net.williammck.nope;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Util {
    Configuration config;

    Util(Configuration config) {
        this.config = config;
    }

    public boolean isPlayerNopeEligible(Player player, Location location) {
        if (player == null || location == null) return false;
        if (player.getWorld().getEnvironment() != World.Environment.THE_END) return false;
        if (!config.NOPERS.contains(player.getUniqueId().toString())) return false;

        return player.getLocation().distance(location) <= config.RADIUS;
    }
}
