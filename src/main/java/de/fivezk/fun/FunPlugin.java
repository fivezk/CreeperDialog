package de.fivezk.fun;

import de.fivezk.fun.command.FunCommand;
import de.fivezk.fun.config.FunConfig;
import de.fivezk.fun.creeper.dialog.CreeperExplosionDialog;
import de.fivezk.fun.creeper.listener.CreeperDialogClickListener;
import de.fivezk.fun.creeper.listener.CreeperExplosionListener;
import de.fivezk.fun.creeper.listener.PlayerConnectionListener;
import de.fivezk.fun.creeper.service.CreeperDialogService;
import de.fivezk.fun.menu.FunMenu;
import de.fivezk.fun.menu.FunMenuListener;
import de.fivezk.fun.tnt.listener.TntExplosionListener;
import de.fivezk.fun.tnt.service.TntResetService;
import de.fivezk.fun.wand.listener.WandListener;
import de.fivezk.fun.wand.service.WandService;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FunPlugin extends JavaPlugin {

    private CreeperDialogService creeperDialogService;
    private TntResetService tntResetService;
    private WandService wandService;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        FunConfig config = new FunConfig(this);
        CreeperExplosionDialog creeperExplosionDialog = new CreeperExplosionDialog();
        creeperDialogService = new CreeperDialogService(this, config, creeperExplosionDialog);
        tntResetService = new TntResetService(this, config);
        wandService = new WandService(this);
        FunMenu funMenu = new FunMenu(creeperDialogService, tntResetService);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CreeperExplosionListener(creeperDialogService), this);
        pluginManager.registerEvents(new CreeperDialogClickListener(creeperDialogService), this);
        pluginManager.registerEvents(new PlayerConnectionListener(creeperDialogService), this);
        pluginManager.registerEvents(new TntExplosionListener(tntResetService), this);
        pluginManager.registerEvents(new WandListener(wandService), this);
        pluginManager.registerEvents(new FunMenuListener(funMenu, creeperDialogService, tntResetService, wandService), this);

        FunCommand funCommand = new FunCommand(funMenu);
        PluginCommand command = Objects.requireNonNull(getCommand("fun"));
        command.setExecutor(funCommand);
    }

    @Override
    public void onDisable() {
        if (creeperDialogService != null) {
            creeperDialogService.close();
        }

        if (tntResetService != null) {
            tntResetService.close();
        }
    }
}
