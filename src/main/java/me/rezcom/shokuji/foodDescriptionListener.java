package me.rezcom.shokuji;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class foodDescriptionListener extends PacketAdapter {

    foodDescriptionListener(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType){
        super(plugin, listenerPriority, packetType);
    }



    @Override
    public void onPacketSending(PacketEvent event){
        PacketContainer packet = event.getPacket();
        // Listen for when a player holds a food item in their hand
        System.out.println("SET SLOT");
        ItemStack itemStack = packet.getItemModifier().read(0);
        if (itemStack != null && itemStack.getType() != Material.AIR){
            try {
                System.out.println(itemStack.getType());
            } catch (NullPointerException e){
                System.out.println("FUCK YOU");
            }
        }
    }

}
