package net.lxsthw.redelite.reports.commands;

import java.util.ArrayList;

import net.lxsthw.redelite.reports.Main;
import net.lxsthw.redelite.reports.listeners.ReportStorage;
import net.lxsthw.redelite.reports.utils.ItemBuilder;
import net.lxsthw.redelite.reports.utils.ScrollerInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CommandReports implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if(cmd.getName().equalsIgnoreCase("reports")) {
            if(sender instanceof Player) {
                Player p = (Player)sender;
                if(p.hasPermission("redelite.cmd.reports")) {
                    if(args.length == 0) {
                        p.sendMessage("§cUtilize: /reports [on/off/lista].");
                        return true;
                    }
                    if(args.length >= 1) {
                        if(args[0].equalsIgnoreCase("on")) {
                            if(ReportStorage.getDisableNotify().contains(p.getName())) {
                                ReportStorage.getDisableNotify().remove(p.getName());
                                p.sendMessage("§aMensagens de report reabilitadas.");
                            }else {
                                p.sendMessage("§cAs suas mensagens de report já estão habilitadas.");
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("off")) {
                            if(!ReportStorage.getDisableNotify().contains(p.getName())) {
                                ReportStorage.getDisableNotify().add(p.getName());
                                p.sendMessage("§aMensagens de reporte desabilitadas.");
                            }else {
                                p.sendMessage("§cAs suas mensagens de reporte já estão desabilitadas.");
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("lista")) {
                            ArrayList<ItemStack> itens = new ArrayList<ItemStack>();
                            for(String user : ReportStorage.getReportList().keySet()) {
                                ArrayList<String> lore = new ArrayList<String>();
                                lore.add("§7Quantidade Total de Reportes: §f"+ReportStorage.getReportList().get(user).getReportedX());
                                lore.add("§7Status do Jogador: "+(Bukkit.getPlayer(user)!=null ? "§aOnline" : "§4Offline"));
                                lore.add("");
                                lore.add("§7Jogadores que reportaram este jogador:");
                                for(String by : ReportStorage.getReportList().get(user).getReportedBy()) {
                                    lore.add((PermissionsEx.getUser(by).getPrefix().equalsIgnoreCase("&f") ? "§7"+by : PermissionsEx.getUser(by).getPrefix().replace("&", "§")+" "+by));
                                }
                                lore.add("");
                                lore.add("§7Botão direito: §fAvaliar");
                                lore.add("§7Botão esquerdo: §fTeleportar-se até o jogador.");
                                ItemStack i = new ItemBuilder(Material.SKULL_ITEM).setDurability((short)3).setSkullOwner(user).setName((PermissionsEx.getUser(user).getPrefix().equalsIgnoreCase("&f") ? "§7"+user : PermissionsEx.getUser(user).getPrefix().replace("&", "§")+" "+user))
                                        .setLore(lore).toItemStack();
                                itens.add(i);
                            }
                            new ScrollerInventory(itens, "Lista de Reportes", p);
                        }else {
                            p.sendMessage("§cUtilize: /reports [on/off/lista].");
                            return true;
                        }
                    }
                }else {
                    p.sendMessage("§cVocê precisa ser do grupo §eAjudante §cou maior para executar este comando.");
                    return true;
                }
            }
        }
        return false;
    }

}

