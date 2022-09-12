package com.babiesjulius.pingbar.commands;

import com.babiesjulius.pingbar.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.babiesjulius.pingbar.Main.getInstance;
import static com.babiesjulius.pingbar.Main.info;

public class ConfigCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        Strings strings = new Strings(player.getLocale());
        try {
            switch (args[0]) {
                case "show-time" -> {
                    switch (args[1]) {
                        case "true" -> {
                            getInstance().getConfig().set(player.getUniqueId() + ".show_time", true);
                            getInstance().saveConfig();
                            player.sendMessage(info(String.format(strings.property_set_to(), args[0], args[1])));
                        }
                        case "false" -> {
                            getInstance().getConfig().set(player.getUniqueId() + ".show_time", false);
                            getInstance().saveConfig();
                            player.sendMessage(info(String.format(strings.property_set_to(), args[0], args[1])));
                        }
                        default -> sender.sendMessage("Falscher Befehl");
                    }
                }
                case "show-ping" -> {
                    switch (args[1]) {
                        case "true" -> {
                            getInstance().getConfig().set(player.getUniqueId() + ".show_ping", true);
                            getInstance().saveConfig();
                            player.sendMessage(info(String.format(strings.property_set_to(), args[0], args[1])));
                        }
                        case "false" -> {
                            getInstance().getConfig().set(player.getUniqueId() + ".show_ping", false);
                            getInstance().saveConfig();
                            player.sendMessage(info(String.format(strings.property_set_to(), args[0], args[1])));
                        }
                        default -> sender.sendMessage("Falscher Befehl");
                    }
                }
                default -> sender.sendMessage("Falscher Befehl");
            }
        } catch (IndexOutOfBoundsException e) {
            sender.sendMessage("Falscher Befehl");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            if ("show-ping".startsWith(args[0])) arguments.add("show-ping");
            if ("show-time".startsWith(args[0])) arguments.add("show-time");
        }
        if (args.length == 2 && (args[0].equals("show-ping") || args[0].equals("show-time"))) {
            if ("true".startsWith(args[1])) arguments.add("true");
            if ("false".startsWith(args[1])) arguments.add("false");
        }
        return arguments;
    }
}
