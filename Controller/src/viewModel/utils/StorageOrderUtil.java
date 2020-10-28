package viewModel.utils;

import dtoModel.OrderDto;
import dtoModel.StoreDto;
import managers.SuperDuperManager;
import models.Order;

import java.util.Map;
import java.util.stream.Collectors;

public class StorageOrderUtil {

    public static Map<StoreDto, OrderDto> convertStorageOrderStores(String zoneName,Map<Integer, Order> i_StoreIdToOrder) {

        return i_StoreIdToOrder.entrySet().stream()
                .collect(Collectors.toMap(entry -> new StoreDto(SuperDuperManager.getInstance().getStore(zoneName,entry.getKey())),
                        entry -> new OrderDto(entry.getValue())));
    }
}
