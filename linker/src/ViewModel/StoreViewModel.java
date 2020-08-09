package ViewModel;

import models.Store;
import utils.SuperDuperManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoreViewModel {

    public Collection<StoreDto> getAllStores()
    {
       return SuperDuperManager.getInstance().getStoreManager().getAllStores().stream().map(StoreDto::new).collect(Collectors.toList());
    }

public class StoreDto
    {
        private final Integer m_StoreID;
        private final String m_StoreName;
        private final double m_PPK;

        public StoreDto(Integer i_StoreID, String i_StoreName, double i_PPK) {
            m_StoreID = i_StoreID;
            m_StoreName = i_StoreName;
            m_PPK = i_PPK;
        }

        public StoreDto(Store i_Store)
        {
            this(i_Store.getId(),i_Store.getStoreName(),i_Store.getPPK());
        }

        public Integer getId() {
            return m_StoreID;
        }

        public String getStoreName() {
            return m_StoreName;
        }

        public double getPPK() {
            return m_PPK;
        }
    }

}


