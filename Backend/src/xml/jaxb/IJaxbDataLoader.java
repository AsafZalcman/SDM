package xml.jaxb;


import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

public interface IJaxbDataLoader {

    public SuperDuperMarketDescriptor load(String i_PathToFile) throws Exception;
}
