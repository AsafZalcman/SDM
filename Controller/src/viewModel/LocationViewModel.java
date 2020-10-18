package viewModel;

import interfaces.ILocationable;
import myLocation.Location;
import myLocation.LocationException;
import myLocation.LocationManager;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class LocationViewModel {

//    public Location getMaxLocation(){
//        return LocationManager.getMaxLocation();
//    }
//
//    public int getMaxX(){
//        return LocationManager.getMaxLocation().x;
//    }
//
//    public int getMaxY(){
//        return LocationManager.getMaxLocation().y;
//    }
//
//    public ILocationable getILocationable(int x, int y){
//        return LocationManager.getILocationable(new Location(x, y));
//    }

    public Collection<Point> getAllAvailableLocations(String i_ZoneName)
    {
        return LocationManager.getAllAvailableLocations(i_ZoneName).stream()
                .map(location -> new Point(location.x,location.y))
                .collect(Collectors.toList());
    }
}
