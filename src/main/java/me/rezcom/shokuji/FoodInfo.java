package me.rezcom.shokuji;

import me.rezcom.shokuji.events.InvEventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.logging.Level;


/*
FoodInfo class is responsible for holding all information regarding the items to be changed.

In other words, there are several corresponding maps such that the material is the key, and
the value pertaining to that map is the value.

This class is also responsible for initializing the maps from the config, which
needs to be set as its @fileConfig attribute before calling initializations.

This class contains all the info of foods in its maps, so setting the Lore of each
food is handled here as well.
 */

public class FoodInfo {

    // When set to True, sends debug messages to the console during initializations.
    public static boolean debugConfig = false;

    public static FileConfiguration fileConfig;

    // Stores all data for edible food and their lore
    public static Map<Material,EdibleItem> foodMap = new HashMap<>();


    // Stores the lore to be displayed for "ingredient" items.
    public static Map<Material, List<Component>> ingredientLoreMap = new HashMap<>();

    // Returns true if the material is present in ALL maps corresponding to edible materials
    /*public static boolean edibleMapsContain(ItemStack foodItem){
        if (foodItem.getItemMeta() != null && foodItem.getItemMeta().hasDisplayName()){
            // name exists
            EdibleItem target = new EdibleItem()
        }
    }*/


    // Initialize the maps from the config. Call AFTER setting fileConfig.
    public static void initialize(){
        try {
            initializeEdibleConfig(); // Set the maps for edible items.
        } catch (NullPointerException e){
            Main.logger.log(Level.SEVERE, "Could not initialize 'food' section from config file. Please review it, or delete and restart for a fresh new default one.");
        }

        try {
            initializeIngredientConfig(); // Set the maps for 'ingredient' items.
        } catch (NullPointerException e){
            Main.logger.log(Level.SEVERE, "Could not initialize 'ingredients' section from config file. Please review it, or delete and restart for a fresh new default one.");
        }

        try {
            initializeEventConfig();
        } catch (NullPointerException e){
            Main.logger.log(Level.SEVERE, "Could not initialize inventory event handlers. Ensure that inv-event: true or inv-event: false is listed in the config, or delete and restart for a fresh new default one.");
        }


    }

    private static void initializeEventConfig(){
        InvEventHandler.enabled = fileConfig.getBoolean("inv-event");
    }

    private static void initializeIngredientConfig(){
        Objects.requireNonNull(fileConfig.getConfigurationSection("ingredients")).getKeys(false).forEach(ingredient -> {
            Material curFoodMaterial = Material.getMaterial(ingredient);
            Main.sendDebugMessage("CONFIG: Found ingredient " + ingredient,debugConfig);

            List<String> desc = fileConfig.getStringList("ingredients." + ingredient + ".description");
            Main.sendDebugMessage("CONFIG: Found Description for " + ingredient,debugConfig);

            setIngredientLoreMap(curFoodMaterial,desc);
        });
    }

