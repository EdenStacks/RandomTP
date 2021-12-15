package fr.edencraft.randomtp.manager;

import co.aikar.commands.PaperCommandManager;
import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.command.RTP;
import fr.edencraft.randomtp.utils.CommandCompletionUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CommandManager {

    public CommandManager(RandomTP plugin){
        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new RTP());
        commandManager.getCommandCompletions().registerAsyncCompletion(
                "randomtpreload",
                context -> {
                    if (context.getPlayer() != null) {
                        Player player = context.getPlayer();
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1);
                    }
                    return CommandCompletionUtils.getConfigurationFilesName();
                }
        );
    }

}
