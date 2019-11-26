package com.github.steven.domination.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

public class Dynmap {

    private DynmapAPI dynApi;
    private MarkerSet markerset;
    private AreaMarker currentDominationMarker;

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
        this.dynApi = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (this.dynApi == null) {
            Bukkit.getServer().getPluginManager().disablePlugin(p);
        } else {
            this.markerset = this.dynApi.getMarkerAPI().createMarkerSet("dominationMarkerSetId", "dominationMarkerSet", this.dynApi.getMarkerAPI().getMarkerIcons(), false);
        }
    }

    public void createAreaMarker(String world, double[] x, double[] z) {
        this.currentDominationMarker = this.markerset.createAreaMarker("dominationAreaMarkerId", "dominationAreaMarker", true,
                world, x, z, false);
        this.currentDominationMarker.setFillStyle(1, 0x42f4f1);
    }

}
