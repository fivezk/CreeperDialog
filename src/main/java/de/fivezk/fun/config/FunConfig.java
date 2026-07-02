package de.fivezk.fun.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class FunConfig {

    private final JavaPlugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public FunConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean creeperDialogEnabled() {
        return plugin.getConfig().getBoolean("features.creeper-dialog.enabled", true);
    }

    public void setCreeperDialogEnabled(boolean enabled) {
        plugin.getConfig().set("features.creeper-dialog.enabled", enabled);
        plugin.saveConfig();
    }

    public boolean tntResetEnabled() {
        return plugin.getConfig().getBoolean("features.tnt-reset.enabled", false);
    }

    public void setTntResetEnabled(boolean enabled) {
        plugin.getConfig().set("features.tnt-reset.enabled", enabled);
        plugin.saveConfig();
    }

    public Component message(String path) {
        return miniMessage.deserialize(plugin.getConfig().getString(path, ""));
    }

    public Component message(String path, String placeholder, String value) {
        String message = plugin.getConfig().getString(path, "");
        return miniMessage.deserialize(message.replace(placeholder, value));
    }

    public int integer(String path, int fallback) {
        return plugin.getConfig().getInt(path, fallback);
    }

    public Material material(String path, Material fallback) {
        String value = plugin.getConfig().getString(path, fallback.name());
        Material material = Material.matchMaterial(value);
        return material == null ? fallback : material;
    }
}
