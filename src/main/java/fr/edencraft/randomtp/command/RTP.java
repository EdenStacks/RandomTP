package fr.edencraft.randomtp.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.TeleportUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@CommandAlias("rtp")
public class RTP extends BaseCommand {

    private final static RandomTP plugin = RandomTP.getINSTANCE();
    private final static ConfigurationManager configurationManager = RandomTP.getConfigurationManager();

    private static final String basePermission = "randomtp.command";

    @Default
    @CommandPermission(basePermission + ".rtp")
    public static void onRTP(Player player){
        boolean b = TeleportUtils.randomTeleportPlayer(player);
        if (!b) RandomTP.log(Level.INFO, "Le joueur " + player.getName() + " n'a pas pu être rtp.");
    }

    @Subcommand("about")
    @CommandPermission(basePermission + ".about")
    public static void onAbout(CommandSender sender) {
        StringBuilder cmdMessage = new StringBuilder();
        cmdMessage.append("\n");
        cmdMessage.append("╔════════════════╗\n");
        cmdMessage.append("║    RandomTP    ║\n");
        cmdMessage.append("╟────────────────╢\n");
        cmdMessage.append("║ Version: 1.0   ║\n");
        cmdMessage.append("║                ║\n");
        cmdMessage.append("║ Made with &4♥&r    ║\n");
        cmdMessage.append("║ &rby NayeOne.    ║\n");
        cmdMessage.append("╚════════════════╝\n");

        StringBuilder playerMessage = new StringBuilder();
        playerMessage.append("&aRandomTP\n");
        playerMessage.append("&fVersion: &e1.0\n");
        playerMessage.append("&fBy: &eNayeOne\n");

        if (sender instanceof Player){
            sender.sendMessage(new ColoredText(playerMessage.toString()).treat());
        } else {
            assert sender != null;
            sender.sendMessage(new ColoredText(cmdMessage.toString()).treat());
        }
    }

}
