package me.rezcom.shokuji;

import me.rezcom.shokuji.FoodInfo;
import me.rezcom.shokuji.PotionProbList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;


public class EatEvent implements Listener {

    @EventHandler
    void onPlayerEat(FoodLevelChangeEvent event){
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player)event.getEntity();
        //System.out.println(player.getSaturation() + " SAT BEFORE");
        ItemStack foodItem = event.getItem();

        // If a food item has been modified
        if (foodItem != null && FoodInfo.allMapsContain(foodItem.getType())){
            // Feed player
            event.setCancelled(true);
            player.setFoodLevel(Math.min(player.getFoodLevel() + FoodInfo.restoreMap.get(foodItem.getType()), 20));
            player.setSaturation(Math.min(player.getSaturation() + FoodInfo.satMap.get(foodItem.getType()), player.getFoodLevel()));

            // Apply effects
            for (PotionProbList potionProbList : FoodInfo.effectMap.get(foodItem.getType())){
                PotionEffect effect = potionProbList.get();
                if (effect != null){
                    applyEffect(effect, player);
                }
            }
            /*for (PotionEffect potionEffect : FoodInfo.effectMap.get(foodItem.getType())){
                player.addPotionEffect(potionEffect);
            }*/
        }

        //System.out.println(player.getFoodLevel() + " FOOD LEVEL AFTER");
        //System.out.println(player.getSaturation() + " SAT AFTER");

    }

        // Applies the effect if duration > 0, otherwise attempts to remove the status from the player.
    private void applyEffect(PotionEffect effect, Player player){
        if (effect.getDuration() > 0){
            //APPLY it
            player.addPotionEffect(effect);
        } else {
            // REMOVE it
            if (player.getPotionEffect(effect.getType()) != null){
                player.removePotionEffect(effect.getType());
            }
        }
    }
}
