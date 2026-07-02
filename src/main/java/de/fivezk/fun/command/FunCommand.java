package de.fivezk.fun.command;

import de.fivezk.fun.menu.FunMenu;
import de.fivezk.fun.config.FunConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class FunCommand implements CommandExecutor {

    private final FunMenu funMenu;
    private final FunConfig config;

    public FunCommand(FunMenu funMenu, FunConfig config) {
        this.funMenu = funMenu;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(config.message("messages.player-only"));
            return true;
        }

        funMenu.open(player);
        return true;
    }
}
