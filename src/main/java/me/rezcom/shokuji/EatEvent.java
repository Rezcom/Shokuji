package me.rezcom.shokuji;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;


public class EatEvent implements Listener {

    @EventHandler
    void onPlayerEat(FoodLevelChangeEvent event){
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player)event.getEntity();
        System.out.println(player.getSaturation() + " SAT BEFORE");
        ItemStack foodItem = event.getItem();

        // If a food item has been modified
        if (foodItem != null && FoodInfo.allMapsContain(foodItem.getType())){
            // Feed player
            event.setCancelled(true);
            player.setFoodLevel(Math.min(player.getFoodLevel() + FoodInfo.restoreMap.get(foodItem.getType()), 20));
            player.setSaturation(Math.min(player.getSaturation() + FoodInfo.satMap.get(foodItem.getType()), player.getFoodLevel()));

            // Apply effects
            player.addPotionEffects(FoodInfo.effectMap.get(foodItem.getType()));
            /*for (PotionEffect potionEffect : FoodInfo.effectMap.get(foodItem.getType())){
                player.addPotionEffect(potionEffect);
            }*/
        }

        System.out.println(player.getFoodLevel() + " FOOD LEVEL AFTER");
        System.out.println(player.getSaturation() + " SAT AFTER");

    }
}
