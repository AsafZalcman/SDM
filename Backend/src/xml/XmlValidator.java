package xml;


import xml.jaxb.schema.generatedV2.SuperDuperMarketDescriptor;

public interface XmlValidator {

    void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException;
}
