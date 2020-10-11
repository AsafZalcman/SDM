package xml.jaxb;


import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

import java.io.InputStream;

public interface IJaxbDataLoader {

    public SuperDuperMarketDescriptor load(String i_PathToFile) throws Exception;
    public SuperDuperMarketDescriptor load(InputStream i_FileInputStream) throws Exception;


    }
