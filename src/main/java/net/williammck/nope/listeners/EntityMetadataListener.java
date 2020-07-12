package net.williammck.nope.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.williammck.nope.Util;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public class EntityMetadataListener extends PacketAdapter {
    Util util;

    public EntityMetadataListener(Plugin plugin, Util util) {
        super(plugin, PacketType.Play.Server.ENTITY_METADATA);
        this.util = util;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();

        // Exit early so we're not iterating over all endermen if the player is not eligible
        if (!util.isPlayerNopeEligible(player)) return;

        // Filter through all endermen in the world and get the one matching this packet
        Collection<Enderman> endermen = player.getWorld().getEntitiesByClass(Enderman.class);
        int entityID = event.getPacket().getIntegers().read(0);
        Enderman enderman = null;
        for (Enderman candidate : endermen) {
            if (candidate.getEntityId() == entityID) {
                enderman = candidate;
                break;
            }
        }

        // Couldn't find a matching enderman
        if (enderman == null) return;

        // Set index 17 (is screaming) to false
        List<WrappedWatchableObject> watchableList = packet.getWatchableCollectionModifier().read(0);
        for (WrappedWatchableObject metadata : watchableList) {
            if (metadata.getIndex() == 17) {
                metadata.setValue(false, true);
            }
        }
    }
}
