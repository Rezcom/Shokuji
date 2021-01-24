package me.rezcom.shokuji;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

// THIS IS DEPRECATED AND DOES NOT WORK,
// USE FOR REFERENCE ONLY FOR DETECTING
// ENTITY_ACTION
public class AntiSprintPacket extends PacketAdapter {

    AntiSprintPacket(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType){
        super(plugin,listenerPriority,packetType);
    }

    @Override
    public void onPacketReceiving(PacketEvent event){
        PacketContainer packet = event.getPacket();
        StructureModifier<EnumWrappers.PlayerAction> actions = packet.getPlayerActions();
        PotionEffect potionEffect = event.getPlayer().getPotionEffect(PotionEffectType.SPEED);
        if (actions != null && potionEffect != null &&actions.getValues().contains(EnumWrappers.PlayerAction.START_SPRINTING)){
            // Player is sprinting with slow debuff!
            if (potionEffect.getAmplifier() == -2){
                System.out.println("START SPRINTING WITH SLOW");
                event.getPlayer().setVelocity(new Vector(0,0,0));
                event.getPlayer().setSprinting(false);
            }

        }

    }
}
