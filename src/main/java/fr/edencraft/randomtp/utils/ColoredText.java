package fr.edencraft.randomtp.utils;

import org.bukkit.ChatColor;

public class ColoredText {

    private final String coloredText;

    public ColoredText(String coloredText) {
        this.coloredText = coloredText;
    }

    public String treat() {
        return ChatColor.translateAlternateColorCodes('&', this.coloredText);
    }

}
