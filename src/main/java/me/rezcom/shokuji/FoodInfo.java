package me.rezcom.shokuji;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class FoodInfo {

    // Stores all data for food and their lore

    // Modified restoration levels
    public static Map<Material, Integer> restoreMap = new HashMap<>();

    // Short description of a side effect the food gives.
    public static Map<Material, List<String>> descMap = new HashMap<>();

    // Flair at the bottom of description.
    public static Map<Material, List<String>> flairMap = new HashMap<>();

    // Saturation each item gives
    public static Map<Material, Float> satMap = new HashMap<>();

    // Effects that each foodstuff provides
    public static Map<Material, List<PotionEffect>> effectMap = new HashMap<>();

    // Initialize all maps. Call this early!
    public static void initialize(){
        setRestoreMap();
        setFlairMap();
        setDescMap();
        setSatMap();
        setEffectMap();
    }

    // Returns true if the material is present in ALL info maps
    public static boolean allMapsContain(Material key){
        return (restoreMap.containsKey(key) && flairMap.containsKey(key) && descMap.containsKey(key) && satMap.containsKey(key) && effectMap.containsKey(key));
    }

    private static void setRestoreMap(){
        restoreMap.put(Material.COOKED_PORKCHOP, 8);
        restoreMap.put(Material.COOKED_BEEF, 6);

        restoreMap.put(Material.ROTTEN_FLESH, -2);
    }

    private static void setDescMap(){
        descMap.put(Material.COOKED_PORKCHOP, Arrays.asList("Disables sprinting for a short while.","Just like the good old days!"));
        descMap.put(Material.COOKED_BEEF, Arrays.asList("Provides resistance for a short while.","Considerably high saturation."));

        descMap.put(Material.ROTTEN_FLESH, Arrays.asList("Instantly depletes hunger.","Gives Hunger debuff."));
    }

    private static void setFlairMap(){
        flairMap.put(Material.COOKED_PORKCHOP, Arrays.asList("A classic delicacy.","Eating this makes you nostalgic."));
        flairMap.put(Material.COOKED_BEEF, Arrays.asList("The quintessential choice of meat lovers.","Nobody can turn down a hearty steak!"));

        flairMap.put(Material.ROTTEN_FLESH,Arrays.asList("This is pretty disgusting.","But it serves as a good palette cleanser."));
    }

    private static void setSatMap(){
        satMap.put(Material.COOKED_PORKCHOP, 12.8f);
        satMap.put(Material.COOKED_BEEF,12.8f);

        satMap.put(Material.ROTTEN_FLESH, 0.8f);
    }

    private static void setEffectMap(){
        effectMap.put(Material.COOKED_PORKCHOP, Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 260, -2)));
        effectMap.put(Material.COOKED_BEEF, Collections.singletonList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 260,0)));

        effectMap.put(Material.ROTTEN_FLESH, Collections.singletonList(new PotionEffect(PotionEffectType.HUNGER, 600, 1)));
    }

}
