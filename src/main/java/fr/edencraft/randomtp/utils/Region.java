package fr.edencraft.randomtp.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Region {

    private final Location a;
    private final Location b;
    private final World world;

    public Region(World world, double x1, double z1, double x2, double z2) {
        this.a = new Location(world, x1, 0, z1);
        this.b = new Location(world, x2, 0, z2);
        this.world = world;
    }

    public Location getA() {
        return a;
    }

    public Location getB() {
        return b;
    }

    public World getWorld() {
        return world;
    }

    public double getXMax() {
        double xa = getA().getX();
        double xb = getB().getX();

        return Math.max(xa, xb);
    }

    public double getXMin() {
        double xa = getA().getX();
        double xb = getB().getX();

        return Math.min(xa, xb);
    }

    public double getZMax() {
        double za = getA().getZ();
        double zb = getB().getZ();

        return Math.max(za, zb);
    }

    public double getZMin() {
        double za = getA().getZ();
        double zb = getB().getZ();

        return Math.min(za, zb);
    }

    public double getXMaxInAbs() {
        double xa = Math.abs(getA().getX());
        double xb = Math.abs(getB().getX());

        return Math.max(xa, xb);
    }

    public double getXMinInAbs() {
        double xa = Math.abs(getA().getX());
        double xb = Math.abs(getB().getX());

        return Math.min(xa, xb);
    }

    public double getZMaxInAbs() {
        double za = Math.abs(getA().getZ());
        double zb = Math.abs(getB().getZ());

        return Math.max(za, zb);
    }

    public double getZMinInAbs() {
        double za = Math.abs(getA().getZ());
        double zb = Math.abs(getB().getZ());

        return Math.min(za, zb);
    }

    public boolean contain(Location location) {
        return getXMin() <= location.getX() && location.getX() <= getXMax() && getZMin() <= location.getZ()
            && location.getZ() <= getZMax();
    }

    @Override
    public String toString() {
        return "Region{" +
                "a=" + a.toString() +
                ", b=" + b.toString() +
                ", world=" + world.getName() +
                '}';
    }
}
