package fr.edencraft.randomtp.manager;

import co.aikar.commands.PaperCommandManager;
import fr.edencraft.randomtp.RandomTP;
import fr.edencraft.randomtp.command.RTP;

public class CommandManager {

    public CommandManager(RandomTP plugin){
        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new RTP());
    }

}
