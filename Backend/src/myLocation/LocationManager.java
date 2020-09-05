package myLocation;

import interfaces.ILocationable;

import java.util.HashMap;
import java.util.Map;

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

    public static void isLocationAvailable(int x, int y) throws LocationException {
        isValidLocation(x, y);
        ILocationable locationable = m_LocationToILocationable.get(new Location(x - 1, y - 1));
        if (locationable != null) {
            throw new LocationException("The location: (" + x + "," + y + ") is already occupied By " + locationable.getClass().getSimpleName() + " with the ID: " + locationable.getId());
        }
    }

    public static void addLocation(int x, int y, ILocationable i_Locationable) throws IllegalArgumentException {
        try {
            isLocationAvailable(x, y);
            m_LocationToILocationable.put(new Location(x - 1, y - 1), i_Locationable);

        } catch (LocationException e) {
            throw new IllegalArgumentException("Set Location to " + i_Locationable.getClass().getSimpleName() + " with the ID: " + i_Locationable.getId() + " Failed because " + e.getMessage());
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
}