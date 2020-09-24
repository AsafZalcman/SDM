package myLocation;

import interfaces.ILocationable;

import java.util.*;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    //maybe should be Interface which implement getId and getLocation instead of Integer
    private static Map<Location, ILocationable> m_LocationToILocationable = new HashMap<>();

    public static void isValidLocation(int x, int y) throws LocationException {
        boolean res = x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
        if (!res) {
            throw new LocationException("X value must be between " + X_LOWER_LIMIT + " to " + X_UPPER_LIMIT + " and Y value must be between " +
                    Y_LOWER_LIMIT + " to " + Y_UPPER_LIMIT);
        }
    }

    public static boolean isLocationAvailable(int x, int y) throws LocationException {
        isValidLocation(x, y);
        ILocationable locationable = m_LocationToILocationable.get(new Location(x, y));
        if (locationable != null) {
            throw new LocationException("The location: (" + x + "," + y + ") is already occupied By " + locationable.getClass().getSimpleName() + " with the ID: " + locationable.getId());
        }
        return true;
    }

    public static void addLocation(int x, int y, ILocationable i_Locationable) throws IllegalArgumentException {
        try {
            isLocationAvailable(x, y);
            m_LocationToILocationable.put(new Location(x , y), i_Locationable);

        } catch (LocationException e) {
            throw new IllegalArgumentException("Setting the location (" + x + "," + y + ") to " + i_Locationable.getClass().getSimpleName() + " with the ID: " + i_Locationable.getId() + " Failed because " + e.getMessage());
        }
    }

    public static void setLocations(Map<Location, ILocationable> i_LocationToId) {
        m_LocationToILocationable = i_LocationToId;
    }

    public static Map<Location, ILocationable> getLocations() {
        return m_LocationToILocationable ;
    }

    public static void initLocations() {
        m_LocationToILocationable.clear();
    }

    public static Location getMaxLocation() {
        int maxY = 1;
        int maxX = maxY;
        for(Location location: m_LocationToILocationable.keySet()){
            if(location.x > maxX){ maxX = location.x; }
            if(location.y > maxY){ maxY = location.y; }
        }
        maxX++;
        maxY++;
        
        return new Location(maxX,maxY);
    }

    public static ILocationable getILocationable(Location i_Location){
        return m_LocationToILocationable.get(i_Location);
    }

    public static Collection<Location> getAllAvailableLocations(){
        List<Location> locationList = new ArrayList<>();
        for (int i = X_LOWER_LIMIT ;i<=X_UPPER_LIMIT ; i++ )
        {
            for (int j = Y_LOWER_LIMIT ; j<=Y_UPPER_LIMIT ; j++)
            {
                try {
                    if(isLocationAvailable(i,j))
                    {
                      locationList.add(new Location(i,j));
                    }
                } catch (LocationException ignored) {
                }
            }
        }

        return locationList;
    }
}