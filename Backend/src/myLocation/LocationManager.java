package myLocation;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    private static boolean[][] m_Locations = new boolean[50][50];

    public static boolean isValidLocation(int x , int y) {
        return x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
    }

    private static boolean isLocationAvailable(int x , int y) {
        if (isValidLocation(x,y)) {
            return m_Locations[x-1][y-1];
        }
        return false;
    }

    public static boolean addLocation(int x, int y) {
        boolean success = false;
        if (isLocationAvailable(x,y)) {
            m_Locations[x - 1][y - 1] = true;
            success = true;
        }
        return success;
    }
}
