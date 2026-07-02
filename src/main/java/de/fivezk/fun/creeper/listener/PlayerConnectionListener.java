package de.fivezk.fun.creeper.listener;

import de.fivezk.fun.creeper.service.CreeperDialogService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerConnectionListener implements Listener {

    private final CreeperDialogService creeperDialogService;

    public PlayerConnectionListener(CreeperDialogService creeperDialogService) {
        this.creeperDialogService = creeperDialogService;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        creeperDialogService.handleQuit(event.getPlayer());
    }
}
