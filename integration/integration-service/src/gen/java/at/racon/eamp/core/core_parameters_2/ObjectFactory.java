
package at.racon.eamp.core.core_parameters_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.core.core_parameters_2 package. 
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

    private final static QName _RegistryException_QNAME = new QName("http://www.racon.at/eamp/core/core-parameters-2.0", "RegistryException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.core.core_parameters_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegistryException }
     * 
     */
    public RegistryException createRegistryException() {
        return new RegistryException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-parameters-2.0", name = "RegistryException")
    public JAXBElement<RegistryException> createRegistryException(RegistryException value) {
        return new JAXBElement<RegistryException>(_RegistryException_QNAME, RegistryException.class, null, value);
    }

}
