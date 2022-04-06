package fr.edencraft.randomtp.utils;

import fr.edencraft.randomtp.RandomTP;
import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;
import java.util.Random;

public class RandomSafeLocation {

    /**
     * The max attempt to find a safe location.
     */
    private final int maxAttempt = 100;
    private final List<Material> unsafeBlocks;
    private final Region region;
    private Location randomSafeLocation = null;
    private int totalAttempt = 0;
    private boolean found;
    private Region excludeRegion;

    /**
     * Construct RandomSafeLocation using a region and a list of unsafe blocks.
     *
     * @param region       The region where search a random and safe location.
     * @param unsafeBlocks The list of unsafe blocks where we can't random teleport.
     */
    public RandomSafeLocation(Region region, List<Material> unsafeBlocks) {
        this.found = false;
        this.unsafeBlocks = unsafeBlocks;
        this.region = region;
        this.excludeRegion = null;
    }

    /**
     * Construct RandomSafeLocation using a region, a list of unsafe blocks and an exclude region.
     *
     * @param region        The region where search a random and safe location.
     * @param unsafeBlocks  The list of unsafe blocks where we can't random teleport.
     * @param excludeRegion The region where we can't random teleport.
     */
    public RandomSafeLocation(Region region, List<Material> unsafeBlocks, Region excludeRegion) {
        this(region, unsafeBlocks);
        this.excludeRegion = excludeRegion;
    }

    /**
     * This method generate a location according with region and the exclude region.
     *
     * @return A location inside region and outside of exclude region.
     */
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

            loc = new Location(region.getWorld(), v1, 0, v2);

            if (excludeRegion != null && excludeRegion.contain(loc)) loc = null;
        }

        // Déterminer le y de loc (pour le moment 0) en fonction du monde /!\ au NETHER
        x = loc.getBlockX();
        y = loc.getWorld().getHighestBlockYAt(loc);
        z = loc.getBlockZ();

        return new Location(region.getWorld(), x, y, z);
    }

    /**
     * This method try to find a location safe inside the region and outside of the exclude region.
     * If no location is found due to max attempt reached, the result of isFound() will be false.
     */
    public void search() {
        Location loc = generateLocation();
        int attempt = 1;

        while ((isClaimed(loc) || unsafeBlocks.contains(loc.getWorld().getBlockAt(loc).getType()))
                && !(attempt == maxAttempt + 1)) {
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

        totalAttempt = attempt;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }

    public Location getRandomSafeLocation() {
        return randomSafeLocation;
    }

    public int getTotalAttempt() {
        return totalAttempt;
    }

    public boolean isFound() {
        return found;
    }

    public List<Material> getUnsafeBlocks() {
        return this.unsafeBlocks;
    }

    public Region getRegion() {
        return region;
    }

    public Region getExcludeRegion() {
        return excludeRegion;
    }

    /**
     * This method check if a location is in a claimed area.
     * If "Lands" isn't installed, it always return false.
     *
     * @param location The location to check.
     * @return If the location is in a claimed area or not.
     */
    private boolean isClaimed(Location location) {
        LandsIntegration landsIntegration = RandomTP.getINSTANCE().getLandsIntegration();
        if (landsIntegration == null) return false;

        return landsIntegration.isClaimed(location);
    }

    @Override
    public String toString() {
        return "RandomSafeLocation{" +
                "maxAttempt=" + maxAttempt +
                ", randomSafeLocation=" + randomSafeLocation.toString() +
                ", lastAttempt=" + totalAttempt +
                ", found=" + found +
                ", unsafeBlocks=" + unsafeBlocks +
                ", region=" + region.toString() +
                ", excludeRegion=" + excludeRegion.toString() +
                '}';
    }
}
