package myLocation;

import xml.jaxb.schema.generated.Location;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    private static boolean[][] m_Locations = new boolean[50][50];

    public static boolean isValidLocation(Location i_Location) {
        int x = i_Location.getX();
        int y = i_Location.getY();
        return x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
    }

    private static boolean isLocationAvailable(Location i_Location) {
        if (isValidLocation(i_Location)) {
            int x = i_Location.getX() - 1;
            int y = i_Location.getY() - 1;
            return m_Locations[x][y];
        }
        return false;
    }

    public static boolean addLocation(Location i_Location) {
        boolean success = false;
        if (isLocationAvailable(i_Location)) {
            m_Locations[i_Location.getX() - 1][i_Location.getY() - 1] = true;
            success = true;
        }
        return success;
    }
}
