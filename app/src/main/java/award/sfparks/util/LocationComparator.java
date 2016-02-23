package award.sfparks.util;

import android.location.Location;

import java.util.Comparator;

import award.sfparks.model.ParkInfo;

public class LocationComparator implements Comparator<ParkInfo> {

    private final Location userLocation;
    public LocationComparator(Location location) {
        this.userLocation = location;
    }

    private float getDist(award.sfparks.model.Location location) {
        float out[] = new float[1];
        Location.distanceBetween(
                Double.valueOf(location.latitude),
                Double.valueOf(location.longitude),
                userLocation.getLatitude(),
                userLocation.getLongitude(),
                out);

        return out[0];
    }

    @Override
    public int compare(ParkInfo lhs, ParkInfo rhs) {
        if(lhs.location == null && rhs.location == null)
            return 0;
        if(rhs.location == null)
            return -1;
        if(lhs.location == null)
            return 1;

        return Math.round(getDist(lhs.location) - getDist(rhs.location));
    }
}