package alugueis.alugueis.location;

import android.location.Location;

public interface LocationSimpleListener {
    void onLocationChange(Location location);

    void onProviderChange(String provider);
}
