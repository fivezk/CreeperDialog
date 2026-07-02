package de.fivezk.fun.creeper.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.UUID;

public record PendingCreeper(UUID uniqueId, boolean ai, boolean gravity, boolean invulnerable) {

    public static PendingCreeper freeze(Creeper creeper) {
        PendingCreeper pendingCreeper = new PendingCreeper(
                creeper.getUniqueId(),
                creeper.hasAI(),
                creeper.hasGravity(),
                creeper.isInvulnerable()
        );
        creeper.setAI(false);
        creeper.setGravity(false);
        creeper.setInvulnerable(true);
        creeper.setVelocity(new Vector());
        return pendingCreeper;
    }

    public void explode() {
        Creeper creeper = getCreeper();
        if (creeper == null) {
            return;
        }
        restore(creeper);
        creeper.ignite();
    }

    public void removeLater(JavaPlugin plugin) {
        Creeper creeper = getCreeper();
        if (creeper == null) {
            return;
        }
        restore(creeper);
        creeper.setVelocity(new Vector(0.0, 3.5, 0.0));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Creeper current = getCreeper();
            if (current != null && current.isValid()) {
                current.setHealth(0.0);
            }
        }, 30L);
    }

    public void restore() {
        Creeper creeper = getCreeper();
        if (creeper != null) {
            restore(creeper);
        }
    }

    private void restore(Creeper creeper) {
        creeper.setAI(ai);
        creeper.setGravity(gravity);
        creeper.setInvulnerable(invulnerable);
    }

    private Creeper getCreeper() {
        Entity entity = Bukkit.getEntity(uniqueId);
        if (entity instanceof Creeper creeper && creeper.isValid() && !creeper.isDead()) {
            return creeper;
        }
        return null;
    }
}
