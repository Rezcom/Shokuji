package me.rezcom.shokuji;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class EdibleItem {

    Material material;
    boolean hasName;
    String name;
    int restore;
    float saturation;
    List<PotionProbList> effects;
    List<Component> lore;

    EdibleItem(Material m, boolean hn, String n, int r, float s, List<PotionProbList> eff, List<Component> l){
        material = m;
        hasName = hn;
        name = n;
        restore = r;
        saturation = s;
        effects = eff;
        lore = l;
    }

    public Material getMaterial() { return material; }
    public int getRestore(){ return this.restore; }
    public float getSaturation(){ return this.saturation; }
    public List<PotionProbList> getEffects(){ return this.effects; }
    public List<Component> getLore(){ return this.lore; }


}
