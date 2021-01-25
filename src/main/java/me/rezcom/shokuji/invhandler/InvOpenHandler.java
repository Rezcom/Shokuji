package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.FoodDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

public class InvOpenHandler implements Listener {

    @EventHandler
    void onPlayerOpenInv(PlayerLoginEvent event){
        //System.out.println("INVENTORY INTERACT");
        Player player = event.getPlayer();
        for (ItemStack item : player.getInventory().getContents()){
            FoodDataHandler.setItemDesc(item);
        }
    }
}
