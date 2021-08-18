package net.lxsthw.redelite.reports;

import net.lxsthw.redelite.reports.commands.CommandReport;
import net.lxsthw.redelite.reports.commands.CommandReports;
import net.lxsthw.redelite.reports.listeners.ReportList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;
    public static Main getInstance() {
        return Main.instance;
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Inicializando...");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ReportList(), this);
        getCommand("report").setExecutor(new CommandReport());
        getCommand("reports").setExecutor(new CommandReports());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.getLogger().info("O plugin foi ativado.");

    }

    @Override
    public void onDisable() {
        this.getLogger().info("O plugin foi desativado.");
    }
}
