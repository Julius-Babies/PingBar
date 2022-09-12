package com.babiesjulius.pingbar;

import com.babiesjulius.pingbar.commands.ConfigCommand;
import com.babiesjulius.pingbar.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        this.saveDefaultConfig();

        getCommand("pingbar-config").setExecutor(new ConfigCommand());
        getCommand("pingbar-config").setTabCompleter(new ConfigCommand());

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String info(String msg) {
        return ChatColor.WHITE + "[" + ChatColor.GREEN + "PingBar" + ChatColor.WHITE + "] " + msg;
    }
}
