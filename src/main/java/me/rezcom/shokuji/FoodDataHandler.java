package me.rezcom.shokuji;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class FoodDataHandler extends PacketAdapter {

    FoodDataHandler(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType){
        super(plugin, listenerPriority, packetType);
    }



    @Override
    public void onPacketSending(PacketEvent event){
        PacketContainer packet = event.getPacket();
        // Listen for when a player holds a food item in their hand
        //System.out.println("SET SLOT");
        ItemStack item = packet.getItemModifier().read(0);

        if (item.getType() != Material.AIR){

            // If the food is in the list of foods needing change...
            if (FoodInfo.allMapsContain(item.getType())){
                ItemMeta itemMeta = item.getItemMeta();
                //System.out.println("IT'S IN THE FOODLIST");
                if (itemMeta != null) {
                    itemMeta.setLore(buildFoodLore(item.getType()));
                    item.setItemMeta(itemMeta);
                } else {
                    // ItemMeta is null? What?
                    System.out.println("OH GOD WE FUCKED UP BOYS");
                }

            }
        }
    }

    private List<String> buildFoodLore(Material material){
        // Builds the description for the food item, using
        // information from FoodInfo class.
        List<String> info = new ArrayList<>();
        // Restore value
        if (FoodInfo.restoreMap.get(material) >= 0){
            info.add("§aRestores " + FoodInfo.restoreMap.get(material) + " Hunger");
        } else {
            info.add("§cDepletes " + java.lang.Math.abs(FoodInfo.restoreMap.get(material)) + " Hunger");
        }

        // Saturation value
        info.add("§bSaturation: " + FoodInfo.satMap.get(material));
        // Any side effects?
        info.add("\n");
        for (String s : FoodInfo.descMap.get(material)){
            info.add("§e" + s);
        }

        // Build flair
        info.add("\n");
        for (String s : FoodInfo.flairMap.get(material)){
            info.add("§3§o" + s);
        }

        return info;
    }

}
