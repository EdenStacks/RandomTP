package fr.edencraft.randomtp.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.lang.Language;
import fr.edencraft.randomtp.manager.ConfigurationManager;
import fr.edencraft.randomtp.manager.CooldownManager;
import fr.edencraft.randomtp.utils.ColoredText;
import fr.edencraft.randomtp.utils.CommandCompletionUtils;
import fr.edencraft.randomtp.utils.Countdown;
import fr.edencraft.randomtp.utils.TeleportUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

@CommandAlias("rtp")
public class RTP extends BaseCommand {

    private final static Language LANGUAGE = RandomTP.getINSTANCE().getLanguage();
    private final static CooldownManager COOLDOWN_MANAGER = RandomTP.getINSTANCE().getCooldownManager();
    private final static ConfigurationManager CONFIGURATION_MANAGER = RandomTP.getINSTANCE().getConfigurationManager();

    private static final String basePermission = "randomtp.command";

    private static HashMap<Player, Countdown> playerCountdownHashMap = new HashMap<>();

    @Default
    @CommandPermission(basePermission + ".rtp")
    public static void onRTP(Player player){
        if (!player.hasPermission("randomtp.cooldown.bypass") && COOLDOWN_MANAGER.update().isRegistered(player)) {
            player.sendMessage(LANGUAGE.getActiveCooldown(COOLDOWN_MANAGER.getTimeLeft(player)));
            return;
        }

        if (playerCountdownHashMap.containsKey(player) && !playerCountdownHashMap.get(player).isCancelled()) {
            return;
        }

        int duration = !player.hasPermission("randomtp.countdown.bypass") ? 3 : 0;
        Countdown countdown = new Countdown(RandomTP.getINSTANCE(), duration, player) {
            @Override
            public void action() {
                boolean b = TeleportUtils.randomTeleportPlayer(player);
                if (!b) {
                    RandomTP.getINSTANCE().log(
                            Level.INFO, "Le joueur " + player.getName() + " n'a pas pu être rtp."
                    );
                } else {
                    if (!isCancelled()) {
                        COOLDOWN_MANAGER.addToRegister(player);

                        ActionBar ab = new ActionBar(LANGUAGE.getTeleportationCountdownSuccess());
                        ab.sendToPlayer(player);
                    }
                }
            }

            @Override
            public void eachSecond() {
                ActionBar ab = new ActionBar(LANGUAGE.getTeleportationCountdownTimeLeft(super.getTimeLeft()));
                ab.sendToPlayer(player);
            }

            @Override
            public void onCancel() {
                ActionBar ab = new ActionBar(LANGUAGE.getTeleportationCountdownCancelled());
                ab.sendToPlayer(player);
            }
        };
        countdown.start();
        playerCountdownHashMap.put(player, countdown);
    }

    @Subcommand("reload|rl")
    @Syntax("[fileName]")
    @CommandCompletion("@randomtpreload")
    @CommandPermission(basePermission + ".reload")
    public static void onReload(CommandSender sender, @Optional String fileName){
        if (fileName != null && !fileName.isEmpty()) {
            if (CONFIGURATION_MANAGER.getConfigurationFile(fileName) != null) {
                CONFIGURATION_MANAGER.reloadFile(fileName);
                sender.sendMessage(LANGUAGE.getReloadFiles(Collections.singletonList(fileName)));
            } else {
                sender.sendMessage(LANGUAGE.getUnknownConfigFile(fileName));
            }
        } else {
            CONFIGURATION_MANAGER.reloadFiles();
            sender.sendMessage(LANGUAGE.getReloadFiles(CommandCompletionUtils.getConfigurationFilesName()));
        }
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
