package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.FoodDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;


public class InvDragHandler implements Listener {

    @EventHandler
    void onPlayerDrag(InventoryDragEvent event){
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player)event.getWhoClicked();
        Set<Integer> slots = event.getInventorySlots();
        for (Integer index : slots){
            FoodDataHandler.setItemDesc(event.getInventory().getItem(index));
        }
        for (ItemStack item : player.getInventory().getContents()){
            FoodDataHandler.setItemDesc(item);
        }
    }
}
