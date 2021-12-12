package fr.edencraft.randomtp.lang;

import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.RandomSafeLocation;
import org.bukkit.World;

public class English implements Language {

    @Override
    public String getUnregisteredWorld(World world) {
        return prefix + new ColoredText(
                "&cYou can't be teleported randomly in this world. &8(&7" + world.getName() + "&8)"
        ).treat();
    }

    @Override
    public String getReachedMaxAttempt(RandomSafeLocation safeLocation) {
        return prefix + new ColoredText(
                "&6The random teleportation has been cancelled because we are unable to found a safe location." +
                        "&8(&7" + safeLocation.getTotalAttempt() + "&8)&6."
        ).treat();
    }

    @Override
    public String getSuccessRTP(RandomSafeLocation safeLocation) {
        return prefix + new ColoredText(
                "&aRandom teleportation done &8(&7" + safeLocation.getTotalAttempt() + "&8)&a."
        ).treat();
    }
}
