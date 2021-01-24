package me.rezcom.shokuji;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AntiSprintEvent implements Listener {

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.SPEED);
        if (potionEffect != null && potionEffect.getAmplifier() == -2 && player.isSprinting()){
            player.setVelocity(new Vector(0, player.getVelocity().getY(), 0));
            System.out.println("ANTISPRINT EVENT");
            //player.setSprinting(false);
        }
    }
}
