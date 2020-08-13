package DtoModel;

import models.Store;
import myLocation.Location;

public class StoreDto {
    private final Store m_Store;

    public StoreDto(Store i_Store) {
        m_Store = i_Store;
    }

    public Location getLocation() {
        return m_Store.getLocation();
    }

    public int getId() {
        return m_Store.getId();
    }

    public String getName() {
        return m_Store.getStoreName();
    }

    public Double getPPK() {
        return m_Store.getPPK();
    }
}
