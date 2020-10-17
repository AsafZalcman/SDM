package myLocation;

import interfaces.ILocationable;

import java.util.*;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    //maybe should be Interface which implement getId and getLocation instead of Integer
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

//  public static void setLocations(Map<Location, ILocationable> i_LocationToId) {
//      m_ZoneToLocations = i_LocationToId;
//  }

//    public static Map<String, List<Location>> getLocations() {
 //       return m_ZoneToLocations;
//    }

//   public static void initLocations() {
//       m_ZoneToLocations.clear();
//   }

//   public static Location getMaxLocation() {
//       int maxY = 1;
//       int maxX = maxY;
//       for(Location location: m_ZoneToLocations.keySet()){
//           if(location.x > maxX){ maxX = location.x; }
//           if(location.y > maxY){ maxY = location.y; }
//       }
//       maxX++;
//       maxY++;
//
//       return new Location(maxX,maxY);
//   }

//   public static ILocationable getILocationable(Location i_Location){
//       return m_ZoneToLocations.get(i_Location);
//   }

    public static Collection<Location> getAllAvailableLocations(String i_ZoneName){
       return m_ZoneToLocations.get(i_ZoneName);
    }
}