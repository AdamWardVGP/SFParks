package award.sfparks.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

public class LocationService implements LocationListener {

    private final LocationManager mgr;
    private LocationCallbacks listener;
    private Location lastLocation;
    private Context context;

    public LocationService(Context context) {
        mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
    }

    public void setListener(LocationCallbacks listener) {
        this.listener = listener;
        if(lastLocation != null)
            listener.locationUpdate(lastLocation);
    }

    public void clearListener() {
        this.listener = null;
    }

    @SuppressWarnings("ResourceType")
    public void getLocation() {

        if ((Build.VERSION.SDK_INT >= 23) &&
                (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if(listener!= null)
                listener.permissionNotGranted();
            return;
        }

        // Get GPS and network status
        boolean isGPSEnabled = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if(!isNetworkEnabled && !isGPSEnabled) {
            if(listener!= null)
                listener.noProviderAvailable();
            return;
        }

        if (isGPSEnabled) {
            mgr.requestSingleUpdate(LocationManager.GPS_PROVIDER,
                    this,
                    Looper.getMainLooper());
            Location loc = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                lastLocation = loc;
                if(listener!= null)
                    listener.locationUpdate(loc);
            }
        } else if (isNetworkEnabled) {
            mgr.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
                    this,
                    Looper.getMainLooper());
            Location loc = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                lastLocation = loc;
                if( listener!= null)
                    listener.locationUpdate(loc);
            }
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        lastLocation = location;
        if(listener != null)
            listener.locationUpdate(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public interface LocationCallbacks {
        void permissionNotGranted();
        void noProviderAvailable();
        void locationUpdate(Location location);
    }
}
