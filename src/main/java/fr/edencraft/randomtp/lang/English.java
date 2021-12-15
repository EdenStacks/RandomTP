package fr.edencraft.randomtp.lang;

import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.RandomSafeLocation;
import org.bukkit.World;

import java.util.List;

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

    @Override
    public String getActiveCooldown(long timeLeft) {
        return prefix + new ColoredText(
                "&cYou will be able to random teleport again in &e" + timeLeft / 1000 +
                        " &csecond(s)."
        ).treat();
    }

    @Override
    public String getReloadFiles(List<String> filesName) {
        if (filesName.size() == 1) {
            return prefix + new ColoredText(
                    "&aThe configuration file &7(&f" + filesName.stream().findFirst().get() + "&7) " +
                            "&ahas been reloaded !"
            ).treat();
        } else {
            return prefix + new ColoredText(
                    "&aAll configuration file has been reloaded !"
            ).treat();
        }
    }

    @Override
    public String getUnknownConfigFile(String name) {
        return prefix + new ColoredText(
                "&cThe configuration file &7(&f" + name + "&7) &cdoes'nt exist !"
        ).treat();
    }
}
