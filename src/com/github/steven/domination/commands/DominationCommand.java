package com.github.steven.domination.commands;

import com.github.steven.domination.Domination;
import com.github.steven.domination.datatypes.Cuboid;
import com.github.steven.domination.helpers.listHelper;
import com.github.steven.domination.runnables.DominationRunnable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class DominationCommand implements CommandExecutor {
    private static DominationRunnable dominationRunnable;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean commandState = false;

        if (args.length != 0) {
            switch (args[0].toLowerCase()) {
                case "start":
                    commandState = start(commandSender);
                    break;
                case "stop":
                    commandState = stop(commandSender);
                    break;
                case "reload":
                    commandState = reload(commandSender);
                    break;
            }
        }

        return commandState;
    }

    private boolean start(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.domination")) {
            returnVar = true;
            Cuboid currentCuboid = listHelper.getRandomCuboid(Domination.getCuboidList());
            Domination.getInstance().currentCuboid = currentCuboid;

            dominationRunnable = new DominationRunnable();
            if (Domination.getRunSync()) {
                dominationRunnable.runTaskTimer(Domination.getInstance(), 0, 20);
            } else {
                dominationRunnable.runTaskTimerAsynchronously(Domination.getInstance(), 0, 20);
            }
            Domination.getInstance().dominationPlayersPoint = new HashMap<>();

            String world = currentCuboid.getPoint1().getWorld().getName();
            double x[] = {currentCuboid.getPoint1().getX(), currentCuboid.getPoint2().getX()};
            double z[] = {currentCuboid.getPoint1().getZ(), currentCuboid.getPoint2().getZ()};
            Domination.getDynmap().createAreaMarker(world, x, z);
            Bukkit.broadcastMessage("[Domination] start");
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

    private boolean stop(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.domination")) {
            returnVar = true;
            if (dominationRunnable != null) {
                dominationRunnable.cancel();
            }
            if(Domination.getDynmap().getAreaMarker() != null){
                Domination.getDynmap().deleteCurrentDominationMarker();
            }
            Bukkit.broadcastMessage("[Domination] end");
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

    private boolean reload(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.reload")) {
            Domination.getInstance().customLoadConfig();
            returnVar = true;
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

}
