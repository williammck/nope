package net.williammck.nope.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.williammck.nope.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NamedSoundEffectListener extends PacketAdapter {
    Util util;

    public NamedSoundEffectListener(Plugin plugin, Util util) {
        super(plugin, PacketType.Play.Server.NAMED_SOUND_EFFECT);
        this.util = util;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        String soundId = packet.getStrings().read(0);

        if (soundId.startsWith("mob.enderman.")) {
            // Determine player "nope eligibility"
            Player player = event.getPlayer();
            Location packetLoc = getPacketLocation(packet, player.getWorld());
            if (!util.isPlayerNopeEligible(player, packetLoc)) return;

            event.setCancelled(true);
        }
    }

    private Location getPacketLocation(PacketContainer packet, World world) {
        double x = packet.getIntegers().read(0) / 8.0;
        double y = packet.getIntegers().read(1) / 8.0;
        double z = packet.getIntegers().read(2) / 8.0;

        return new Location(world, x, y, z);
    }
}
