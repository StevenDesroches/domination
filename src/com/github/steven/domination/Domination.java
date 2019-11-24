package com.github.steven.domination;

import com.github.steven.domination.commands.DominationCommand;
import com.github.steven.domination.datatypes.Cuboid;
import com.github.steven.domination.datatypes.Dynmap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Domination extends JavaPlugin {//

    private static Domination instance;
    private static Dynmap dynmap;
    private static List<Cuboid> cuboidList = new ArrayList<>();
    private static UUID currentLeader;
    private static int currentLeaderPoint = 0;
    private static boolean runSync;

    public int winCount;
    public Cuboid currentCuboid;
    public HashMap<UUID, Integer> dominationPlayersPoint;

    public static Domination getInstance() {
        return instance;
    }

    public static Dynmap getDynmap() {
        return dynmap;
    }

    public static List<Cuboid> getCuboidList() {
        return cuboidList;
    }

    public static boolean getRunSync() {
        return runSync;
    }

    public static UUID getCurrentLeader() {
        return currentLeader;
    }

    public static int getCurrentLeaderPoint() {
        return currentLeaderPoint;
    }

    public static void setCurrentLeader(UUID leader) {
        currentLeader = leader;
    }

    public static void setCurrentLeaderPoint(int point) {
        currentLeaderPoint = point;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        customLoadConfig();

        this.getCommand("domination").setExecutor(new DominationCommand());

        dynmap = new Dynmap(instance);
    }

    @Override
    public void onDisable() {

    }

    public void customLoadConfig() {
        instance.reloadConfig();
        System.out.println("[domination] config reloaded");
        winCount = getConfig().getInt("winCount");
        runSync = getConfig().getBoolean("sync");
        for (String key : getConfig().getConfigurationSection("locations").getKeys(false)) {
            String world = getConfig().getString("locations." + key + ".world");
            double xMin = getConfig().getDouble("locations." + key + ".xMin");
            double yMin = getConfig().getDouble("locations." + key + ".yMin");
            double zMin = getConfig().getDouble("locations." + key + ".zMin");
            double xMax = getConfig().getDouble("locations." + key + ".xMax");
            double yMax = getConfig().getDouble("locations." + key + ".yMax");
            double zMax = getConfig().getDouble("locations." + key + ".zMax");

            Location loc1 = new Location(Bukkit.getWorld(world), xMin, yMin, zMin);
            Location loc2 = new Location(Bukkit.getWorld(world), xMax, yMax, zMax);

            cuboidList.add(new Cuboid(loc1, loc2));
        }
    }


}