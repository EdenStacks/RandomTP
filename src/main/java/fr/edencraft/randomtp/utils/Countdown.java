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

    private BukkitTask bukkitTask;
    private int timeLeft;
    private boolean isCancelled;

    public Countdown(Plugin plugin, int duration, Player player) {
        this.duration = duration;
        this.player = player;
        this.plugin = plugin;

        this.isCancelled = false;
        this.bukkitTask = null;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, plugin);
    }

    /**
     * Call this to start countdown.
     */
    public void start() {
        timeLeft = duration;

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (timeLeft != 0) {
                    eachSecond();
                }

                if (timeLeft == 0) {
                    action();
                    Countdown.this.cancel();
                } else {
                    timeLeft--;
                }
            }
        }.runTaskTimer(plugin, 0, 20L);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasExplicitlyChangedBlock()) return;
        if (event.getPlayer().getName().equalsIgnoreCase(player.getName()) && bukkitTask != null && !isCancelled()) {
            onCancel();
            cancel();
        }
    }

    /**
     * This method is called to cancel the countdown.
     * It usually used in the onEvents() method.
     */
    public void cancel() {
        bukkitTask.cancel();
        this.bukkitTask = null;
        this.isCancelled = true;
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
     * This method return the time remaining to play the action associated to this countdown.
     *
     * @return The time left before the end of the countdown.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * This method represent what to do at the end of the countdown.
     */
    public abstract void action();

    /**
     * This method represent what to each second of the countdown.
     * If getTimeLeft() == 0 this method is replaced by action().
     */
    public abstract void eachSecond();

    /**
     * This method represent what to do when countdown is cancelled.
     */
    public abstract void onCancel();

    /**
     * An util record to send message in the action bar in minecraft instead of the chat.
     */
    public record ActionBar(String message) {

        public void sendToPlayer(Player player) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }

    }
}