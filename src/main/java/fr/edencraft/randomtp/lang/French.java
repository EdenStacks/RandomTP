package fr.edencraft.randomtp.lang;

import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.RandomSafeLocation;
import org.bukkit.World;

import java.util.List;

public class French implements Language {

    @Override
    public String getUnregisteredWorld(World world) {
        return prefix + new ColoredText(
                "&cVous ne pouvez pas vous téléporter aléatoirement dans ce monde. &8(&7" + world.getName() + "&8)"
        ).treat();
    }

    @Override
    public String getReachedMaxAttempt(RandomSafeLocation safeLocation) {
        return prefix + new ColoredText(
                "&6La téléportation aléatoire a été annulé car aucun lieu sûr ou libre n'a été trouvé " +
                        "&8(&7" + safeLocation.getTotalAttempt() + "&8)&6."
        ).treat();
    }

    @Override
    public String getSuccessRTP(RandomSafeLocation safeLocation) {
        return prefix + new ColoredText(
                "&aTéléportation aléatoire effectué &8(&7" + safeLocation.getTotalAttempt() + "&8)&a."
        ).treat();
    }

    @Override
    public String getActiveCooldown(long timeLeft) {
        return prefix + new ColoredText(
                "&cVous pourrez de nouveau vous téléporter aléatoirement dans &e" + timeLeft / 1000 +
                        " &cseconde(s)."
        ).treat();
    }

    @Override
    public String getReloadFiles(List<String> filesName) {
        if (filesName.size() == 1) {
            return prefix + new ColoredText(
                    "&aLe fichier de configuration &7(&f" + filesName.stream().findFirst().get() + "&7) " +
                            "&aa été reload !"
            ).treat();
        } else {
            return prefix + new ColoredText(
                    "&aTout les fichiers de configuration ont été reload !"
            ).treat();
        }
    }

    @Override
    public String getUnknownConfigFile(String name) {
        return prefix + new ColoredText(
                "&cLe fichier de configuration &7(&f" + name + "&7) &cn'existe pas !"
        ).treat();
    }
}
