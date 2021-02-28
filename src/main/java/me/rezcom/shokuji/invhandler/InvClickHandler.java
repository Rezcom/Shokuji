package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.ItemDescHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvClickHandler implements Listener {

    public static boolean enabled = false;

    @EventHandler
    void onPlayerClickInv(InventoryClickEvent event){
        if (enabled){
            for (ItemStack item : event.getInventory().getContents()){
                ItemDescHandler.setItemDesc(item);
            }
        }
    }
}
