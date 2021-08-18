package net.lxsthw.redelite.reports.listeners;

import net.lxsthw.redelite.reports.utils.ScrollerInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ReportList implements Listener {

    @EventHandler
    public void onWork(InventoryClickEvent event) {
        if(event.getInventory().getName().equalsIgnoreCase("Avaliar o Jogador")) {
            event.setCancelled(true);
            Player p = (Player)event.getWhoClicked();
            if(event.getInventory().getItem(13)!=null) {
                if(event.getSlot() == 29) {
                    ItemStack skullclicked = event.getInventory().getItem(13);
                    SkullMeta skullMeta = (SkullMeta) skullclicked.getItemMeta();
                    String user = skullMeta.getOwner();
                    if(ReportStorage.getReportList().containsKey(user)) {
                        ReportStorage.getReportList().remove(user);
                        p.sendMessage("§aUsuário removido da lista de avaliações.");
                    }
                    p.closeInventory();
                }
                if(event.getSlot() == 33) {
                    p.closeInventory();
                    p.sendMessage("§cAção cancelada.");
                }
            }
        }
    }
    @EventHandler
    public void onClickOnHead(InventoryClickEvent event) {
        if(event.getInventory().getName().equalsIgnoreCase("Lista de Reportes")) {
            if(event.getCurrentItem()!=null) {
                if(event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                    event.setCancelled(true);
                    if(event.getClick().equals(ClickType.LEFT)) {
                        Player p = (Player)event.getWhoClicked();
                        ItemStack skullclicked = event.getCurrentItem();
                        SkullMeta skullMeta = (SkullMeta) skullclicked.getItemMeta();
                        String user = skullMeta.getOwner();
                        if(user !=null) {
                            if(Bukkit.getPlayer(user)!=null) {
                                p.teleport(Bukkit.getPlayer(user).getLocation());
                                p.sendMessage("§aTeleportado para o jogador selecionado.");
                                p.closeInventory();
                            }
                        }
                    }
                    if(event.getClick().equals(ClickType.RIGHT)) {
                        Player p = (Player)event.getWhoClicked();
                        ItemStack skullclicked = event.getCurrentItem();
                        SkullMeta skullMeta = (SkullMeta) skullclicked.getItemMeta();
                        String user = skullMeta.getOwner();
                        if(user !=null) {
                            ReportStorage.openMenuConfirmation(user, p);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player p = (Player) event.getWhoClicked();
        if(!event.getInventory().getName().equalsIgnoreCase("Lista de Reportes")) return;
        if(!ScrollerInventory.users.containsKey(p.getUniqueId())) return;
        ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        //If the pressed item was a nextpage button
        if(event.getCurrentItem().getType().equals(Material.ARROW)) {
            if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if(event.getSlot() == 26) {
                    event.setCancelled(true);
                    //If there is no next page, don't do anything
                    if(inv.currpage >= inv.pages.size()-1){
                        return;
                    }else{
                        //Next page exists, flip the page
                        inv.currpage += 1;
                        p.openInventory(inv.pages.get(inv.currpage));
                    }
                }
                if(event.getSlot() == 18) {
                    event.setCancelled(true);
                    //If the page number is more than 0 (So a previous page exists)
                    if(inv.currpage > 0){
                        //Flip to previous page
                        inv.currpage -= 1;
                        p.openInventory(inv.pages.get(inv.currpage));
                    }
                }
            }
        }
    }
}
