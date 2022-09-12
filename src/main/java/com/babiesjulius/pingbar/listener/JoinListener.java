package com.babiesjulius.pingbar.listener;

import com.babiesjulius.pingbar.Strings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.babiesjulius.pingbar.Main.getInstance;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (getInstance().getConfig().get(uuid + ".show_time") == null) getInstance().getConfig().set(uuid + ".show_time", true);
        if (getInstance().getConfig().get(uuid + ".show_ping") == null) getInstance().getConfig().set(uuid + ".show_ping", true);
        getInstance().saveConfig();

        Strings strings = new Strings(player.getLocale());

        new Thread(() -> {
            try {
                player.setPlayerListFooter(ChatColor.RED + strings.please_wait() + "...");
                player.setPlayerListHeader(ChatColor.RED + strings.please_wait() + "...");
                Thread.sleep(500);
                player.setPlayerListHeaderFooter("", "");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean ping_calculated = false;
            while (Bukkit.getOnlinePlayers().contains(player)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                if (getInstance().getConfig().getBoolean(uuid + ".show_time")) player.setPlayerListHeader(ChatColor.GREEN + "Es ist " + ChatColor.GOLD + formatter.format(LocalDateTime.now()) + "\n");
                if (getInstance().getConfig().getBoolean(uuid + ".show_ping")) {
                    if (player.getPing() == 0 && !ping_calculated) {
                        player.setPlayerListFooter(ChatColor.GREEN + "\n" + ChatColor.GOLD + strings.calculating_ping());
                    } else {
                        ping_calculated = true;
                        player.setPlayerListFooter(ChatColor.GREEN + "\nPing: " + ChatColor.GOLD + player.getPing() + "ms");
                    }
                }
                try {
                    Thread.sleep(getInstance().getConfig().getInt("refresh_interval"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
