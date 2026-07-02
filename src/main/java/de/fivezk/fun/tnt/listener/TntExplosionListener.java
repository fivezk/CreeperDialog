package de.fivezk.fun.tnt.listener;

import de.fivezk.fun.tnt.service.TntResetService;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class TntExplosionListener implements Listener {

    private final TntResetService tntResetService;

    public TntExplosionListener(TntResetService tntResetService) {
        this.tntResetService = tntResetService;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed)) {
            return;
        }

        if (tntResetService.handleExplosion(event.blockList())) {
            event.setCancelled(true);
            return;
        }

        if (tntResetService.enabled()) {
            event.setYield(0.0f);
        }
    }
}
