package fr.edencraft.randomtp.lang;

import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.utils.RandomSafeLocation;
import org.bukkit.World;

public interface Language {

    String prefix = RandomTP.getPrefix();

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

}
