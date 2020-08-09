package DtoModel;

import myLocation.Location;

import java.util.Collection;

public class OrderDto
{
    private final Collection<ItemDto> m_ItemsDto;
    private final Location m_DestLocation;
    private final Location m_SourceLocation;
    private final double m_PPK;

    public OrderDto(Collection<ItemDto> i_ItemsDto, Location i_DestLocation, Location i_SourceLocation, double i_PPK) {
        m_ItemsDto = i_ItemsDto;
        m_DestLocation = i_DestLocation;
        m_SourceLocation = i_SourceLocation;
        m_PPK = i_PPK;
    }

    public Collection<ItemDto> getItemsDto() {
        return m_ItemsDto;
    }

    public Location getDestLocation() {
        return m_DestLocation;
    }

    public Location getSourceLocation() {
        return m_SourceLocation;
    }

    public double getPPK() {
        return m_PPK;
    }

    public double getDistanceFromSource() {
        return m_DestLocation.distance(m_SourceLocation);
    }

    public double getDeliveryPrice() {
        return getDistanceFromSource() * m_PPK;
    }


}
