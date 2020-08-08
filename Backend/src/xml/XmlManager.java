package xml;

import xml.jaxb.IJaxbDataLoader;
import xml.jaxb.schema.generated.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XmlManager implements IJaxbDataLoader {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "xml.jaxb.schema.generated";
    private final static String XML_SUFFIX = ".xml";
    private final Collection<XmlValidator> m_Validators;

    public XmlManager(Collection<XmlValidator> validators) {
        this.m_Validators = validators;
    }
    public XmlManager()
    {
        m_Validators = new ArrayList<>();
        m_Validators.add(new XmlItemsValidator());
        m_Validators.add(new XmlItemValidator());
        m_Validators.add(new XmlStoresValidator());
        m_Validators.add(new XmlStoreValidator());;
    }

    @Override
    public SuperDuperMarketDescriptor load(String i_PathToFile) throws Exception {
        if (!isWithXmlSuffix(i_PathToFile)) {
            throw new IllegalArgumentException("Error:\"" + i_PathToFile + "\" file dos'ent have " + XML_SUFFIX + " suffix");
        }
        try (InputStream inputStream = new FileInputStream(i_PathToFile)) {
            SuperDuperMarketDescriptor superDuperMarketDescriptor = deserializeFrom(inputStream);
            for (XmlValidator validator : m_Validators
            ) {
                validator.validate(superDuperMarketDescriptor);
            }
            return superDuperMarketDescriptor;

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error:\"" + i_PathToFile + "\"" + " file is not exists");
        } catch (IOException | JAXBException e) {
            throw new Exception("A general glitch occurred while loading \"" + i_PathToFile + "\"" + "\n" + e.getMessage());
        }
    }

        private static SuperDuperMarketDescriptor deserializeFrom(InputStream i_InputStream) throws JAXBException {
            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            return (SuperDuperMarketDescriptor) u.unmarshal(i_InputStream);
        }

        private boolean isWithXmlSuffix(String i_PathToFile)
        {
            return i_PathToFile.endsWith(XML_SUFFIX);
        }
}
