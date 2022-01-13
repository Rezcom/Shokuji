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

import java.util.logging.Level;

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
        if (item == null){
            //Main.logger.log(Level.WARNING, "Tried to set Item Description of a null itemStack");
            return;
        }

        if (item.getType() != Material.AIR){
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null){
                // System.out.println("Checking for " + item.toString());
                if (FoodInfo.foodMap.containsKey(item.getType())){ // If it's an edible item
                    // System.out.println("Found it: " + item.toString());
                    EdibleItem modifiedFood = FoodInfo.foodMap.get(item.getType());
                    itemMeta.lore(modifiedFood.getLore());
                    item.setItemMeta(itemMeta);
                } else if (FoodInfo.ingredientLoreMap.containsKey(item.getType())){ // If it's an 'ingredient'
                    itemMeta.lore(FoodInfo.ingredientLoreMap.get(item.getType()));
                    item.setItemMeta(itemMeta);
                }

            } else {
                Main.logger.log(Level.WARNING, "ItemMeta was null in setItemDesc for " + item + "; could not modify description.");
            }
        }

    }



}
