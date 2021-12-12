package fr.edencraft.randomtp.utils;

import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.lang.English;
import fr.edencraft.randomtp.lang.French;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

/**
 * This class regroup all request that can be done with all configuration files
 * used by RandomTP plugin.
 */
public class ConfigurationUtils {

    private static final ConfigurationManager cm = RandomTP.getConfigurationManager();

    /**
     * This method will check if the checked world has been registered as an
     * active world to allow the rtp command.
     *
     * @param world The world to check.
     * @return      Result of the check.
     */
    public static boolean isRegisteredWorld(World world) {
        ConfigurationSection worldsSection = getRandomTPFile().getConfigurationSection("worlds");
        assert worldsSection != null;
        return worldsSection.getKeys(false).contains(world.getName());
    }

    /**
     * This method return a configuration section depending on a file configuration and a path.
     * The return can be null !
     *
     * @param fileConfigurationName The name of the configuration file.
     * @param path The path to the configuration section.
     * @return The requested configuration section found by the given path.
     */
    @Nullable
    public static ConfigurationSection getConfigurationSection(String fileConfigurationName, String path)
    {
        FileConfiguration fg = cm.getConfigurationFile(fileConfigurationName);
        if (fg == null) return null;

        return fg.getConfigurationSection(path);
    }

    /**
     * @return  The file configuration of RandomTP.yml.
     */
    public static FileConfiguration getRandomTPFile() {
        return cm.getConfigurationFile("RandomTP.yml");
    }

    /**
     * @return The language selected in the RandomTP configuration file.
     */
    public static Object getLanguage(){
        String language = getRandomTPFile().getString("language");
        assert language != null;

        return switch (language) {
            case "fr" -> new French();
            case "en" -> new English();
            default -> throw new IllegalStateException("Unexpected value: " + language);
        };
    }

}
