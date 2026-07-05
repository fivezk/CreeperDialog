package de.fivezk.fun.config;

import org.bukkit.plugin.java.JavaPlugin;

public final class FunConfig {

    private final JavaPlugin plugin;

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

    public boolean penisBuilderEnabled() {
        return plugin.getConfig().getBoolean("features.penis-builder.enabled", false);
    }

    public void setPenisBuilderEnabled(boolean enabled) {
        plugin.getConfig().set("features.penis-builder.enabled", enabled);
        plugin.saveConfig();
    }

    public int integer(String path, int fallback) {
        return plugin.getConfig().getInt(path, fallback);
    }
}
