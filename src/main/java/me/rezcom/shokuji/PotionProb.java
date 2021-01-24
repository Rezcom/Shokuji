package me.rezcom.shokuji;

import org.bukkit.potion.PotionEffect;

import java.util.Comparator;

public class PotionProb implements Comparable<PotionProb>{
    PotionEffect potionEffect;
    double probability;
    PotionProb(PotionEffect potionEffect,double probability){
        this.potionEffect = potionEffect;
        this.probability = probability;
    }
    @Override
    public int compareTo(PotionProb other){
        return Comparators.PROBABILITY.compare(this, other);
    }

    public static class Comparators {
        public static Comparator<PotionProb> PROBABILITY = Comparator.comparingDouble(o -> o.probability);
    }
}
