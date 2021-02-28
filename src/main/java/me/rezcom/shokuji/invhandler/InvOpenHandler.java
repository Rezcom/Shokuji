package me.rezcom.shokuji.invhandler;

import me.rezcom.shokuji.ItemDescHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

public class InvOpenHandler implements Listener {

    public static boolean enabled = false;

    @EventHandler
    void onPlayerOpenInv(PlayerLoginEvent event){
        //System.out.println("INVENTORY INTERACT");
        if (enabled){
            Player player = event.getPlayer();
            for (ItemStack item : player.getInventory().getContents()){
                ItemDescHandler.setItemDesc(item);
            }
        }
    }
}
