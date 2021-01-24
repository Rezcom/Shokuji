package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.FoodDataHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InvCloseHandler implements Listener {
    @EventHandler
    void onPlayerCloseInv(InventoryCloseEvent event){
        for (ItemStack item : event.getInventory().getContents()){
            FoodDataHandler.setItemDesc(item);
        }
    }
}
