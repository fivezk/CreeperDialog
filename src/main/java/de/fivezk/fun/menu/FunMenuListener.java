package de.fivezk.fun.menu;

import de.fivezk.fun.creeper.service.CreeperDialogService;
import de.fivezk.fun.penis.service.PenisBuilderService;
import de.fivezk.fun.tnt.service.TntResetService;
import de.fivezk.fun.wand.WandType;
import de.fivezk.fun.wand.service.WandService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class FunMenuListener implements Listener {

    private final FunMenu funMenu;
    private final CreeperDialogService creeperDialogService;
    private final TntResetService tntResetService;
    private final PenisBuilderService penisBuilderService;
    private final WandService wandService;

    public FunMenuListener(FunMenu funMenu, CreeperDialogService creeperDialogService, TntResetService tntResetService, PenisBuilderService penisBuilderService, WandService wandService) {
        this.funMenu = funMenu;
        this.creeperDialogService = creeperDialogService;
        this.tntResetService = tntResetService;
        this.penisBuilderService = penisBuilderService;
        this.wandService = wandService;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof FunMenuHolder)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getRawSlot() == FunMenu.CREEPER_SLOT) {
            creeperDialogService.setEnabled(!creeperDialogService.enabled());
            funMenu.open(player);
            return;
        }

        if (event.getRawSlot() == FunMenu.TNT_SLOT) {
            tntResetService.setEnabled(!tntResetService.enabled());
            funMenu.open(player);
            return;
        }

        if (event.getRawSlot() == FunMenu.PENIS_SLOT) {
            penisBuilderService.setEnabled(!penisBuilderService.enabled(), player);
            funMenu.open(player);
            return;
        }

        if (event.getRawSlot() == FunMenu.RESET_SLOT && tntResetService.enabled()) {
            tntResetService.reset(player);
            player.closeInventory();
            return;
        }

        if (event.getRawSlot() == FunMenu.WITHER_WAND_SLOT) {
            wandService.give(player, WandType.WITHER_SKULL);
            player.closeInventory();
            return;
        }

        if (event.getRawSlot() == FunMenu.FIREBALL_WAND_SLOT) {
            wandService.give(player, WandType.FIREBALL);
            player.closeInventory();
        }
    }
}
