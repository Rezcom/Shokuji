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
    public static Map<Material, List<PotionProbList>> effectMap = new HashMap<>();

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
        restoreMap.put(Material.APPLE, 4);
        restoreMap.put(Material.BREAD, 5);

        restoreMap.put(Material.ROTTEN_FLESH, -2);
    }

    private static void setDescMap(){
        descMap.put(Material.COOKED_PORKCHOP, Arrays.asList("Gives Instant Health.","5% chance for Large Heal.","Disables sprinting for a short while.","Just like the good old days!"));
        descMap.put(Material.COOKED_BEEF, Arrays.asList("Provides Resistance for a short while.","Considerably high saturation."));
        descMap.put(Material.APPLE, Arrays.asList("Cures several ailments.","(Nausea, Poison,","Blindness, Wither)","2.5% chance gives poison."));
        descMap.put(Material.BREAD, Arrays.asList("Cures Hunger.","Notably high nourishment value."));

        descMap.put(Material.ROTTEN_FLESH, Arrays.asList("Instantly depletes hunger.","80% chance Hunger Debuff"));
    }

    private static void setFlairMap(){
        flairMap.put(Material.COOKED_PORKCHOP, Arrays.asList("A classic delicacy.","Eating this makes you nostalgic."));
        flairMap.put(Material.COOKED_BEEF, Arrays.asList("The quintessential choice of meat lovers.","Nobody can turn down a hearty steak!"));
        flairMap.put(Material.APPLE, Arrays.asList("An apple a day","keeps the status effects away!"));
        flairMap.put(Material.BREAD, Arrays.asList("Bread is quite filling,","so be sure to eat it last!"));

        flairMap.put(Material.ROTTEN_FLESH,Arrays.asList("This is pretty disgusting.","But it serves as a good palette cleanser."));
    }

    private static void setSatMap(){
        satMap.put(Material.COOKED_PORKCHOP, 11.5f);
        satMap.put(Material.COOKED_BEEF,9.8f);
        satMap.put(Material.APPLE, 2.8f);
        satMap.put(Material.BREAD, 9.8f);

        satMap.put(Material.ROTTEN_FLESH, 0.8f);
    }

    private static void setEffectMap(){
        // Cooked PorkChop
        // Instant Health
        PotionProbList porkInstantHP = new PotionProbList(Arrays.asList(
                new PotionProb(new PotionEffect(PotionEffectType.HEAL, 1, 0), 0.95),
                new PotionProb(new PotionEffect(PotionEffectType.HEAL, 1, 2), 0.05)));
        // No Sprint
        PotionProbList porkSpeed = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.SPEED, 260, -1), 1)));
        effectMap.put(Material.COOKED_PORKCHOP, Arrays.asList(porkInstantHP,porkSpeed));

        // Cooked Beef
        // Resistance
        PotionProbList beefResist = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 260,0), 1)));
        effectMap.put(Material.COOKED_BEEF, Collections.singletonList(beefResist));

        // Apple (Nausea, Poison, Wither, Blindness, Hunger)
        PotionProbList appleNausea = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.CONFUSION, -1, 0), 1)));
        PotionProbList applePoison = new PotionProbList(Arrays.asList(
                new PotionProb(new PotionEffect(PotionEffectType.POISON, -1,0), 0.975),
                new PotionProb(new PotionEffect(PotionEffectType.POISON, 260,0), 0.025))); // Small chance of poisoning!
        PotionProbList appleWither = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.WITHER,-1,0),1)));
        PotionProbList appleBlind = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.BLINDNESS,-1,0),1)));
        effectMap.put(Material.APPLE,Arrays.asList(appleNausea,applePoison,appleWither,appleBlind));

        // Bread (Cures Hunger)
        PotionProbList breadHunger = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.HUNGER, -1,1),1)));
        effectMap.put(Material.BREAD,Collections.singletonList(breadHunger));

        // Rotten Flesh
        PotionProbList rotHunger = new PotionProbList(Collections.singletonList(
                new PotionProb(new PotionEffect(PotionEffectType.HUNGER, 600,0),0.8)));
        effectMap.put(Material.ROTTEN_FLESH, Collections.singletonList(rotHunger));
    }

}
