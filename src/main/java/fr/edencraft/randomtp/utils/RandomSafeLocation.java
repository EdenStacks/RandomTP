package fr.edencraft.randomtp.utils;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;
import java.util.Random;

public class RandomSafeLocation {

    private final int maxAttempt = 100;

    private Location randomSafeLocation = null;
    private int lastAttempt = 0;

    private boolean found;
    private final List<Material> unsafeBlocks;
    private final Region region;
    private final Region excludeRegion;

    public RandomSafeLocation(Region region, List<Material> unsafeBlocks, Region excludeRegion) {
        this.found = false;
        this.unsafeBlocks = unsafeBlocks;
        this.region = region;
        this.excludeRegion = excludeRegion;
    }

    private Location generateLocation() {
        Random random = new Random();

        int x, y, z, v1, v2;
        boolean b;
        Location loc = null;

        while (loc == null) {
            v1 = random.nextInt((int) region.getXMaxInAbs());
            b = random.nextBoolean();
            if (b) v1 *= -1;

            v2 = random.nextInt((int) region.getZMaxInAbs());
            b = random.nextBoolean();
            if (b) v2 *= -1;

            loc = new Location(region.getWorld(), v1,0, v2);

            if (excludeRegion != null && excludeRegion.contain(loc)) loc = null;
        }

        // Déterminer le y de loc (pour le moment 0) en fonction du monde /!\ au NETHER
        x = loc.getBlockX();
        y = loc.getWorld().getHighestBlockYAt(loc);
        z = loc.getBlockZ();

        return new Location(region.getWorld(), x, y, z);
    }

    public void search() {
        Location loc = generateLocation();
        int attempt = 1;

        while (unsafeBlocks.contains(loc.getWorld().getBlockAt(loc).getType()) && !(attempt == maxAttempt + 1)) {
            loc = generateLocation();
            attempt++;
        }

        if (attempt != maxAttempt + 1 || !unsafeBlocks.contains(loc.getWorld().getBlockAt(loc).getType())) {
            loc.setX(loc.getBlockX() + 0.5);
            loc.setY(loc.getBlockY() + 1);
            loc.setZ(loc.getBlockZ() + 0.5);

            randomSafeLocation = loc;
            found = true;
        }

        lastAttempt = attempt;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }

    public Location getRandomSafeLocation() {
        return randomSafeLocation;
    }

    public int getLastAttempt() {
        return lastAttempt;
    }

    public boolean isFound() {
        return found;
    }

    public List<Material> getUnsafeBlocks() {
        return unsafeBlocks;
    }

    public Region getRegion() {
        return region;
    }

    public Region getExcludeRegion() {
        return excludeRegion;
    }

    @Override
    public String toString() {
        return "RandomSafeLocation{" +
                "maxAttempt=" + maxAttempt +
                ", randomSafeLocation=" + randomSafeLocation.toString() +
                ", lastAttempt=" + lastAttempt +
                ", found=" + found +
                ", unsafeBlocks=" + unsafeBlocks +
                ", region=" + region.toString() +
                ", excludeRegion=" + excludeRegion.toString() +
                '}';
    }
}
