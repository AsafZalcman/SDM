package models;

import myLocation.Location;

import java.util.Collection;

public class TempDtoForFunction4Part5Wrapper {

    private final Collection<TempDtoForFunction4Part5> m_Dtos;
    private final Location m_DestLocation;
    private final Location m_SourceLocation;
    private final double m_PPK;

    public TempDtoForFunction4Part5Wrapper(Collection<TempDtoForFunction4Part5> i_TempDtoForFunction4Part5, Location i_DestLocation, Location i_SourceLocation, double i_PPK) {
        m_Dtos = i_TempDtoForFunction4Part5;
        m_DestLocation = i_DestLocation;
        m_SourceLocation = i_SourceLocation;
        m_PPK = i_PPK;
    }
    public double getDistanceFromSource() {
        return m_DestLocation.distance(m_SourceLocation);
    }

    public double getPPK() {
        return m_PPK;
    }

    public double getDeliveryPrice() {
        return getDistanceFromSource() * m_PPK;
    }

    public Collection<TempDtoForFunction4Part5> getDtos()
    {
        return m_Dtos;
    }
}
