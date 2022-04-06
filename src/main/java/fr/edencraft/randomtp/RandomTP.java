package fr.edencraft.randomtp;

import fr.edencraft.randomtp.lang.Language;
import fr.edencraft.randomtp.manager.CommandManager;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import fr.edencraft.randomtp.manager.CooldownManager;
import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.ConfigurationUtils;
import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

public final class RandomTP extends JavaPlugin {

    // PLUGIN PREFIX
    private final String pluginPrefix = new ColoredText("&dEden&eTP &f&l» &r").treat();

    // MANAGERS
    private ConfigurationManager configurationManager;
    private CooldownManager cooldownManager;

    // API
    private LandsIntegration landsIntegration;

    // INSTANCE
    private static RandomTP INSTANCE;

    @Override
    public void onEnable() {
        long delay = System.currentTimeMillis();

        INSTANCE = this;

        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.setupFiles();
        updateConfigFiles();

        this.cooldownManager = new CooldownManager();
        new CommandManager(this);

        this.landsIntegration = new LandsIntegration(this);

        log(Level.INFO, "RandomTP enabled. (took " + (System.currentTimeMillis() - delay) + "ms)");
    }

    @Override
    public void onDisable() {
        configurationManager.saveFiles();
    }

    public void log(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + getPlugin(RandomTP.class).getName() + "] " + message);
    }

    public static RandomTP getINSTANCE(){
        return INSTANCE;
    }

    public String getPrefix(){
        return pluginPrefix;
    }

    public ConfigurationManager getConfigurationManager(){
        return configurationManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public Language getLanguage() {
        return (Language) ConfigurationUtils.getLanguage();
    }

    public LandsIntegration getLandsIntegration() {
        return landsIntegration;
    }

    /**
     * @return true if a file as been updated else false.
     */
    private boolean updateConfigFiles() {
        boolean state = false;
        FileConfiguration randomtpCFG = configurationManager.getConfigurationFile("RandomTP.yml");

        if (!randomtpCFG.contains("unsafe-blocks")) {
            randomtpCFG.set("unsafe-blocks", Arrays.asList("OAK_LEAVES", "LAVA", "FIRE", "CACTUS", "WATER", "MAGMA_BLOCK"));
            state = true;
        }

        configurationManager.saveFiles();
        return state;
    }
}
