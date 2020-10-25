package viewModel;

import interfaces.ILocationable;
import myLocation.Location;
import myLocation.LocationManager;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;


public class LocationViewModel {

    public Collection<Point> getAllAvailableLocations(String i_ZoneName)
    {
        return LocationManager.getAllAvailableLocations(i_ZoneName).stream()
                .map(location -> new Point(location.x,location.y))
                .collect(Collectors.toList());
    }

}
