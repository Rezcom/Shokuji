package me.rezcom.shokuji.events;

import me.rezcom.shokuji.ItemDescHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.inventory.ItemStack;

public class CookEvent implements Listener {

    @EventHandler
    void onBlockCooked(BlockCookEvent event){
        ItemStack itemStack = event.getResult();
        ItemDescHandler.setItemDesc(itemStack);
    }
}
