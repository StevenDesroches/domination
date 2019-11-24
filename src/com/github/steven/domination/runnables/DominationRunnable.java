package com.github.steven.domination.runnables;

import com.github.steven.domination.Domination;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class DominationRunnable extends BukkitRunnable {
    @Override
    public void run() {
        Domination currentInstance = Domination.getInstance();
        Map<UUID, Integer> currentPlayersPointMap = currentInstance.dominationPlayersPoint;
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID currentUUID = p.getUniqueId();
            int count = 0;

            if (count > Domination.getCurrentLeaderPoint()) {
                Domination.setCurrentLeader(currentUUID);
                Domination.setCurrentLeaderPoint(count);
            }

            if (Domination.getRunSync()) {
                if(Domination.getCurrentLeader() == null){
                    Domination.setCurrentLeader(currentUUID);
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText("Lead : "
                                + Bukkit.getPlayer(Domination.getCurrentLeader()).getDisplayName()
                                + " avec " + Domination.getCurrentLeaderPoint() + " points"));
            }

            if (currentInstance.currentCuboid.isIn(p)) {
                count = currentPlayersPointMap.getOrDefault(currentUUID, 0);

                if (Domination.getRunSync()) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
                }

                if (count > Domination.getCurrentLeaderPoint()) {
                    Domination.setCurrentLeader(currentUUID);
                    Domination.setCurrentLeaderPoint(count);
                }

                if (count >= currentInstance.winCount) {

                    Bukkit.broadcastMessage(p.getDisplayName() + " gagne la domination !");
                    System.out.println("[domination] deleting marker");
                    Domination.getDynmap().deleteCurrentDominationMarker();
                    System.out.println("[domination] deleting all players points");
                    currentInstance.dominationPlayersPoint = null;
                    System.out.println("[domination] deleting current cuboid");
                    currentInstance.currentCuboid = null;
                    System.out.println("[domination] resetting current lead");
                    Domination.setCurrentLeader(null);
                    Domination.setCurrentLeaderPoint(0);
                    System.out.println("[domination] dominationRunnable ending");
                    this.cancel();

                } else {
                    currentPlayersPointMap.put(currentUUID, count + 1);
                }

            } else {

                currentPlayersPointMap.put(currentUUID, 0);
                if (Domination.getCurrentLeader() == currentUUID) {
                    Domination.setCurrentLeaderPoint(0);
                }
            }
        }

    }
}
