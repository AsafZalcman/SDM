package myLocation;

import java.awt.*;

public class Location extends Point {

    public Location (xml.jaxb.schema.generated.Location i_JaxbLocation)
    {
        super(i_JaxbLocation.getX(),i_JaxbLocation.getY());
    }

}
