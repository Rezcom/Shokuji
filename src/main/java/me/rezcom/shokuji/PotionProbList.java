package me.rezcom.shokuji;

import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotionProbList {

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
        for (PotionProb potionProb : probList){
            if (result <= (potionProb.probability + base)){
                return potionProb.potionEffect;
            }
            base += potionProb.probability;

        }
        return null;
    }




}
