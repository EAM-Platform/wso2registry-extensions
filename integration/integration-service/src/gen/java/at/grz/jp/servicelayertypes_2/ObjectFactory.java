
package at.grz.jp.servicelayertypes_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.grz.jp.servicelayertypes_2 package. 
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

    private final static QName _ServiceContext_QNAME = new QName("http://www.grz.at/jp/ServiceLayerTypes-2.5", "ServiceContext");
    private final static QName _ServiceException_QNAME = new QName("http://www.grz.at/jp/ServiceLayerTypes-2.5", "ServiceException");
    private final static QName _SystemException_QNAME = new QName("http://www.grz.at/jp/ServiceLayerTypes-2.5", "SystemException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.grz.jp.servicelayertypes_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SystemException }
     * 
     */
    public SystemException createSystemException() {
        return new SystemException();
    }

    /**
     * Create an instance of {@link ServiceContext }
     * 
     */
    public ServiceContext createServiceContext() {
        return new ServiceContext();
    }

    /**
     * Create an instance of {@link ServiceException }
     * 
     */
    public ServiceException createServiceException() {
        return new ServiceException();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceContext }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/ServiceLayerTypes-2.5", name = "ServiceContext")
    public JAXBElement<ServiceContext> createServiceContext(ServiceContext value) {
        return new JAXBElement<ServiceContext>(_ServiceContext_QNAME, ServiceContext.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/ServiceLayerTypes-2.5", name = "ServiceException")
    public JAXBElement<ServiceException> createServiceException(ServiceException value) {
        return new JAXBElement<ServiceException>(_ServiceException_QNAME, ServiceException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.grz.at/jp/ServiceLayerTypes-2.5", name = "SystemException")
    public JAXBElement<SystemException> createSystemException(SystemException value) {
        return new JAXBElement<SystemException>(_SystemException_QNAME, SystemException.class, null, value);
    }

}
