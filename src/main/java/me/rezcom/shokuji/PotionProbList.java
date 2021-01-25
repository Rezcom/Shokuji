package me.rezcom.shokuji;

import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotionProbList {

    public static boolean probDebug = false;

    // A list of potions and their probabilities.
    List<PotionProb> probList = new ArrayList<>();

    // Constructor
    public PotionProbList(List<PotionProb> consProbList){
        probList.addAll(consProbList);
    }

    // Add all potion probabilities to the list.
    public void addAll(List<PotionProb> addList){
        probList.addAll(addList);
    }

    // Verifies that this potion probability list is valid.
    public boolean isValid(){
        double sum = 0;
        for (PotionProb potionProb : probList){
            sum += potionProb.probability;
        }
        return sum <= 1;
    }

    // Gets a potion effect based on the probabilities given in the list.
    // If the list isn't valid, returns null instead.
    // If no effects proc, returns null.

    public PotionEffect get(){
        if (!isValid()){return null;}
        double base = 0;
        probList.sort(PotionProb.Comparators.PROBABILITY); // Sort the list by probability
        Random rand = new Random();
        double result = rand.nextDouble();
        Main.sendDebugMessage("[Shokuji] Probability Roll RESULT: " + result,probDebug);
        for (PotionProb potionProb : probList){
            Main.sendDebugMessage("[Shokuji] RESULT TO GO UNDER: " + potionProb.probability + base,probDebug);
            if (result <= (potionProb.probability + base)){
                Main.sendDebugMessage("[Shokuji] ROLL SUCCESS",probDebug);
                return potionProb.potionEffect;
            }
            Main.sendDebugMessage("[Shokuji] ROLL FAILURE",probDebug);
            base += potionProb.probability;

        }
        return null;
    }




}
