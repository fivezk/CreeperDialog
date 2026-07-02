package de.fivezk.fun.command;

import de.fivezk.fun.menu.FunMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class FunCommand implements CommandExecutor {

    private final FunMenu funMenu;

    public FunCommand(FunMenu funMenu) {
        this.funMenu = funMenu;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Dieser Befehl kann nur von Spielern genutzt werden.", NamedTextColor.RED));
            return true;
        }

        funMenu.open(player);
        return true;
    }
}
