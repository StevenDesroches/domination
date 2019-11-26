package com.github.steven.domination.runnables;

import com.github.steven.domination.Domination;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ChanceRunnable extends BukkitRunnable {

    @Override
    public void run() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        if(playerCount >= 3){
            Random rand = new Random();
            int  currentLuck = rand.nextInt(100) + 1;
            if (currentLuck <=0.25){
                Domination.getInstance().createDominationEvent();
            }
        }
    }
}
