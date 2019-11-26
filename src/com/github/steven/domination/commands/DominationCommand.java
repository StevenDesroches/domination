package com.github.steven.domination.commands;

import com.github.steven.domination.Domination;
import com.github.steven.domination.datatypes.DominationObj;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DominationCommand implements CommandExecutor {

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
                case "auto":
                    commandState = auto(commandSender);
                    break;
                case "autoStop":
                    commandState = autoStop(commandSender);
                    break;
            }
        }

        return commandState;
    }

    private boolean start(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.domination")) {
            returnVar = true;
            Domination.getInstance().createDominationEvent();
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

    private boolean stop(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.domination")) {
            returnVar = true;
            Domination instance = Domination.getInstance();
            DominationObj dominationEvent = instance.getEvent();
            dominationEvent.cancelDominationEvent();
            dominationEvent.removeDynmapMarker();
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

    private boolean auto(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.auto")) {
            Domination.getInstance().createAutoChanceRunnable();
            returnVar = true;
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

    private boolean autoStop(CommandSender commandSender) {
        boolean returnVar = false;
        if (commandSender.hasPermission("domination.commands.auto.stop")) {
            Domination.getInstance().stopAutoChanceRunnable();
            returnVar = true;
        } else {
            commandSender.sendMessage("Vous n'avez pas la permission d'utiliser cette commande");
        }
        return returnVar;
    }

}
