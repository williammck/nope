package net.williammck.nope.listeners;

import net.williammck.nope.Util;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDropListener implements Listener {
    Util util;

    public MobDropListener(Util util) {
        this.util = util;
    }

    @EventHandler
    public void onMobDrop(EntityDeathEvent e) {
        if (e.getEntity() instanceof Enderman) {
            // Determine player "nope eligibility"
            LivingEntity mob = e.getEntity();
            if (!util.isPlayerNopeEligible(mob.getKiller())) return;

            e.getDrops().clear();
        }
    }
}
