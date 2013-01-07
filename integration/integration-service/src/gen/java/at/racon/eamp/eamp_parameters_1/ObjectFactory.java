
package at.racon.eamp.eamp_parameters_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.eamp_parameters_1 package. 
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

    private final static QName _ServiceRegistryException_QNAME = new QName("http://www.racon.at/eamp/eamp-parameters-1.0", "ServiceRegistryException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.eamp_parameters_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceRegistryException }
     * 
     */
    public ServiceRegistryException createServiceRegistryException() {
        return new ServiceRegistryException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceRegistryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-parameters-1.0", name = "ServiceRegistryException")
    public JAXBElement<ServiceRegistryException> createServiceRegistryException(ServiceRegistryException value) {
        return new JAXBElement<ServiceRegistryException>(_ServiceRegistryException_QNAME, ServiceRegistryException.class, null, value);
    }

}
