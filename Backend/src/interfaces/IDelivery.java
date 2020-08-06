package interfaces;

import utils.Location;

import java.util.Collection;

public interface IDelivery {
    public Collection<Object> getDetails();
    public double getDeliveryPrice(Location i_DestLocation, double i_PPK);
}
