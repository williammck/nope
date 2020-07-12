package net.williammck.nope;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.williammck.nope.commands.*;
import net.williammck.nope.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NopePlugin extends JavaPlugin {
    Configuration config = new Configuration(this);

    public void onEnable() {
        // Load configuration
        saveDefaultConfig();
        config.load();

        // Initialize utility class instance
        Util util = new Util(config);

        // Register Bukkit listeners
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new MobDropListener(util), this);

        // Register ProtocolLib listeners
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new NamedSoundEffectListener(this, util));
        protocolManager.addPacketListener(new EntityMetadataListener(this, util));

        // Register Bukkit commands
        getCommand("nope").setExecutor(new NopeCommand(config));
        getCommand("yep").setExecutor(new YepCommand(config));
    }

    public void onDisable() {
        // Unregister ProtocolLib listeners
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.removePacketListeners(this);

        // Save current configuration
        config.save();
    }
}
