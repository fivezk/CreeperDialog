package de.fivezk.fun.wand.listener;

import de.fivezk.fun.wand.service.WandService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class WandListener implements Listener {

    private final WandService wandService;

    public WandListener(WandService wandService) {
        this.wandService = wandService;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        wandService.type(event.getItem()).ifPresent(wandType -> {
            event.setCancelled(true);
            wandService.shoot(event.getPlayer(), wandType);
        });
    }
}
