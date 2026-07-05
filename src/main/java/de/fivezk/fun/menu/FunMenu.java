package de.fivezk.fun.menu;

import de.fivezk.fun.creeper.service.CreeperDialogService;
import de.fivezk.fun.tnt.service.TntResetService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
    public static final int RESET_SLOT = 24;
    public static final int WITHER_WAND_SLOT = 30;
    public static final int FIREBALL_WAND_SLOT = 32;
    private final CreeperDialogService creeperDialogService;
    private final TntResetService tntResetService;

    public FunMenu(CreeperDialogService creeperDialogService, TntResetService tntResetService) {
        this.creeperDialogService = creeperDialogService;
        this.tntResetService = tntResetService;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new FunMenuHolder(), 36, Component.text("Fun", NamedTextColor.DARK_GREEN));
        inventory.setItem(CREEPER_SLOT, createItem(
                Material.CREEPER_HEAD,
                Component.text("Creeper Dialog", NamedTextColor.GREEN),
                stateLore(creeperDialogService.enabled())
        ));
        inventory.setItem(TNT_SLOT, createItem(
                Material.TNT,
                Component.text("TNT Reset", NamedTextColor.RED),
                stateLore(tntResetService.enabled())
        ));

        if (tntResetService.enabled()) {
            inventory.setItem(RESET_SLOT, createItem(
                    Material.RECOVERY_COMPASS,
                    Component.text("Blöcke zurücksetzen", NamedTextColor.GOLD),
                    List.of(
                            Component.text("Setzt gespeicherte TNT-Schäden animiert zurück.", NamedTextColor.GRAY),
                            Component.text("Gespeicherte Blöcke: ", NamedTextColor.GRAY)
                                    .append(Component.text(tntResetService.savedBlocks(), NamedTextColor.YELLOW))
                    )
            ));
        }

        inventory.setItem(WITHER_WAND_SLOT, createItem(
                Material.STICK,
                Component.text("Wither Kopf Stab", NamedTextColor.DARK_PURPLE),
                List.of(Component.text("Gibt dir einen Stab für Wither-Köpfe.", NamedTextColor.GRAY))
        ));
        inventory.setItem(FIREBALL_WAND_SLOT, createItem(
                Material.STICK,
                Component.text("Ghast Feuerball Stab", NamedTextColor.GOLD),
                List.of(Component.text("Gibt dir einen Stab für Ghast-Feuerbälle.", NamedTextColor.GRAY))
        ));

        player.openInventory(inventory);
    }

    private List<Component> stateLore(boolean enabled) {
        return List.of(enabled
                ? Component.text("Aktiviert", NamedTextColor.GREEN)
                : Component.text("Deaktiviert", NamedTextColor.RED));
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
