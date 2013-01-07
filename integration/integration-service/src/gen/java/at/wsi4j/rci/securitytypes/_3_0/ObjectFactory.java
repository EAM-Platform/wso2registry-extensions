
package at.wsi4j.rci.securitytypes._3_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.wsi4j.rci.securitytypes._3_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RCIFault_QNAME = new QName("http://www.wsi4j.at/rci/SecurityTypes/3.0.0", "RCIFault");
    private final static QName _RCISecurity_QNAME = new QName("http://www.wsi4j.at/rci/SecurityTypes/3.0.0", "RCISecurity");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.wsi4j.rci.securitytypes._3_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RCISecurityType }
     * 
     */
    public RCISecurityType createRCISecurityType() {
        return new RCISecurityType();
    }

    /**
     * Create an instance of {@link RCIFaultType }
     * 
     */
    public RCIFaultType createRCIFaultType() {
        return new RCIFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RCIFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wsi4j.at/rci/SecurityTypes/3.0.0", name = "RCIFault")
    public JAXBElement<RCIFaultType> createRCIFault(RCIFaultType value) {
        return new JAXBElement<RCIFaultType>(_RCIFault_QNAME, RCIFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RCISecurityType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.wsi4j.at/rci/SecurityTypes/3.0.0", name = "RCISecurity")
    public JAXBElement<RCISecurityType> createRCISecurity(RCISecurityType value) {
        return new JAXBElement<RCISecurityType>(_RCISecurity_QNAME, RCISecurityType.class, null, value);
    }

}
