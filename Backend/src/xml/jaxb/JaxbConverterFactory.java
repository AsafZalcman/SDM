package xml.jaxb;

import xml.XmlManager;

import static xml.jaxb.JaxbConverterFactory.JaxbConverterType.XML;

public class JaxbConverterFactory {

    public static JaxbConverter create(JaxbConverterType i_JaxbConverterType)
    {
        JaxbConverter jaxbConverter =null;
        switch (i_JaxbConverterType) {
            case XML: jaxbConverter = new JaxbConverter(new XmlManager());
        }

        return jaxbConverter;
    }

        public enum JaxbConverterType
        {
            XML
        }
}
