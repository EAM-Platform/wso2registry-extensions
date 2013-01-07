
package at.racon.eamp.core.registryservice_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.core.registryservice_2 package. 
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

    private final static QName _LoadContainedElements_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadContainedElements");
    private final static QName _LoadContainedElementsResponse_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadContainedElementsResponse");
    private final static QName _LoadArtifactResponse_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadArtifactResponse");
    private final static QName _LoadDependencies_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadDependencies");
    private final static QName _LoadArtifact_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadArtifact");
    private final static QName _LoadDependenciesResponse_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "loadDependenciesResponse");
    private final static QName _SearchArtifacts_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "searchArtifacts");
    private final static QName _SearchArtifactsResponse_QNAME = new QName("http://www.racon.at/eamp/core/RegistryService-2.0", "searchArtifactsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.core.registryservice_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoadDependenciesResponse }
     * 
     */
    public LoadDependenciesResponse createLoadDependenciesResponse() {
        return new LoadDependenciesResponse();
    }

    /**
     * Create an instance of {@link LoadArtifactRequest }
     * 
     */
    public LoadArtifactRequest createLoadArtifactRequest() {
        return new LoadArtifactRequest();
    }

    /**
     * Create an instance of {@link SearchArtifactsResponse }
     * 
     */
    public SearchArtifactsResponse createSearchArtifactsResponse() {
        return new SearchArtifactsResponse();
    }

    /**
     * Create an instance of {@link LoadDependenciesRequest }
     * 
     */
    public LoadDependenciesRequest createLoadDependenciesRequest() {
        return new LoadDependenciesRequest();
    }

    /**
     * Create an instance of {@link LoadArtifactResponse }
     * 
     */
    public LoadArtifactResponse createLoadArtifactResponse() {
        return new LoadArtifactResponse();
    }

    /**
     * Create an instance of {@link LoadContainedElementsRequest }
     * 
     */
    public LoadContainedElementsRequest createLoadContainedElementsRequest() {
        return new LoadContainedElementsRequest();
    }

    /**
     * Create an instance of {@link SearchArtifactsRequest }
     * 
     */
    public SearchArtifactsRequest createSearchArtifactsRequest() {
        return new SearchArtifactsRequest();
    }

    /**
     * Create an instance of {@link LoadContainedElementsResponse }
     * 
     */
    public LoadContainedElementsResponse createLoadContainedElementsResponse() {
        return new LoadContainedElementsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadContainedElementsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadContainedElements")
    public JAXBElement<LoadContainedElementsRequest> createLoadContainedElements(LoadContainedElementsRequest value) {
        return new JAXBElement<LoadContainedElementsRequest>(_LoadContainedElements_QNAME, LoadContainedElementsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadContainedElementsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadContainedElementsResponse")
    public JAXBElement<LoadContainedElementsResponse> createLoadContainedElementsResponse(LoadContainedElementsResponse value) {
        return new JAXBElement<LoadContainedElementsResponse>(_LoadContainedElementsResponse_QNAME, LoadContainedElementsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadArtifactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadArtifactResponse")
    public JAXBElement<LoadArtifactResponse> createLoadArtifactResponse(LoadArtifactResponse value) {
        return new JAXBElement<LoadArtifactResponse>(_LoadArtifactResponse_QNAME, LoadArtifactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadDependenciesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadDependencies")
    public JAXBElement<LoadDependenciesRequest> createLoadDependencies(LoadDependenciesRequest value) {
        return new JAXBElement<LoadDependenciesRequest>(_LoadDependencies_QNAME, LoadDependenciesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadArtifactRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadArtifact")
    public JAXBElement<LoadArtifactRequest> createLoadArtifact(LoadArtifactRequest value) {
        return new JAXBElement<LoadArtifactRequest>(_LoadArtifact_QNAME, LoadArtifactRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadDependenciesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "loadDependenciesResponse")
    public JAXBElement<LoadDependenciesResponse> createLoadDependenciesResponse(LoadDependenciesResponse value) {
        return new JAXBElement<LoadDependenciesResponse>(_LoadDependenciesResponse_QNAME, LoadDependenciesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchArtifactsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "searchArtifacts")
    public JAXBElement<SearchArtifactsRequest> createSearchArtifacts(SearchArtifactsRequest value) {
        return new JAXBElement<SearchArtifactsRequest>(_SearchArtifacts_QNAME, SearchArtifactsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchArtifactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/RegistryService-2.0", name = "searchArtifactsResponse")
    public JAXBElement<SearchArtifactsResponse> createSearchArtifactsResponse(SearchArtifactsResponse value) {
        return new JAXBElement<SearchArtifactsResponse>(_SearchArtifactsResponse_QNAME, SearchArtifactsResponse.class, null, value);
    }

}
