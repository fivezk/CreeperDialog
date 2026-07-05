package de.fivezk.fun.wand.service;

import de.fivezk.fun.wand.WandType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Optional;

public final class WandService {

    private final JavaPlugin plugin;
    private final NamespacedKey wandKey;

    public WandService(JavaPlugin plugin) {
        this.plugin = plugin;
        wandKey = new NamespacedKey(plugin, "wand");
    }

    public void give(Player player, WandType wandType) {
        player.getInventory().addItem(create(wandType));
    }

    public Optional<WandType> type(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != Material.STICK || !itemStack.hasItemMeta()) {
            return Optional.empty();
        }

        String value = itemStack.getItemMeta().getPersistentDataContainer().get(wandKey, PersistentDataType.STRING);
        if (value == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(WandType.valueOf(value));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    public void shoot(Player player, WandType wandType) {
        Vector direction = player.getEyeLocation().getDirection().normalize();
        if (wandType == WandType.WITHER_SKULL) {
            WitherSkull witherSkull = player.getWorld().spawn(player.getEyeLocation().add(direction.multiply(1.5)), WitherSkull.class);
            Vector velocity = player.getEyeLocation().getDirection().normalize().multiply(3.2);
            witherSkull.setShooter(player);
            witherSkull.setCharged(true);
            witherSkull.setGravity(false);
            witherSkull.setDirection(player.getEyeLocation().getDirection().normalize());
            witherSkull.setVelocity(velocity);
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (witherSkull.isValid()) {
                    witherSkull.setDirection(player.getEyeLocation().getDirection().normalize());
                    witherSkull.setVelocity(velocity);
                }
            });
            return;
        }

        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setVelocity(direction.multiply(1.6));
        fireball.setYield(3.0f);
        fireball.setIsIncendiary(true);
    }

    private ItemStack create(WandType wandType) {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name(wandType));
        itemMeta.lore(List.of(Component.text("Rechtsklick zum Schießen", NamedTextColor.GRAY)));
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.getPersistentDataContainer().set(wandKey, PersistentDataType.STRING, wandType.name());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private Component name(WandType wandType) {
        if (wandType == WandType.WITHER_SKULL) {
            return Component.text("Wither Kopf Stab", NamedTextColor.DARK_PURPLE);
        }

        return Component.text("Ghast Feuerball Stab", NamedTextColor.GOLD);
    }
}
