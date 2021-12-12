package fr.edencraft.randomtp;

import fr.edencraft.randomtp.lang.Language;
import fr.edencraft.randomtp.manager.CommandManager;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.ConfigurationUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class RandomTP extends JavaPlugin {

    // PLUGIN PREFIX
    private static final String pluginPrefix = new ColoredText("&dEden&eTP &f&l» &r").treat();

    // MANAGERS
    private static ConfigurationManager configurationManager;

    // INSTANCE
    private static RandomTP INSTANCE;

    @Override
    public void onEnable() {
        long delay = System.currentTimeMillis();

        INSTANCE = this;

        configurationManager = new ConfigurationManager(this);
        configurationManager.setupFiles();

        new CommandManager(this);

        log(Level.INFO, "RandomTP enabled. (took " + (System.currentTimeMillis() - delay) + "ms)");
    }

    @Override
    public void onDisable() {
        configurationManager.saveFiles();
    }

    public static void log(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + getPlugin(RandomTP.class).getName() + "] " + message);
    }

    public static RandomTP getINSTANCE(){
        return INSTANCE;
    }

    public static String getPrefix(){
        return pluginPrefix;
    }

    public static ConfigurationManager getConfigurationManager(){
        return configurationManager;
    }

    public static Language getLanguage() {
        return (Language) ConfigurationUtils.getLanguage();
    }
}
