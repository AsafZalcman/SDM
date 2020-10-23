package myLocation;

import interfaces.ILocationable;

import java.util.*;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    private static Map<String, List<Location>> m_ZoneToLocations = new HashMap<>();

    public static void isValidLocation(int x, int y) throws LocationException {
        boolean res = x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
        if (!res) {
            throw new LocationException("X value must be between " + X_LOWER_LIMIT + " to " + X_UPPER_LIMIT + " and Y value must be between " +
                    Y_LOWER_LIMIT + " to " + Y_UPPER_LIMIT);
        }
    }

    public static boolean isLocationAvailable(String i_ZoneName,int x, int y) throws LocationException {
        isValidLocation(x, y);
        List<Location> locations =  m_ZoneToLocations.get(i_ZoneName);
        if ( locations!=null && m_ZoneToLocations.get(i_ZoneName).contains(new Location(x,y))) {
            throw new LocationException("The location: (" + x + "," + y + ") is already occupied");
        }
        return true;
    }

    public static void addLocation(String i_ZoneName,int x, int y) throws IllegalArgumentException {
        try {
            isLocationAvailable(i_ZoneName,x, y);
            List<Location> locations = m_ZoneToLocations.getOrDefault(i_ZoneName,new ArrayList<>());
            locations.add(new Location(x , y));
            m_ZoneToLocations.put(i_ZoneName, locations);

        } catch (LocationException e) {
            throw new IllegalArgumentException("Setting the location (" + x + "," + y + ") in zone:\"" + i_ZoneName + "\" Failed because " + e.getMessage());
        }
    }

    public static Collection<Location> getAllAvailableLocations(String i_ZoneName) {
        List<Location> locationList = new ArrayList<>();
        for (int i = X_LOWER_LIMIT; i <= X_UPPER_LIMIT; i++) {
            for (int j = Y_LOWER_LIMIT; j <= Y_UPPER_LIMIT; j++) {
                try {
                    if (isLocationAvailable(i_ZoneName, i, j)) {
                        locationList.add(new Location(i, j));
                    }
                } catch (LocationException ignored) {
                }
            }
        }
        return locationList;
    }

}