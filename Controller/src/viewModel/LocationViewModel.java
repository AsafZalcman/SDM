package viewModel;

import interfaces.ILocationable;
import myLocation.Location;
import myLocation.LocationManager;

public class LocationViewModel {

    public Location getMaxLocation(){
        return LocationManager.getMaxLocation();
    }

    public int getMaxX(){
        return LocationManager.getMaxLocation().x;
    }

    public int getMaxY(){
        return LocationManager.getMaxLocation().y;
    }

    public ILocationable getILocationable(int x, int y){
        return LocationManager.getILocationable(new Location(x, y));
    }
}
