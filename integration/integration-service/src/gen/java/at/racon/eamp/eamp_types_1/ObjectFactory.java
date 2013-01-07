
package at.racon.eamp.eamp_types_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.eamp_types_1 package. 
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

    private final static QName _Subscription_QNAME = new QName("http://www.racon.at/eamp/eamp-types-1.0", "subscription");
    private final static QName _Application_QNAME = new QName("http://www.racon.at/eamp/eamp-types-1.0", "application");
    private final static QName _Module_QNAME = new QName("http://www.racon.at/eamp/eamp-types-1.0", "module");
    private final static QName _Artifact_QNAME = new QName("http://www.racon.at/eamp/eamp-types-1.0", "artifact");
    private final static QName _ApplicationService_QNAME = new QName("http://www.racon.at/eamp/eamp-types-1.0", "applicationService");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.eamp_types_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ApplicationService }
     * 
     */
    public ApplicationService createApplicationService() {
        return new ApplicationService();
    }

    /**
     * Create an instance of {@link Artifact }
     * 
     */
    public Artifact createArtifact() {
        return new Artifact();
    }

    /**
     * Create an instance of {@link Module }
     * 
     */
    public Module createModule() {
        return new Module();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link Subscription }
     * 
     */
    public Subscription createSubscription() {
        return new Subscription();
    }

    /**
     * Create an instance of {@link ArtifactList }
     * 
     */
    public ArtifactList createArtifactList() {
        return new ArtifactList();
    }

    /**
     * Create an instance of {@link ApplicationServiceList }
     * 
     */
    public ApplicationServiceList createApplicationServiceList() {
        return new ApplicationServiceList();
    }

    /**
     * Create an instance of {@link SubscriptionList }
     * 
     */
    public SubscriptionList createSubscriptionList() {
        return new SubscriptionList();
    }

    /**
     * Create an instance of {@link ModuleList }
     * 
     */
    public ModuleList createModuleList() {
        return new ModuleList();
    }

    /**
     * Create an instance of {@link ApplicationList }
     * 
     */
    public ApplicationList createApplicationList() {
        return new ApplicationList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Subscription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-types-1.0", name = "subscription")
    public JAXBElement<Subscription> createSubscription(Subscription value) {
        return new JAXBElement<Subscription>(_Subscription_QNAME, Subscription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Application }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-types-1.0", name = "application")
    public JAXBElement<Application> createApplication(Application value) {
        return new JAXBElement<Application>(_Application_QNAME, Application.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Module }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-types-1.0", name = "module")
    public JAXBElement<Module> createModule(Module value) {
        return new JAXBElement<Module>(_Module_QNAME, Module.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Artifact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-types-1.0", name = "artifact")
    public JAXBElement<Artifact> createArtifact(Artifact value) {
        return new JAXBElement<Artifact>(_Artifact_QNAME, Artifact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/eamp-types-1.0", name = "applicationService")
    public JAXBElement<ApplicationService> createApplicationService(ApplicationService value) {
        return new JAXBElement<ApplicationService>(_ApplicationService_QNAME, ApplicationService.class, null, value);
    }

}
