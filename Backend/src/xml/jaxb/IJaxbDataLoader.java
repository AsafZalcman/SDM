package xml.jaxb;


import xml.jaxb.schema.generatedV2.SuperDuperMarketDescriptor;

public interface IJaxbDataLoader {

    public SuperDuperMarketDescriptor load(String i_PathToFile) throws Exception;
}
