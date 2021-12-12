package fr.edencraft.randomtp.filler;

import fr.edencraft.randomtp.utils.CFGFiller;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class RandomTPFiller implements CFGFiller {

    /* File Configuration preview :
    =======================================
    *language: fr
    *worlds:
    *  our_world_name:
    *    border:
    *      pos1:
    *        x: 1000
    *        z: 1000
    *      pos2:
    *        x: -1000
    *        z: -1000
    *    exclude:
    *      pos1:
    *        x: 200
    *        z: 200
    *      pos2:
    *        x: -200
    *        z: -200
    =======================================
     */

    @Override
    public void fill(FileConfiguration fg) {
        fg.set("language", "fr");
        ConfigurationSection worldsSection = fg.createSection("worlds");
        ConfigurationSection worldSection = worldsSection.createSection("our_world_name");
        ConfigurationSection borderSection = worldSection.createSection("border");
        ConfigurationSection excludeSection = worldSection.createSection("exclude");

        borderSection.set("pos1.x", 1000);
        borderSection.set("pos1.z", 1000);
        borderSection.set("pos2.x", -1000);
        borderSection.set("pos2.z", -1000);

        excludeSection.set("pos1.x", 200);
        excludeSection.set("pos1.z", 200);
        excludeSection.set("pos2.x", -200);
        excludeSection.set("pos2.z", -200);
    }
}
