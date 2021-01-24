package me.rezcom.shokuji;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new FoodDataHandler(this, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT));
        //protocolManager.addPacketListener(new AntiSprintPacket(this, ListenerPriority.NORMAL, PacketType.Play.Client.ENTITY_ACTION));
        FoodInfo.initialize();

        getServer().getPluginManager().registerEvents(new EatEvent(), this);
        getServer().getPluginManager().registerEvents(new AntiSprintEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
