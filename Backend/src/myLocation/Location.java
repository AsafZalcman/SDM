package myLocation;

import java.awt.*;

public class Location extends Point {

    public Location(int x,int y)
    {
        super(x,y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
