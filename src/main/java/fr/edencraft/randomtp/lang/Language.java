package fr.edencraft.randomtp.lang;

import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.utils.RandomSafeLocation;
import org.bukkit.World;

import java.util.List;

public interface Language {

    String prefix = RandomTP.getINSTANCE().getPrefix();

    /**
     * This message is called when a player try to use the /rtp command in
     * a world that is not listed in the RandomTP configuration file.
     *
     * @param world The unregistered world.
     * @return The message in a specific language.
     */
    String getUnregisteredWorld(World world);

    /**
     * This message is called when a random teleportation has been canceled due to the max attempt
     * limit.
     *
     * @param safeLocation The random safe location object used.
     * @return The message in a specific language.
     */
    String getReachedMaxAttempt(RandomSafeLocation safeLocation);

    /**
     * This message is called when a random teleportation is a success.
     *
     * @param safeLocation The random safe location object used.
     * @return The message in a specific language.
     */
    String getSuccessRTP(RandomSafeLocation safeLocation);

    /**
     * This message is called when a player try to do an action with an active cooldown on it.
     *
     * @param timeLeft Time left before next action.
     * @return The message in a specific language.
     */
    String getActiveCooldown(long timeLeft);

    /**
     * This message is called when one or more files has been reloaded.
     *
     * @param filesName The list reloaded files.
     * @return The message in a specific language.
     */
    String getReloadFiles(List<String> filesName);

    /**
     * This message is called when trying to use an unknown configuration file.
     *
     * @param name The name of unknown configuration file.
     * @return The message in a specific language.
     */
    String getUnknownConfigFile(String name);

    /**
     * This message is called each second of a teleportation cooldown.
     *
     * @param timeLeft The time left of the countdown.
     * @return The message in a specific language.
     */
    String getTeleportationCountdownTimeLeft(int timeLeft);

    /**
     * This message is called when a teleportation countdown is cancelled.
     *
     * @return The message in a specific language.
     */
    String getTeleportationCountdownCancelled();

}
