package de.Fabi.mlgrush.maps;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.LocationType;
import org.bukkit.Location;

public class RegionManager {

    public boolean isInRegion(final Location location) {
        // X
        Double maxX = (this.getPos1().getX() > this.getPos2().getX() ? this.getPos1().getX() : this.getPos2().getX());
        Double minX = (this.getPos1().getX() < this.getPos2().getX() ? this.getPos1().getX() : this.getPos2().getX());

        // Y
        Double maxY = (this.getPos1().getY() > this.getPos2().getY() ? this.getPos1().getY() : this.getPos2().getY());
        Double minY = (this.getPos1().getY() < this.getPos2().getY() ? this.getPos1().getY() : this.getPos2().getY());

        // Z
        Double maxZ = (this.getPos1().getZ() > this.getPos2().getZ() ? this.getPos1().getZ() : this.getPos2().getZ());
        Double minZ = (this.getPos1().getZ() < this.getPos2().getZ() ? this.getPos1().getZ() : this.getPos2().getZ());

        if(location.getX() <= maxX && location.getX() >= minX) {
            if(location.getY() <= maxY && location.getY() >= minY) {
                return location.getZ() <= maxZ && location.getZ() >= minZ;
            }
        }
        return false;
    }

    private Location getPos1() {
        return MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.POS_1);
    }

    private Location getPos2() {
        return MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.POS_2);
    }

}
