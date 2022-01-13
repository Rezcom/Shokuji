package me.rezcom.shokuji;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

    // For when an entity dies, change the foods
    // within its inventory to fit the description.
    @EventHandler
    void onEntityDies(EntityDeathEvent event){
        for (ItemStack i : event.getDrops()){
            ItemDescHandler.setItemDesc(i);
        }
    }
}
