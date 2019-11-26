package com.github.steven.domination;

import com.github.steven.domination.commands.DominationCommand;
import com.github.steven.domination.datatypes.Cuboid;
import com.github.steven.domination.datatypes.DominationObj;
import com.github.steven.domination.helpers.listHelper;
import com.github.steven.domination.runnables.ChanceRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Domination extends JavaPlugin {//

    private static Domination instance;

    private static List<Cuboid> cuboidList = new ArrayList<>();
    private static UUID currentLeader;
    private static int currentLeaderPoint = 0;
    private DominationObj event;
    private ChanceRunnable chanceRunnable;

    public HashMap<UUID, Integer> dominationPlayersPoint;

    public static Domination getInstance() {
        return instance;
    }

    public DominationObj getEvent() {
        return this.event;
    }

    public static List<Cuboid> getCuboidList() {
        return cuboidList;
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
        this.event = new DominationObj(instance);
        customLoadConfig();

        this.getCommand("domination").setExecutor(new DominationCommand());

    }

    @Override
    public void onDisable() {

    }

    public void customLoadConfig() {
        instance.reloadConfig();
        System.out.println("[domination] config reloaded");
        this.event.setWinCount(getConfig().getInt("winCount"));
        this.event.setRunSync(getConfig().getBoolean("sync"));

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

    public void createDominationEvent() {
        Domination instance = Domination.getInstance();
        Cuboid currentCuboid = listHelper.getRandomCuboid(Domination.getCuboidList());
        DominationObj dominationEvent = instance.getEvent();
        dominationEvent.setCurrentCuboid(currentCuboid);
        dominationEvent.runDominationEvent(instance);
        dominationEvent.createDynmapMarker();
        instance.dominationPlayersPoint = new HashMap<>();
    }

    public void createAutoChanceRunnable() {
        if (this.chanceRunnable == null || this.chanceRunnable.isCancelled()) {
            this.chanceRunnable = new ChanceRunnable();
            this.chanceRunnable.runTaskTimerAsynchronously(instance, 0, 72000);
        }
    }

    public void stopAutoChanceRunnable(){
        if (this.chanceRunnable != null) {
            this.chanceRunnable.cancel();
        }
    }


}