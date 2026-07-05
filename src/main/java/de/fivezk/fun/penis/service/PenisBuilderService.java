package de.fivezk.fun.penis.service;

import de.fivezk.fun.config.FunConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public final class PenisBuilderService {

    private final FunConfig config;

    public PenisBuilderService(FunConfig config) {
        this.config = config;
    }

    public boolean enabled() {
        return config.penisBuilderEnabled();
    }

    public void setEnabled(boolean enabled, Player player) {
        config.setPenisBuilderEnabled(enabled);
        if (enabled) {
            spawn(player);
        }
    }

    private void spawn(Player player) {
        World world = player.getWorld();
        Location center = player.getLocation();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 100; i++) {
            double x = center.getX() + random.nextDouble(-100.0, 100.0);
            double z = center.getZ() + random.nextDouble(-100.0, 100.0);
            double y = Math.min(world.getMaxHeight() - 6.0, center.getY() + random.nextDouble(35.0, 70.0));
            Location origin = new Location(world, x, y, z);
            spawnStructure(origin);
        }
    }

    private void spawnStructure(Location origin) {
        BlockData whiteWool = Material.WHITE_WOOL.createBlockData();
        BlockData pinkWool = Material.PINK_WOOL.createBlockData();

        spawnBlock(origin.clone().add(0.0, 3.0, 0.0), whiteWool);
        spawnBlock(origin.clone().add(0.0, 2.0, 0.0), pinkWool);
        spawnBlock(origin.clone().add(0.0, 1.0, 0.0), pinkWool);
        spawnBlock(origin.clone().add(-1.0, 0.0, 0.0), pinkWool);
        spawnBlock(origin.clone().add(1.0, 0.0, 0.0), pinkWool);
    }

    private void spawnBlock(Location location, BlockData blockData) {
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, blockData);
        fallingBlock.setDropItem(false);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setVelocity(new Vector(0.0, -0.15, 0.0));
    }
}
