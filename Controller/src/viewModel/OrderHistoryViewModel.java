package viewModel;

import dtoModel.OrderDto;
import dtoModel.StorageOrderDto;
import managers.SuperDuperManager;
import models.Order;
import models.StorageOrder;
import viewModel.utils.StorageOrderUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderHistoryViewModel {

    private SuperDuperManager m_SuperDuperManager = SuperDuperManager.getInstance();

    public Collection<StorageOrderDto> getAllStorageOrderOfUser(String i_ZoneName,int i_UserId) {

        List<StorageOrder> storageOrders = new ArrayList<>(getAllStorageOrders(i_ZoneName));
        return storageOrders.stream().filter(storageOrder -> storageOrder.getOrder().getUserId() == i_UserId)
                .map(storageOrder -> new StorageOrderDto(storageOrder, StorageOrderUtil.convertStorageOrderStores(i_ZoneName,storageOrder.getStoresIdToOrder()))).
                collect(Collectors.toList());
    }

// public Collection<OrderDto> getAllStorageOrderByOwner(String i_ZoneName, String i_UserName) {

//     List<StorageOrder> storageOrders = new ArrayList<>(getAllStorageOrders(i_ZoneName));
//     List<OrderDto> res = new ArrayList<>();
//     for (StorageOrder storageOrder: storageOrders
//          ) {
//         Map<Integer, Order> storeIdToOrder =storageOrder.getStoresIdToOrder();
//         for (Map.Entry<Integer,Order>entry : storeIdToOrder.entrySet()
//              ) {
//             if(i_UserName.equalsIgnoreCase(m_SuperDuperManager.getStore(i_ZoneName,entry.getKey()).getOwnerName()))
//             {
//                 res.add(new OrderDto(entry.getValue()));
//             }
//         }
//     }
//    return res;
// }

    private Collection<StorageOrder> getAllStorageOrders(String i_ZoneName)
    {
       return m_SuperDuperManager.getZone(i_ZoneName).getAllStorageOrders();
    }
}
