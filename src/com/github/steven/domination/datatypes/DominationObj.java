package com.github.steven.domination.datatypes;

import com.github.steven.domination.Domination;
import com.github.steven.domination.runnables.DominationRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class DominationObj {

    private DominationRunnable dominationRunnable;
    private Dynmap dynmap;
    private UUID currentLeader;
    private int currentLeaderPoint = 0;
    private boolean runSync;
    private int winCount;
    private Cuboid currentCuboid;

    public DominationObj(JavaPlugin instance) {
        this.dynmap = new Dynmap(instance);
    }

    public void runDominationEvent(Domination instance) {
        if (this.dominationRunnable == null || this.dominationRunnable.isCancelled()) {
            this.dominationRunnable = new DominationRunnable();
            if (this.getRunSync()) {
                this.dominationRunnable.runTaskTimer(instance, 0, 20);
            } else {
                this.dominationRunnable.runTaskTimerAsynchronously(instance, 0, 20);
            }
        }
        Bukkit.broadcastMessage("[Domination] start");
    }

    public void cancelDominationEvent() {
        if (this.dominationRunnable != null) {
            this.dominationRunnable.cancel();
            this.dominationRunnable = null;
        }
    }

    public void removeDynmapMarker() {
        if (this.dynmap.getAreaMarker() != null) {
            this.dynmap.deleteCurrentDominationMarker();
        }
    }

    public void createDynmapMarker() {
        String world = currentCuboid.getPoint1().getWorld().getName();
        double x[] = {currentCuboid.getPoint1().getX(), currentCuboid.getPoint2().getX()};
        double z[] = {currentCuboid.getPoint1().getZ(), currentCuboid.getPoint2().getZ()};
        this.dynmap.createAreaMarker(world, x, z);
    }

    public Dynmap getDynmap() {
        return this.dynmap;
    }

    public boolean getRunSync() {
        return this.runSync;
    }

    public UUID getCurrentLeader() {
        return this.currentLeader;
    }

    public int getCurrentLeaderPoint() {
        return this.currentLeaderPoint;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public Cuboid getCurrentCuboid() {
        return this.currentCuboid;
    }

    public void setCurrentLeader(UUID leader) {
        this.currentLeader = leader;
    }

    public void setCurrentLeaderPoint(int point) {
        this.currentLeaderPoint = point;
    }

    public void setRunSync(boolean sync) {
        this.runSync = sync;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void setCurrentCuboid(Cuboid currentCuboid) {
        this.currentCuboid = currentCuboid;
    }

}
