package de.fivezk.fun.creeper.service;

import de.fivezk.fun.config.FunConfig;
import de.fivezk.fun.creeper.dialog.CreeperExplosionDialog;
import de.fivezk.fun.creeper.model.PendingCreeper;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class CreeperDialogService {

    private final JavaPlugin plugin;
    private final FunConfig config;
    private final CreeperExplosionDialog creeperExplosionDialog;
    private final Map<UUID, PendingCreeper> pendingCreepers = new HashMap<>();
    private final Set<UUID> releasedCreepers = new HashSet<>();

    public CreeperDialogService(JavaPlugin plugin, FunConfig config, CreeperExplosionDialog creeperExplosionDialog) {
        this.plugin = plugin;
        this.config = config;
        this.creeperExplosionDialog = creeperExplosionDialog;
    }

    public boolean enabled() {
        return config.creeperDialogEnabled();
    }

    public void setEnabled(boolean enabled) {
        config.setCreeperDialogEnabled(enabled);
        if (!enabled) {
            clearPending();
        }
    }

    public boolean handleExplosionPrime(Creeper creeper, Player player) {
        if (!enabled()) {
            return false;
        }

        if (releasedCreepers.remove(creeper.getUniqueId())) {
            return false;
        }

        if (pendingCreepers.containsKey(player.getUniqueId())) {
            return true;
        }

        PendingCreeper pendingCreeper = PendingCreeper.freeze(creeper);
        pendingCreepers.put(player.getUniqueId(), pendingCreeper);
        player.showDialog(creeperExplosionDialog.create());
        return true;
    }

    public void handleClick(Player player, Key identifier) {
        PendingCreeper pendingCreeper = pendingCreepers.remove(player.getUniqueId());
        if (pendingCreeper == null) {
            return;
        }

        if (identifier.equals(CreeperExplosionDialog.EXPLODE_KEY)) {
            releasedCreepers.add(pendingCreeper.uniqueId());
            pendingCreeper.explode();
            return;
        }

        if (identifier.equals(CreeperExplosionDialog.DENY_KEY)) {
            pendingCreeper.removeLater(plugin);
            return;
        }

        pendingCreeper.restore();
    }

    public void handleQuit(Player player) {
        PendingCreeper pendingCreeper = pendingCreepers.remove(player.getUniqueId());
        if (pendingCreeper != null) {
            pendingCreeper.restore();
        }
    }

    public void close() {
        clearPending();
    }

    public void clearPending() {
        pendingCreepers.values().forEach(PendingCreeper::restore);
        pendingCreepers.clear();
        releasedCreepers.clear();
    }
}
