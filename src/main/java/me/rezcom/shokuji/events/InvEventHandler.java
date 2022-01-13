package me.rezcom.shokuji.events;

import me.rezcom.shokuji.ItemDescHandler;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class InvEventHandler implements Listener {

    public static boolean enabled = true;

    @EventHandler
    void onInvOpen(InventoryOpenEvent event){
        Inventory inventory = event.getInventory();
        for (ItemStack i : inventory.getContents()){
            if (i != null){
                ItemDescHandler.setItemDesc(i);
            }
        }
    }

    @EventHandler
    void onItemPickup(EntityPickupItemEvent event){
        ItemStack itemStack = event.getItem().getItemStack();
        ItemDescHandler.setItemDesc(itemStack);
    }

    @EventHandler
    void onItemCraft(PrepareItemCraftEvent event){
        ItemStack itemStack = event.getInventory().getResult();
        ItemDescHandler.setItemDesc(itemStack);
    }

    @EventHandler
    void onPlayerCloseInv(InventoryCloseEvent event){
        if (enabled){
            HumanEntity player = event.getPlayer();
            for (ItemStack i : player.getInventory().getContents()){
                if (i != null){
                    ItemDescHandler.setItemDesc(i);
                }
            }
        }
    }

    @EventHandler
    void onPlayerDrag(InventoryDragEvent event){
        if ( !enabled || !(event.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player)event.getWhoClicked();
        Set<Integer> slots = event.getInventorySlots();
        for (Integer index : slots){
            if (event.getInventory().getItem(index) != null) {
                ItemDescHandler.setItemDesc(event.getInventory().getItem(index));
            }
        }
        for (ItemStack item : player.getInventory().getContents()){
            if (item != null){
                ItemDescHandler.setItemDesc(item);
            }
        }
    }


}
