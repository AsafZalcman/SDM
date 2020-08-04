package xml;

public class XmlValidatorException extends Exception {

    public XmlValidatorException(String errorMessage) {
        super("XML parsing error: " + errorMessage);
    }
}
