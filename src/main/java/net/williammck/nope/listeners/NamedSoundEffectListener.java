package net.williammck.nope.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.williammck.nope.Util;
import org.bukkit.Sound;
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
        Sound soundEffect = packet.getSoundEffects().read(0);

        if (soundEffect.name().matches("(ENTITY_(HOSTILE|ENDERMAN)_[A-Z_]+|BLOCK_[A-Z_]+_(STEP|FALL))")) {
            // Determine player "nope eligibility"
            Player player = event.getPlayer();
            if (!util.isPlayerNopeEligible(player)) return;

            event.setCancelled(true);
        }
    }
}
