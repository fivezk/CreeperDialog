package de.fivezk.fun.menu;

import de.fivezk.fun.config.FunConfig;
import de.fivezk.fun.creeper.service.CreeperDialogService;
import de.fivezk.fun.tnt.service.TntResetService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class FunMenu {

    public static final int CREEPER_SLOT = 11;
    public static final int TNT_SLOT = 15;
    public static final int RESET_SLOT = 22;
    private final FunConfig config;
    private final CreeperDialogService creeperDialogService;
    private final TntResetService tntResetService;

    public FunMenu(FunConfig config, CreeperDialogService creeperDialogService, TntResetService tntResetService) {
        this.config = config;
        this.creeperDialogService = creeperDialogService;
        this.tntResetService = tntResetService;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new FunMenuHolder(), 27, config.message("menu.title"));
        inventory.setItem(CREEPER_SLOT, createItem(
                config.material("menu.items.creeper-dialog.material", Material.CREEPER_HEAD),
                config.message("menu.items.creeper-dialog.name"),
                stateLore(creeperDialogService.enabled())
        ));
        inventory.setItem(TNT_SLOT, createItem(
                config.material("menu.items.tnt-reset.material", Material.TNT),
                config.message("menu.items.tnt-reset.name"),
                stateLore(tntResetService.enabled())
        ));

        if (tntResetService.enabled()) {
            inventory.setItem(RESET_SLOT, createItem(
                    config.material("menu.items.tnt-reset-button.material", Material.RECOVERY_COMPASS),
                    config.message("menu.items.tnt-reset-button.name"),
                    List.of(
                            config.message("menu.items.tnt-reset-button.lore"),
                            config.message("menu.items.tnt-reset-button.blocks", "%blocks%", String.valueOf(tntResetService.savedBlocks()))
                    )
            ));
        }

        player.openInventory(inventory);
    }

    private List<Component> stateLore(boolean enabled) {
        String path = enabled ? "menu.state.enabled" : "menu.state.disabled";
        return List.of(config.message(path));
    }

    private ItemStack createItem(Material material, Component name, List<Component> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(lore);
        itemMeta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
