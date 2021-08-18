package net.lxsthw.redelite.reports.bungee.cmd;

import net.fancyful.FancyMessage;
import net.lxsthw.redelite.reports.listeners.ReportStorage;
import net.lxsthw.redelite.reports.objects.ReportedUser;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

 public class ReportCommand extends Command {

     public ReportCommand() {
         super("report", "reports");
     }

     public void execute(CommandSender sender, String[] args) {
         if (sender instanceof ProxiedPlayer) {
             ProxiedPlayer p = (ProxiedPlayer) sender;
             if (args.length > 0) {
                 p.sendMessage("§cUtilize: /report <usuário> para reportar algum jogador. ");
                 return;
             }
             if (args.length == 1) {
                 if (Bukkit.getPlayer(args[0]) != null) {
                     Player reported = Bukkit.getPlayer(args[0]);
                     if (reported.getName().equalsIgnoreCase(p.getName())) {
                         p.sendMessage("§cVocê não pode reportar você mesmo.");
                         return;
                     }
                     if (!reported.hasPermission("redelite.cmd.report")) {
                         if (ReportStorage.isPlayerReported(args[0])) {
                             if (!ReportStorage.getReportList().get(reported.getName()).getReportedBy().contains(p.getName())) {
                                 ReportStorage.getReportList().get(reported.getName()).setReportedX(ReportStorage.getReportList().get(reported.getName()).getReportedX() + 1);
                                 p.sendMessage("");
                                 p.sendMessage("§e* §eVocê reportou o jogador " + (PermissionsEx.getUser(reported).getPrefix().equalsIgnoreCase("&f") ? "§7" + reported.getName() : PermissionsEx.getUser(reported).getPrefix().replace("&", "§") + " " + reported.getName()) + "§e. §eUm membro de");
                                 p.sendMessage("§e* §enossa equipe foi notificado e o comportamento deste jogador será analisado em breve.");
                                 p.sendMessage("");
                                 p.sendMessage("§e* §7O uso abusivo deste comando poderá resultar em punição.");
                                 p.sendMessage("");
                             } else {
                                 p.sendMessage("§cVocê já reportou este jogador.");
                                 return;
                             }
                         } else {
                             ArrayList<String> reportedBy = new ArrayList<String>();
                             reportedBy.add(p.getName());
                             ReportedUser ru = new ReportedUser(reported.getName(), 1, reportedBy);
                             ReportStorage.getReportList().put(reported.getName(), ru);
                             p.sendMessage("");
                             p.sendMessage("§e* §eVocê reportou o jogador " + (PermissionsEx.getUser(reported).getPrefix().equalsIgnoreCase("&f") ? "§7" + reported.getName() : PermissionsEx.getUser(reported).getPrefix().replace("&", "§") + " " + reported.getName()) + "§e. §eUm membro de");
                             p.sendMessage("§e* §enossa equipe foi notificado e o comportamento deste jogador será analisado em breve.");
                             p.sendMessage("");
                             p.sendMessage("§e* §7O uso abusivo deste comando poderá resultar em punição.");
                             p.sendMessage("");
                         }
                     } else {
                         p.sendMessage("§cVocê não pode reportar um jogador membro da equipe.");
                         return;
                     }
                 } else {
                     p.sendMessage("§cEste jogador está offline.");
                     return;
                 }
             }
         }
        return;
    }

    public static void notifyStaffs(Player reported,Player owner) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("redelite.cmd.report")) {
                if (!ReportStorage.getDisableNotify().contains(online.getName())) {
                    online.playSound(online.getLocation(), Sound.LEVEL_UP, 1, 1);
                    online.sendMessage("");
                    online.sendMessage("§7* §eO jogador "+(PermissionsEx.getUser(reported).getPrefix().equalsIgnoreCase("&f") ? "§7"+reported.getName() : PermissionsEx.getUser(reported).getPrefix().replace("&", "§")+" "+reported.getName())+"§e foi reportado pelo jogador "+(PermissionsEx.getUser(owner.getName()).getPrefix().equalsIgnoreCase("&f") ? "§7"+owner.getName() : PermissionsEx.getUser(owner.getName()).getPrefix().replace("&", "§")+" "+owner.getName()));
                    new FancyMessage("§e* Clique ").then("§6§lAQUI").tooltip("§eAbrir lista de reportes").command("/reports lista").then("§e para abrir a lista de reportes.").send(online);
                    online.sendMessage("");
                }
            }
        }
    }
}

