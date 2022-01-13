package me.rezcom.shokuji.events;

import me.rezcom.shokuji.EdibleItem;
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
        ItemStack foodItem = event.getItem();
        // If a food item has been modified
        if (foodItem != null && foodItem.getItemMeta() != null && FoodInfo.foodMap.containsKey(foodItem.getType())){
            // Feed player
            event.setCancelled(true);
            EdibleItem modifiedFood = FoodInfo.foodMap.get(foodItem.getType());
            player.setFoodLevel(Math.max(Math.min(player.getFoodLevel() + modifiedFood.getRestore(), 20),0));
            player.setSaturation(Math.min(player.getSaturation() + modifiedFood.getSaturation(), player.getFoodLevel()));

            // Apply effects
            for (PotionProbList potionProbList : modifiedFood.getEffects()){
                PotionEffect effect = potionProbList.get();
                if (effect != null){
                    applyEffect(effect, player);
                }
            }


        }


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
