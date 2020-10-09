package xml;


import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

public interface XmlValidator {

    void validate(SuperDuperMarketDescriptor i_SuperDuperMarketDescriptor) throws XmlValidatorException;
}
