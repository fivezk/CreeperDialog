package de.fivezk.fun.creeper.listener;

import de.fivezk.fun.creeper.service.CreeperDialogService;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public final class CreeperExplosionListener implements Listener {

    private final CreeperDialogService creeperDialogService;

    public CreeperExplosionListener(CreeperDialogService creeperDialogService) {
        this.creeperDialogService = creeperDialogService;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) {
            return;
        }

        LivingEntity target = creeper.getTarget();
        if (!(target instanceof Player player)) {
            return;
        }

        if (creeperDialogService.handleExplosionPrime(creeper, player)) {
            event.setCancelled(true);
        }
    }
}
