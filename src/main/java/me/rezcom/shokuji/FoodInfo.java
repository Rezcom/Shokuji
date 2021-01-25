package me.rezcom.shokuji;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class FoodInfo {

    public static boolean debugConfig = false;

    public static FileConfiguration fileConfig;

    // Stores all data for food and their lore

    // Modified restoration levels
    public static Map<Material, Integer> restoreMap = new HashMap<>();

    // Saturation each item gives
    public static Map<Material, Float> satMap = new HashMap<>();

    // Effects that each foodstuff provides
    public static Map<Material, List<PotionProbList>> effectMap = new HashMap<>();

    public static Map<Material, List<String>> loreMap = new HashMap<>();

    // Initialize all maps. Call this early!


    // Returns true if the material is present in ALL info maps
    public static boolean allMapsContain(Material key){
        return (restoreMap.containsKey(key) && loreMap.containsKey(key) && satMap.containsKey(key) && effectMap.containsKey(key));
    }

    // Initialize the maps from the config. Call AFTER setting fileConfig.
    public static void initialize(){
        try {
            Objects.requireNonNull(fileConfig.getConfigurationSection("food")).getKeys(false).forEach(food -> {
                // Get current material
                Material curFoodMaterial = Material.getMaterial(food);
                Main.sendDebugMessage("[Shokuji] CONFIG: Found food " + food,debugConfig);
                // Initialize numeric values
                int restore = fileConfig.getInt("food." + food + ".restore");
                restoreMap.put(curFoodMaterial,restore);
                float sat = (float)fileConfig.getDouble("food." + food + ".saturation");
                satMap.put(curFoodMaterial, sat);
                Main.sendDebugMessage("[Shokuji] CONFIG: Restore and Saturation Successful",debugConfig);

                // Get desc/flair strings
                List<String> desc = fileConfig.getStringList("food." + food + ".description");
                List<String> flair = fileConfig.getStringList("food." + food + ".flair");
                Main.sendDebugMessage("[Shokuji] CONFIG: desc and flair found",debugConfig);

                // Get list of probLists for effectMap
                List<PotionProbList> listOfProbLists = new ArrayList<>();
                Objects.requireNonNull(fileConfig.getConfigurationSection("food." + food + ".effects")).getKeys(false).forEach(customProbList -> {
                    // Scope of Probability Lists
                    Main.sendDebugMessage("[Shokuji] CONFIG: Working on " + customProbList,debugConfig);

                    List<PotionProb> listOfPotionProbs = new ArrayList<>();
                    Objects.requireNonNull(fileConfig.getConfigurationSection("food." + food + ".effects." + customProbList)).getKeys(false).forEach(customProb -> {
                        // Scope of Probabilities/Effects
                        Main.sendDebugMessage("[Shokuji] CONFIG: Working on " + customProb,debugConfig);

                        PotionEffectType type = PotionEffectType.getByName(Objects.requireNonNull(fileConfig.getString("food." + food + ".effects." + customProbList + "." + customProb + ".effect")));
                        Main.sendDebugMessage("[Shokuji] CONFIG: Type " + type + " found.",debugConfig);
                        int duration = fileConfig.getInt("food." + food + ".effects." + customProbList + "." + customProb + ".duration");
                        Main.sendDebugMessage("[Shokuji] CONFIG: Duration " + duration + " found",debugConfig);
                        int amplifier = fileConfig.getInt("food." + food + ".effects." + customProbList + "." + customProb + ".amplifier");
                        Main.sendDebugMessage("[Shokuji] CONFIG: Amplifier " + amplifier + " found",debugConfig);
                        double prob = fileConfig.getDouble("food." + food + ".effects." + customProbList + "." + customProb + ".probability");
                        Main.sendDebugMessage("[Shokuji] CONFIG: Probability " + prob + " found",debugConfig);

                        if (type == null){
                            System.out.println("[Shokuji] PotionEffectType for " + customProb + " in " + customProbList + " for " + food + " was null. Make sure the name is correct per Spigot naming");
                        } else if (prob == 0) {
                            System.out.println("[Shokuji] The probability for " + customProb + " is 0. Are you sure this is correct?");
                        }

                        listOfPotionProbs.add(new PotionProb(new PotionEffect(Objects.requireNonNull(type),duration,amplifier),prob));
                    });
                    Main.sendDebugMessage("[Shokuji] CONFIG: Each custom probability was added",debugConfig);
                    listOfProbLists.add(new PotionProbList(listOfPotionProbs));
                });
                Main.sendDebugMessage("[Shokuji] CONFIG: List of lists finished",debugConfig);
                effectMap.put(curFoodMaterial,listOfProbLists);
                Main.sendDebugMessage("[Shokuji] CONFIG: EffectMap initialized.",debugConfig);
                setLoreMap(curFoodMaterial,restore,sat,desc,flair);
                Main.sendDebugMessage("[Shokuji] CONFIG: LoreMap initialized.",debugConfig);
            });
        } catch (NullPointerException e){
            System.out.println("[Shokuji] Couldn't load config. Path food returned null pointer.");
        }
    }

    private static void setLoreMap(Material material, int restore, float sat, List<String> desc, List<String> flair){
        // Builds the description for the food item, and
        // places it in the map.
        List<String> lore = new ArrayList<>();
        if (restore >= 0){
            lore.add("§aRestores " + restore + " Hunger");
        } else {
            lore.add("§cDepletes " + java.lang.Math.abs(restore) + " Hunger");
        }

        // Saturaiton and Nourishment Value
        BigDecimal bd = BigDecimal.valueOf(sat / restore);
        bd = bd.round(new MathContext(3));
        lore.add("§bSaturation: " + sat + " §9(" + bd.doubleValue() + " Nourishment)");

        // Description of Effects
        lore.add("\n");
        for (String s : desc){
            lore.add("§e" + s);
        }

        // Flair
        lore.add("\n");
        for (String s : flair){
            lore.add("§3§o" + s);
        }

        loreMap.put(material, lore);
    }


}
