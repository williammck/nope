package net.williammck.nope;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Configuration {
    private final Plugin plugin;

    List<String> NOPERS;

    Configuration(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.reloadConfig();

        NOPERS = plugin.getConfig().getStringList("state.nopers");
    }

    public void save() {
        plugin.getConfig().set("state.nopers", NOPERS);
        plugin.saveConfig();
    }

    public void addNoper(Player player) {
        String uuid = player.getUniqueId().toString();
        if (!NOPERS.contains(uuid)) {
            NOPERS.add(uuid);
            save();
        }
    }

    public void removeNoper(Player player) {
        String uuid = player.getUniqueId().toString();
        if (NOPERS.contains(uuid)) {
            NOPERS.remove(uuid);
            save();
        }
    }
}
