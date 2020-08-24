package myLocation;

public class LocationManager {
    public static final int X_UPPER_LIMIT = 50;
    public static final int X_LOWER_LIMIT = 1;
    public static final int Y_UPPER_LIMIT = 50;
    public static final int Y_LOWER_LIMIT = 1;
    private static boolean[][] m_Locations = new boolean[50][50];

    public static boolean isValidLocation(int x , int y) throws LocationException {
        boolean res = x <= X_UPPER_LIMIT && x >= X_LOWER_LIMIT && y <= Y_UPPER_LIMIT && y >= Y_LOWER_LIMIT;
        if(!res)
        {
            throw new LocationException("X value must be between " +X_LOWER_LIMIT + " to " + X_UPPER_LIMIT + " and Y value must be between " +
                    Y_LOWER_LIMIT + " to " + Y_UPPER_LIMIT);
        }
        return true;
    }

    public static boolean isLocationAvailable(int x , int y) throws LocationException {
        isValidLocation(x, y);
        if (m_Locations[x - 1][y - 1]) {
            throw new LocationException("The location: (" + x + "," + y + ") is already occupied");
        }

        return true;
    }

    public static boolean addLocation(int x, int y) throws IllegalArgumentException {
        boolean success = false;
        try {
            if (isLocationAvailable(x,y)) {
                m_Locations[x - 1][y - 1] = true;
                success = true;
            }
        } catch (LocationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return success;
    }
}
