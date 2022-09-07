package org.scarike.minecraft.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;

import java.util.HashMap;

@Data
@Accessors(chain = true)
public class ReplacePos {
    private int x;
    private int z;
    private Material origin;
    private Material material;

    private static final HashMap<String, Material> COLOR_MAP = new HashMap<>();
    public static Material DEFAULT_BLOCK=Material.WHITE_WOOL;

    public static void setMap(String name,Material material){
        COLOR_MAP.put(name,material);
    }

    public static Material loadMaterial(Feature feature) {
        Property layer = feature.getProperty("Layer");
        Material material = COLOR_MAP.get(layer == null ? null : layer.getValue());
        return material == null ? DEFAULT_BLOCK : material;
    }
}
