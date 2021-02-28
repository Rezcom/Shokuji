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

public class ItemDescHandler extends PacketAdapter {

    ItemDescHandler(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType){
        super(plugin, listenerPriority, packetType);
    }



    @Override
    public void onPacketSending(PacketEvent event){
        PacketContainer packet = event.getPacket();
        // Listen for when a player holds a food item in their hand
        ItemStack item = packet.getItemModifier().read(0);
        setItemDesc(item);

    }
    // Sets a specific item's tags to the appropriate tags.
    public static void setItemDesc(ItemStack item){
        if (item != null && item.getType() != Material.AIR){
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null){
                // System.out.println("Checking for " + item.toString());
                if (FoodInfo.foodMap.containsKey(item.getType())){ // If it's an edible item
                    // System.out.println("Found it: " + item.toString());
                    EdibleItem modifiedFood = FoodInfo.foodMap.get(item.getType());
                    itemMeta.setLore(modifiedFood.getLore());
                    item.setItemMeta(itemMeta);
                } else if (FoodInfo.ingredientLoreMap.containsKey(item.getType())){ // If it's an 'ingredient'
                    itemMeta.setLore(FoodInfo.ingredientLoreMap.get(item.getType()));
                    item.setItemMeta(itemMeta);
                }

            } else {
                System.out.println("[Shokuji] ItemMeta was null in setItemDesc for " + item.toString() + "; could not modify description.");
            }
        }

    }



}
