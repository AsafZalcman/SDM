package xml;

import xml.jaxb.schema.generatedV2.SDMCustomer;
import xml.jaxb.schema.generatedV2.SDMStore;
import xml.jaxb.schema.generatedV2.SuperDuperMarketDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class XmlCustomersValidator implements XmlValidator {
    @Override
    public void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException {
        validateUniqueIdForEachCustomer(i_SuperDuperMarketDescriptor.getSDMCustomers().getSDMCustomer());
    }

    private void validateUniqueIdForEachCustomer(Collection<SDMCustomer> i_Customer) throws XmlValidatorException {
        Map<Integer,String> customerIdToCustomerNameMap = new HashMap<>();
        String customerNameByIdInMap;
        for (SDMCustomer customer: i_Customer
        ) {
            customerNameByIdInMap = customerIdToCustomerNameMap.getOrDefault(customer.getId(),null);
            if(customerNameByIdInMap != null)
            {
                throw new XmlValidatorException("Error: Both Customer: " + customerNameByIdInMap + " and " + customer.getName() + " have the same id: " + customer.getId());
            }
            customerIdToCustomerNameMap.put(customer.getId(),customer.getName());
        }
    }

    }

