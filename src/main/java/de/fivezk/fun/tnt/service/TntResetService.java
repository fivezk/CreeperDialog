package de.fivezk.fun.tnt.service;

import de.fivezk.fun.config.FunConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TntResetService {

    private final JavaPlugin plugin;
    private final FunConfig config;
    private final Map<Location, BlockState> blockStates = new LinkedHashMap<>();
    private BukkitTask resetTask;

    public TntResetService(JavaPlugin plugin, FunConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public boolean enabled() {
        return config.tntResetEnabled();
    }

    public void setEnabled(boolean enabled) {
        config.setTntResetEnabled(enabled);
        if (!enabled) {
            blockStates.clear();
        }
    }

    public boolean handleExplosion(List<Block> blocks) {
        if (resetRunning()) {
            return true;
        }

        if (!enabled()) {
            return false;
        }

        blocks.stream()
                .filter(block -> block.getType() != Material.TNT)
                .forEach(block -> blockStates.putIfAbsent(block.getLocation(), block.getState()));
        return false;
    }

    public boolean handlePrime(Block block) {
        if (resetRunning()) {
            return true;
        }

        if (!enabled()) {
            return false;
        }

        return false;
    }

    public int savedBlocks() {
        return blockStates.size();
    }

    public void reset(Player player) {
        if (resetRunning() || blockStates.isEmpty()) {
            return;
        }

        List<BlockState> states = new ArrayList<>(blockStates.values());
        int totalBlocks = states.size();
        blockStates.clear();
        int blocksPerStep = config.integer("tnt-reset.animation.blocks-per-step", 24);
        long interval = config.integer("tnt-reset.animation.interval-ticks", 1);

        resetTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            private int index;

            @Override
            public void run() {
                for (int i = 0; i < blocksPerStep && index < states.size(); i++) {
                    BlockState state = states.get(index++);
                    state.getBlock().setType(state.getType(), false);
                    state.update(true, false);
                    Location location = state.getLocation().toCenterLocation();
                    location.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, location, 6, 0.35, 0.35, 0.35, 0.0);
                    location.getWorld().playSound(location, Sound.BLOCK_WOOD_PLACE, 0.45f, 1.25f);
                }

                if (index >= states.size()) {
                    resetTask.cancel();
                    resetTask = null;
                    player.sendMessage(Component.text("Explodierte Blöcke zurückgesetzt.", NamedTextColor.GREEN));
                    player.sendMessage(Component.text("Blöcke: ", NamedTextColor.GRAY)
                            .append(Component.text(totalBlocks, NamedTextColor.YELLOW)));
                }
            }
        }, 0L, Math.max(1L, interval));
    }

    public void close() {
        if (resetTask != null) {
            resetTask.cancel();
            resetTask = null;
        }
        blockStates.clear();
    }

    private boolean resetRunning() {
        return resetTask != null && !resetTask.isCancelled();
    }
}
