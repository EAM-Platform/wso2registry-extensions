
package at.racon.eamp.dependencyqueryservice_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import at.grz.jp.datatypes_2.Key;
import at.grz.jp.datatypes_2.KeyList;
import at.racon.eamp.eamp_types_1.Application;
import at.racon.eamp.eamp_types_1.ApplicationService;
import at.racon.eamp.eamp_types_1.Module;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.dependencyqueryservice_1 package. 
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

    private final static QName _QueryServiceDependecies_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "queryServiceDependecies");
    private final static QName _QueryServiceDependeciesResponse_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "queryServiceDependeciesResponse");
    private final static QName _QueryServiceDependeciesRequestModule_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "module");
    private final static QName _QueryServiceDependeciesRequestApplication_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "application");
    private final static QName _QueryServiceDependeciesRequestService_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "service");
    private final static QName _QueryServiceDependeciesRequestDomain_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "domain");
    private final static QName _QueryServiceDependeciesRequestRole_QNAME = new QName("http://www.racon.at/eamp/DependencyQueryService-1.0", "role");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.dependencyqueryservice_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryServiceDependeciesRequest }
     * 
     */
    public QueryServiceDependeciesRequest createQueryServiceDependeciesRequest() {
        return new QueryServiceDependeciesRequest();
    }

    /**
     * Create an instance of {@link QueryServiceDependeciesResponse }
     * 
     */
    public QueryServiceDependeciesResponse createQueryServiceDependeciesResponse() {
        return new QueryServiceDependeciesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryServiceDependeciesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "queryServiceDependecies")
    public JAXBElement<QueryServiceDependeciesRequest> createQueryServiceDependecies(QueryServiceDependeciesRequest value) {
        return new JAXBElement<QueryServiceDependeciesRequest>(_QueryServiceDependecies_QNAME, QueryServiceDependeciesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryServiceDependeciesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "queryServiceDependeciesResponse")
    public JAXBElement<QueryServiceDependeciesResponse> createQueryServiceDependeciesResponse(QueryServiceDependeciesResponse value) {
        return new JAXBElement<QueryServiceDependeciesResponse>(_QueryServiceDependeciesResponse_QNAME, QueryServiceDependeciesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Module }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "module", scope = QueryServiceDependeciesRequest.class)
    public JAXBElement<Module> createQueryServiceDependeciesRequestModule(Module value) {
        return new JAXBElement<Module>(_QueryServiceDependeciesRequestModule_QNAME, Module.class, QueryServiceDependeciesRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Application }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "application", scope = QueryServiceDependeciesRequest.class)
    public JAXBElement<Application> createQueryServiceDependeciesRequestApplication(Application value) {
        return new JAXBElement<Application>(_QueryServiceDependeciesRequestApplication_QNAME, Application.class, QueryServiceDependeciesRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "service", scope = QueryServiceDependeciesRequest.class)
    public JAXBElement<ApplicationService> createQueryServiceDependeciesRequestService(ApplicationService value) {
        return new JAXBElement<ApplicationService>(_QueryServiceDependeciesRequestService_QNAME, ApplicationService.class, QueryServiceDependeciesRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KeyList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "domain", scope = QueryServiceDependeciesRequest.class)
    public JAXBElement<KeyList> createQueryServiceDependeciesRequestDomain(KeyList value) {
        return new JAXBElement<KeyList>(_QueryServiceDependeciesRequestDomain_QNAME, KeyList.class, QueryServiceDependeciesRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Key }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "role", scope = QueryServiceDependeciesRequest.class)
    public JAXBElement<Key> createQueryServiceDependeciesRequestRole(Key value) {
        return new JAXBElement<Key>(_QueryServiceDependeciesRequestRole_QNAME, Key.class, QueryServiceDependeciesRequest.class, value);
    }

}
