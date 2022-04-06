package fr.edencraft.randomtp.utils;

import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.lang.Language;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportUtils {

    private static final Language lang = RandomTP.getINSTANCE().getLanguage();

    // private static final HashSet<Material> unsafeBlocks = new HashSet<>();

    /**
     * Teleport the player to a random and safe location in his world.
     * This teleportation is directly managed by the RandomTP configuration file.
     *
     * @param player    The player that will be teleported randomly.
     * @return          The success status of the operation.
     */
    public static boolean randomTeleportPlayer(Player player) {
        if (!ConfigurationUtils.isRegisteredWorld(player.getWorld())) {
            player.sendMessage(lang.getUnregisteredWorld(player.getWorld()));
            return false;
        }

        World world = player.getWorld();
        int x1, z1, x2, z2;
        ConfigurationSection borderSection =
                ConfigurationUtils.getConfigurationSection(
                        "RandomTP.yml", "worlds." + world.getName() + ".border"
                );
        assert borderSection != null;
        x1 = borderSection.getInt("pos1.x");
        z1 = borderSection.getInt("pos1.z");
        x2 = borderSection.getInt("pos2.x");
        z2 = borderSection.getInt("pos2.z");

        Region region = new Region(world, x1, z1, x2, z2);

        Region excludeRegion;
        ConfigurationSection excludeSection =
                ConfigurationUtils.getConfigurationSection(
                        "RandomTP.yml", "worlds." + world.getName() + ".exclude"
                );
        if (excludeSection != null) {
            x1 = excludeSection.getInt("pos1.x");
            z1 = excludeSection.getInt("pos1.z");
            x2 = excludeSection.getInt("pos2.x");
            z2 = excludeSection.getInt("pos2.z");
            excludeRegion = new Region(world, x1, z1, x2, z2);
        } else {
            excludeRegion = null;
        }


        ConfigurationManager configurationManager = RandomTP.getINSTANCE().getConfigurationManager();
        FileConfiguration cfg = configurationManager.getConfigurationFile("RandomTP.yml");
        List<String> unsafeBlocksName = cfg.getStringList("unsafe-blocks");
        List<Material> unsafeBlocksMaterial = new ArrayList<>();

        unsafeBlocksName.forEach(materialName -> unsafeBlocksMaterial.add(Material.valueOf(materialName)));

        RandomSafeLocation safeLocation = new RandomSafeLocation(region, unsafeBlocksMaterial, excludeRegion);
        safeLocation.search();

        if (safeLocation.isFound()) {
            player.sendMessage(lang.getSuccessRTP(safeLocation));
            player.teleport(safeLocation.getRandomSafeLocation());
            return true;
        } else {
            player.sendMessage(lang.getReachedMaxAttempt(safeLocation));
            return false;
        }
    }

}
