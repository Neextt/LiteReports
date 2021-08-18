package net.lxsthw.redelite.reports.bungee;

import net.lxsthw.redelite.reports.bungee.cmd.Commands;
import net.lxsthw.redelite.reports.bungee.cmd.ReportCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Bungee extends Plugin {

    public static Map<ProxiedPlayer, ProxiedPlayer> baianice = new HashMap<>();
    private static Bungee instance;

    public Bungee() {
        instance = this;
    }

    public static Bungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        Commands.setupCommands();
        this.getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        this.getLogger().info("O plugin foi ativado.");
    }
}
