package fr.edencraft.randomtp.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Countdown implements Listener {

    private final int duration;
    private final Player player;
    private final Plugin plugin;

    private BukkitTask bukkitTask = null;
    private int timeLeft;
    private boolean isCancelled = false;

    public Countdown(Plugin plugin, int duration, Player player) {
        this.duration = duration;
        this.player = player;
        this.plugin = plugin;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, plugin);
    }

    /**
     * Call this to start countdown.
     */
    public void start() {
        if (duration == 0) action();
        else {
            timeLeft = duration;

            bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {

                    if (timeLeft != 0) {
                        ActionBar ab = new ActionBar(new ColoredText(
                                "&7Téléportation dans &e" + timeLeft + " &7seconde(s)."
                        ).treat());
                        ab.sendToPlayer(player);
                    }

                    if (timeLeft == 0) {
                        action();
                        this.cancel();
                    } else {
                        timeLeft--;
                    }
                }
            }.runTaskTimer(plugin, 0, 20L);
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasExplicitlyChangedBlock()) return;
        if (event.getPlayer().getName().equalsIgnoreCase(player.getName()) && bukkitTask != null) {
            ActionBar ab = new ActionBar(new ColoredText(
                    "&7Téléportation annulée !"
            ).treat());
            ab.sendToPlayer(player);
            bukkitTask.cancel();
            bukkitTask = null;
            isCancelled = true;
        }
    }

    /**
     * The countdown is cancelled by default only when a player move.
     *
     * @return if the countdown has been cancelled or not.
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * This method represent what to do at the end of the countdown.
     */
    public abstract void action();

    /**
     * An util record to send message in the action bar in minecraft instead of the chat.
     */
    public record ActionBar(String message) {

        public void sendToPlayer(Player player) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }

    }
}