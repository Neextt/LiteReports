package net.lxsthw.redelite.reports.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import net.lxsthw.redelite.reports.objects.ReportedUser;
import net.lxsthw.redelite.reports.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ReportStorage {

    public static HashMap<String, ReportedUser> reportList = new HashMap<String,ReportedUser>();
    public static ArrayList<String> disableNotify = new ArrayList<String>();

    public static HashMap<String, ReportedUser> getReportList() {
        return reportList;
    }
    public static boolean isPlayerReported(Player p) {
        return reportList.containsKey(p.getName());
    }
    public static boolean isPlayerReported(String p) {
        return reportList.containsKey(p);
    }
    public static ArrayList<String> getDisableNotify() {
        return disableNotify;
    }
    public static void setDisableNotify(ArrayList<String> disableNotify) {
        ReportStorage.disableNotify = disableNotify;
    }

    public static void openMenuConfirmation(String user,Player p) {
        if(ReportStorage.getReportList().containsKey(user)) {
            Inventory inv = Bukkit.createInventory(null, 45,"Avaliar o Jogador");
            ArrayList<String> lore = new ArrayList<String>();
            lore.add("§7Quantidade Total de Reportes: §f"+ReportStorage.getReportList().get(user).getReportedX());
            lore.add("§7Status do Jogador: "+(Bukkit.getPlayer(user)!=null ? "§aOnline" : "§4Offline"));
            lore.add("");
            lore.add("§7Jogadores que reportaram este jogador:");
            for(String by : ReportStorage.getReportList().get(user).getReportedBy()) {
                lore.add((PermissionsEx.getUser(by).getPrefix().equalsIgnoreCase("&f") ? "§7"+by : PermissionsEx.getUser(by).getPrefix().replace("&", "§")+" "+by));
            }
            ItemStack i = new ItemBuilder(Material.SKULL_ITEM).setDurability((short)3).setSkullOwner(user).setName((PermissionsEx.getUser(user).getPrefix().equalsIgnoreCase("&f") ? "§7"+user : PermissionsEx.getUser(user).getPrefix().replace("&", "§")+" "+user))
                    .setLore(lore).toItemStack();
            inv.setItem(13, i);
            inv.setItem(29, new ItemBuilder(Material.BARRIER).setName("§cExcluir jogador da lista de reportes").setLore("§7Excluindo o jogador da lista de reportes","§7você estará dizendo ao nosso sistema que","§7este jogador já foi avaliado ou revisado.").toItemStack());
            inv.setItem(33, new ItemBuilder(Material.WOOL).setDurability((short)14).setName("§cCancelar esta ação").setLore("§7Clique aqui para cancelar").toItemStack());
            p.openInventory(inv);
        }else {
            p.sendMessage("§cDesculpe, Este jogador já foi avaliado.");
            return;
        }
    }

}
