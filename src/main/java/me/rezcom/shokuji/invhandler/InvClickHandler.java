package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.FoodDataHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvClickHandler implements Listener {
    @EventHandler
    void onPlayerClickInv(InventoryClickEvent event){
        for (ItemStack item : event.getInventory().getContents()){
            FoodDataHandler.setItemDesc(item);
        }
    }
}
