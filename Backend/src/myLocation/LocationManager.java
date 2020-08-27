package myLocation;

import java.util.HashMap;
import java.util.Map;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    //maybe should be Interface which implement getId and getLocation instead of Integer
    private static Map<Location, Integer> m_LocationToId = new HashMap<>();

    public static boolean isValidLocation(int x, int y) throws LocationException {
        boolean res = x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
        if (!res) {
            throw new LocationException("X value must be between " + X_LOWER_LIMIT + " to " + X_UPPER_LIMIT + " and Y value must be between " +
                    Y_LOWER_LIMIT + " to " + Y_UPPER_LIMIT);
        }
        return true;
    }

    public static boolean isLocationAvailable(int x, int y) throws LocationException {
        isValidLocation(x, y);
        if (m_LocationToId.get(new Location(x - 1, y - 1)) != null) {
            throw new LocationException("The location: (" + x + "," + y + ") is already occupied");
        }

        return true;
    }

    public static boolean addLocation(int x, int y, int i_Id) throws IllegalArgumentException {
        boolean success = false;
        try {
            if (isLocationAvailable(x, y)) {
                m_LocationToId.put(new Location(x - 1, y - 1), i_Id);
                success = true;
            }
        } catch (LocationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return success;
    }

    public static void setLocations(Map<Location, Integer> i_LocationToId) {
        m_LocationToId = i_LocationToId;
    }

    public static Map<Location, Integer> getLocations() {
        return m_LocationToId;
    }

    public static void initLocations() {
        m_LocationToId.clear();
    }
}