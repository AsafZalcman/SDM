package xml.jaxb;

import models.Item;
import models.Store;
import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

import java.util.Collection;
import java.util.stream.Collectors;

public class JaxbConverter {
    IJaxbDataLoader m_IJaxbLoader;
    SuperDuperMarketDescriptor m_SuperDuperMarketDescriptor;

    public JaxbConverter(IJaxbDataLoader i_IJaxbLoader)
    {
        m_IJaxbLoader=i_IJaxbLoader;
    }
    public Collection<Store> getStores()
    {
        if(m_SuperDuperMarketDescriptor==null)
        {
            return null;
        }

        return m_SuperDuperMarketDescriptor.getSDMStores().getSDMStore().stream().map(Store::new).collect(Collectors.toList());
    }

    public Collection<Item> getItems()
    {
        if(m_SuperDuperMarketDescriptor==null)
        {
            return null;
        }

        return m_SuperDuperMarketDescriptor.getSDMItems().getSDMItem().stream().map(Item::new).collect(Collectors.toList());
    }

    public void loadJaxbData(String i_PathToFile) throws Exception {
        m_SuperDuperMarketDescriptor=m_IJaxbLoader.load(i_PathToFile);
    }

    public void setIJaxbDataLoader(IJaxbDataLoader i_IJaxbDataLoader)
    {
        m_IJaxbLoader=i_IJaxbDataLoader;
    }
}
