package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.ItemDescHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;


public class InvDragHandler implements Listener {

    public static boolean enabled = false;

    @EventHandler
    void onPlayerDrag(InventoryDragEvent event){
        if ( !enabled || !(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player)event.getWhoClicked();
        Set<Integer> slots = event.getInventorySlots();
        for (Integer index : slots){
            if (event.getInventory().getItem(index) != null) { ItemDescHandler.setItemDesc(event.getInventory().getItem(index)); }
        }
        for (ItemStack item : player.getInventory().getContents()){
            if (item != null){ ItemDescHandler.setItemDesc(item); }
        }
    }
}