    // Sets the appropriate maps for all edible items, in this case all items under the "food" key in the config.
    private static void initializeEdibleConfig(){
        Objects.requireNonNull(fileConfig.getConfigurationSection("food")).getKeys(false).forEach(food -> {
            // Get current material
            Material curFoodMaterial = Material.getMaterial(food);
            Main.sendDebugMessage("CONFIG: Found food " + food,debugConfig);

            // Get the name of this new material.
            String name = fileConfig.getString("food." + food + ".name");
            boolean hasName;
            if (name != null){
                hasName = true;
                Main.sendDebugMessage("CONFIG: Name for " + food + " is " + name,debugConfig);
            } else {
                hasName = false;
                Main.sendDebugMessage("CONFIG: Didn't have a name for " + food,debugConfig);
            }


            // Initialize restore and saturation values
            int restore = fileConfig.getInt("food." + food + ".restore");
            float sat = (float)fileConfig.getDouble("food." + food + ".saturation");
            Main.sendDebugMessage("CONFIG: Restore and Saturation Successful",debugConfig);

            // Get desc/flair strings
            List<String> desc = fileConfig.getStringList("food." + food + ".description");
            List<String> flair = fileConfig.getStringList("food." + food + ".flair");
            Main.sendDebugMessage("CONFIG: desc and flair found",debugConfig);

            List<PotionProbList> effects = new ArrayList<>();

            if (fileConfig.getConfigurationSection("food." + food + ".effects") == null){
                // Effects is null
                Main.sendDebugMessage("CONFIG: There was no effects for " + food + ", skipping.",debugConfig);
            } else {
                Objects.requireNonNull(fileConfig.getConfigurationSection("food." + food + ".effects")).getKeys(false).forEach(customProbList -> {
                    // Scope of Probability Lists
                    Main.sendDebugMessage("CONFIG: Working on " + customProbList,debugConfig);

                    List<PotionProb> listOfPotionProbs = new ArrayList<>();
                    Objects.requireNonNull(fileConfig.getConfigurationSection("food." + food + ".effects." + customProbList)).getKeys(false).forEach(customProb -> {
                        // Scope of Probabilities/Effects
                        Main.sendDebugMessage("CONFIG: Working on " + customProb,debugConfig);

                        PotionEffectType type = PotionEffectType.getByName(Objects.requireNonNull(fileConfig.getString("food." + food + ".effects." + customProbList + "." + customProb + ".effect")));
                        Main.sendDebugMessage("CONFIG: Type " + type + " found.",debugConfig);
                        int duration = fileConfig.getInt("food." + food + ".effects." + customProbList + "." + customProb + ".duration");
                        Main.sendDebugMessage("CONFIG: Duration " + duration + " found",debugConfig);
                        int amplifier = fileConfig.getInt("food." + food + ".effects." + customProbList + "." + customProb + ".amplifier");
                        Main.sendDebugMessage("CONFIG: Amplifier " + amplifier + " found",debugConfig);
                        double prob = fileConfig.getDouble("food." + food + ".effects." + customProbList + "." + customProb + ".probability");
                        Main.sendDebugMessage("CONFIG: Probability " + prob + " found",debugConfig);

                        if (type == null){
                            System.out.println("PotionEffectType for " + customProb + " in " + customProbList + " for " + food + " was null. Make sure the name is correct per Spigot naming");
                        } else if (prob == 0) {
                            System.out.println("The probability for " + customProb + " is 0. Are you sure this is correct?");
                        }

                        listOfPotionProbs.add(new PotionProb(new PotionEffect(Objects.requireNonNull(type),duration,amplifier),prob));
                    });
                    Main.sendDebugMessage("CONFIG: Each custom probability was added",debugConfig);
                    PotionProbList newPotionProbList = new PotionProbList(listOfPotionProbs);
                    if (newPotionProbList.isValid()){
                        effects.add(newPotionProbList);
                    } else {
                        System.out.println("CONFIG: " + customProbList + " is not a valid Probability List! It will not proc (Are the sum of probabilities over 1.0?)");
                    }

                });
                Main.sendDebugMessage("CONFIG: List of lists finished",debugConfig);

            }

            Main.sendDebugMessage("CONFIG: Effects initialized.",debugConfig);
            List<Component> newLore = buildEdibleLore(restore,sat,desc,flair);
            Main.sendDebugMessage("CONFIG: Lore initialized.",debugConfig);

            foodMap.put(curFoodMaterial, new EdibleItem(curFoodMaterial,hasName, name,restore,sat,effects,newLore));
            Main.sendDebugMessage("CONFIG: Config for " + name + " complete, and added to data structure.",debugConfig);

        });
    }

    // Builds the description for the edible food item.
    private static List<Component> buildEdibleLore(int restore, float sat, List<String> desc, List<String> flair){

        ArrayList<Component> lore = new ArrayList<>();
        if (restore >= 0){
            lore.add(Component.text("Restores " + restore + " Hunger").color(TextColor.color(0x55FF55)).decoration(TextDecoration.ITALIC,false));
        } else {
            lore.add(Component.text("Depletes " + java.lang.Math.abs(restore) + " Hunger").color(TextColor.color(0xFF5555)).decoration(TextDecoration.ITALIC,false));
        }

        // Saturation and Nourishment Value
        BigDecimal bd = BigDecimal.valueOf(sat / restore);
        bd = bd.round(new MathContext(3));
        lore.add(Component.text("Saturation: " + sat + " ").color(TextColor.color(0x55FFFF)).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("(" + bd.doubleValue() + " Nourishment)").color(TextColor.color(0x5555FF))).decoration(TextDecoration.ITALIC,false));

        // Description of Effects
        if (desc != null){
            lore.add(Component.text(" "));
            for (String s : desc){
                lore.add(Component.text(s).color(TextColor.color(0xFFFF55)).decoration(TextDecoration.ITALIC,false));
            }
        }

        // Flair
        if (flair != null){
            lore.add(Component.text(" "));
            for (String s : flair){
                lore.add(Component.text(s).color(TextColor.color(0x00AAAA)).decoration(TextDecoration.ITALIC,true));
            }
        }
        return lore;
    }

    // Builds the description for ingredient items, and
    // places it in the map.
    private static void setIngredientLoreMap(Material material, List<String> desc){
        //if (edibleLoreMap.containsKey(material)){ return; }

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Ingredient").color(TextColor.color(0xFCE803)).decoration(TextDecoration.ITALIC,false));
        lore.add(Component.text(" "));
        if (desc != null){
            for (String s : desc){
                lore.add(Component.text(s).color(TextColor.color(0x338AF5)).decoration(TextDecoration.ITALIC,true));
            }
        }

        ingredientLoreMap.put(material,lore);

    }
}
