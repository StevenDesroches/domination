package com.github.steven.domination.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

public class Dynmap {

    private static DynmapAPI dynApi;
    private static MarkerSet markerset;
    private static AreaMarker currentDominationMarker;

    public DynmapAPI getDynApi() {
        return dynApi;
    }

    public MarkerSet getMarkerSet() {
        return markerset;
    }

    public AreaMarker getAreaMarker() {
        return currentDominationMarker;
    }

    public void deleteCurrentDominationMarker() {
        currentDominationMarker.deleteMarker();
    }

    public Dynmap(JavaPlugin p) {
        dynApi = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (dynApi == null) {
            Bukkit.getServer().getPluginManager().disablePlugin(p);
        } else {
            markerset = dynApi.getMarkerAPI().createMarkerSet("dominationMarkerSetId", "dominationMarkerSet", dynApi.getMarkerAPI().getMarkerIcons(), false);
        }
    }

    public void createAreaMarker(String world, double[] x, double[] z) {
        AreaMarker marker = markerset.createAreaMarker("dominationAreaMarkerId", "dominationAreaMarker", true,
                world, x, z, false);
        marker.setFillStyle(1, 0x42f4f1);
        currentDominationMarker = marker;
    }

}
