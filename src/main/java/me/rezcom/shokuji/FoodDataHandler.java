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
        //System.out.println("Size: " + packet.getItemModifier().size());
        //System.out.println("Getval: " + packet.getItemModifier().getValues());
        setItemDesc(item);

    }
    // Sets a specific item's tags to the appropriate tags.
    public static void setItemDesc(ItemStack item){
        if (item != null && item.getType() != Material.AIR && FoodInfo.allMapsContain(item.getType())){
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setLore(FoodInfo.loreMap.get(item.getType()));
                item.setItemMeta(itemMeta);
            } else {
                // ItemMeta is null? What?
                System.out.println("OH GOD WE FUCKED UP BOYS");
            }
        }
    }



}
