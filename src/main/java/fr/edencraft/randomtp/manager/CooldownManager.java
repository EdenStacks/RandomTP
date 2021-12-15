package fr.edencraft.randomtp.manager;

import fr.edencraft.randomtp.utils.ConfigurationUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class manage random teleport cooldown to avoid excess.
 */
public class CooldownManager {

    private final HashMap<Player, Long> register;

    /**
     * Call this constructor to init this manager.
     */
    public CooldownManager() {
        this.register = new HashMap<>();
    }

    /**
     * This method check if a player is registered in the cooldown register.
     *
     * @param player The player to check.
     * @return If the player is registered or not.
     */
    public boolean isRegistered(Player player) {
        return register.containsKey(player);
    }

    /**
     * This method add a player to the cooldown register.
     *
     * @param player The player to register.
     */
    public void addToRegister(Player player) {
        register.put(player, System.currentTimeMillis());
    }

    /**
     * This method remove player from the cooldown register.
     *
     * @param player The player to unregister.
     */
    public void removeFromRegister(Player player) {
        if (isRegistered(player)) register.remove(player);
    }

    /**
     * This method update the register, it clear outdated cooldowns. You can call this before a check for
     * example.
     *
     * @return Instance of CooldownManager.
     */
    public CooldownManager update() {
        long cooldownDuration = ConfigurationUtils.getCooldownDuration() * 1000L;
        List<Player> outdated = new ArrayList<>();
        register.forEach((player, time) -> {
            long actualTime = System.currentTimeMillis();
            if ((actualTime - time) >= cooldownDuration) outdated.add(player);
        });
        outdated.forEach(register::remove);
        return this;
    }

    /**
     * This method return the time left in milliseconds before next action.
     *
     * @param player The player to get the time left.
     * @return Time left in milliseconds.
     */
    public long getTimeLeft(Player player) {
        if (!register.containsKey(player)) return 0;
        long cooldownDuration = ConfigurationUtils.getCooldownDuration() * 1000L;
        return (cooldownDuration - (System.currentTimeMillis() - register.get(player)));
    }

}
