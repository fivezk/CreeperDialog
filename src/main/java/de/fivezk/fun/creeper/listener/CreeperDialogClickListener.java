package de.fivezk.fun.creeper.listener;

import de.fivezk.fun.creeper.service.CreeperDialogService;
import io.papermc.paper.connection.PlayerGameConnection;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class CreeperDialogClickListener implements Listener {

    private final CreeperDialogService creeperDialogService;

    public CreeperDialogClickListener(CreeperDialogService creeperDialogService) {
        this.creeperDialogService = creeperDialogService;
    }

    @EventHandler
    public void onPlayerCustomClick(PlayerCustomClickEvent event) {
        if (!(event.getCommonConnection() instanceof PlayerGameConnection connection)) {
            return;
        }

        Player player = connection.getPlayer();
        creeperDialogService.handleClick(player, event.getIdentifier());
    }
}
