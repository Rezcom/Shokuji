package me.rezcom.shokuji;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import me.rezcom.shokuji.invhandler.InvClickHandler;
import me.rezcom.shokuji.invhandler.InvCloseHandler;
import me.rezcom.shokuji.invhandler.InvDragHandler;
import me.rezcom.shokuji.invhandler.InvOpenHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger = this.getLogger();
        logger.log(Level.INFO, "Initializing Plugin");

        this.saveDefaultConfig();
        FoodInfo.fileConfig = this.getConfig(); // Pass config to food so it can build descriptions.

        FoodInfo.debugConfig = this.getConfig().getBoolean("debug-config");
        PotionProbList.probDebug = this.getConfig().getBoolean("debug-probability");
        FoodInfo.initialize();

        // Enable the NoSprint Event for Speed Amp = -1
        AntiSprintEvent.enabled = this.getConfig().getBoolean("enable-no-sprint");

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new ItemDescHandler(this, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT));
        //protocolManager.addPacketListener(new AntiSprintPacket(this, ListenerPriority.NORMAL, PacketType.Play.Client.ENTITY_ACTION));


        getServer().getPluginManager().registerEvents(new EatEvent(),this);
        getServer().getPluginManager().registerEvents(new AntiSprintEvent(),this);

        // Inv Handlers
        getServer().getPluginManager().registerEvents(new InvOpenHandler(),this);
        getServer().getPluginManager().registerEvents(new InvDragHandler(),this);
        getServer().getPluginManager().registerEvents(new InvClickHandler(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static void sendDebugMessage(String string, boolean send){
        if (send){
            logger.log(Level.INFO, string);
        }
    }
}
