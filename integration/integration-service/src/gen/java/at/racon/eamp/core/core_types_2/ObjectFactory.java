
package at.racon.eamp.core.core_types_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.racon.eamp.core.core_types_2 package. 
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

    private final static QName _Document_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "document");
    private final static QName _ServiceInterface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "serviceInterface");
    private final static QName _ParameterList_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "parameterList");
    private final static QName _Port_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "port");
    private final static QName _UserInterfaceComponent_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "userInterfaceComponent");
    private final static QName _Person_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "person");
    private final static QName _HostInterface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "hostInterface");
    private final static QName _InformationObject_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "informationObject");
    private final static QName _Service_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "service");
    private final static QName _SoftwareArtifact_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "softwareArtifact");
    private final static QName _Artifact_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "artifact");
    private final static QName _NativeServiceInterface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "nativeServiceInterface");
    private final static QName _PortalEventInterface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "portalEventInterface");
    private final static QName _ContainedElement_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "containedElement");
    private final static QName _Table_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "table");
    private final static QName _Interface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "interface");
    private final static QName _Endpoint_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "endpoint");
    private final static QName _Stakeholder_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "stakeholder");
    private final static QName _HostProgramm_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "hostProgramm");
    private final static QName _WebServiceInterface_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "webServiceInterface");
    private final static QName _Module_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "module");
    private final static QName _Application_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "application");
    private final static QName _BusinesDomain_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "businesDomain");
    private final static QName _ArtifactPredecessor_QNAME = new QName("http://www.racon.at/eamp/core/core-types-2.0", "predecessor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.racon.eamp.core.core_types_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HostProgramm }
     * 
     */
    public HostProgramm createHostProgramm() {
        return new HostProgramm();
    }

    /**
     * Create an instance of {@link UserInterfaceComponent }
     * 
     */
    public UserInterfaceComponent createUserInterfaceComponent() {
        return new UserInterfaceComponent();
    }

    /**
     * Create an instance of {@link Stakeholder }
     * 
     */
    public Stakeholder createStakeholder() {
        return new Stakeholder();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link HostInterface }
     * 
     */
    public HostInterface createHostInterface() {
        return new HostInterface();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link Module }
     * 
     */
    public Module createModule() {
        return new Module();
    }

    /**
     * Create an instance of {@link WebServiceInterface }
     * 
     */
    public WebServiceInterface createWebServiceInterface() {
        return new WebServiceInterface();
    }

    /**
     * Create an instance of {@link InformationObject }
     * 
     */
    public InformationObject createInformationObject() {
        return new InformationObject();
    }

    /**
     * Create an instance of {@link BusinesDomain }
     * 
     */
    public BusinesDomain createBusinesDomain() {
        return new BusinesDomain();
    }

    /**
     * Create an instance of {@link PortalEventInterface }
     * 
     */
    public PortalEventInterface createPortalEventInterface() {
        return new PortalEventInterface();
    }

    /**
     * Create an instance of {@link ContainedElement }
     * 
     */
    public ContainedElement createContainedElement() {
        return new ContainedElement();
    }

    /**
     * Create an instance of {@link Table }
     * 
     */
    public Table createTable() {
        return new Table();
    }

    /**
     * Create an instance of {@link Interface }
     * 
     */
    public Interface createInterface() {
        return new Interface();
    }

    /**
     * Create an instance of {@link Endpoint }
     * 
     */
    public Endpoint createEndpoint() {
        return new Endpoint();
    }

    /**
     * Create an instance of {@link Artifact }
     * 
     */
    public Artifact createArtifact() {
        return new Artifact();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link ServiceInterface }
     * 
     */
    public ServiceInterface createServiceInterface() {
        return new ServiceInterface();
    }

    /**
     * Create an instance of {@link NativeServiceInterface }
     * 
     */
    public NativeServiceInterface createNativeServiceInterface() {
        return new NativeServiceInterface();
    }

    /**
     * Create an instance of {@link ParameterList }
     * 
     */
    public ParameterList createParameterList() {
        return new ParameterList();
    }

    /**
     * Create an instance of {@link Service }
     * 
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link SoftwareArtifact }
     * 
     */
    public SoftwareArtifact createSoftwareArtifact() {
        return new SoftwareArtifact();
    }

    /**
     * Create an instance of {@link ContainedElementList }
     * 
     */
    public ContainedElementList createContainedElementList() {
        return new ContainedElementList();
    }

    /**
     * Create an instance of {@link PortalEventInterfaceList }
     * 
     */
    public PortalEventInterfaceList createPortalEventInterfaceList() {
        return new PortalEventInterfaceList();
    }

    /**
     * Create an instance of {@link HostInterfaceList }
     * 
     */
    public HostInterfaceList createHostInterfaceList() {
        return new HostInterfaceList();
    }

    /**
     * Create an instance of {@link DocumentList }
     * 
     */
    public DocumentList createDocumentList() {
        return new DocumentList();
    }

    /**
     * Create an instance of {@link NativeServiceInterfaceList }
     * 
     */
    public NativeServiceInterfaceList createNativeServiceInterfaceList() {
        return new NativeServiceInterfaceList();
    }

    /**
     * Create an instance of {@link HostProgrammList }
     * 
     */
    public HostProgrammList createHostProgrammList() {
        return new HostProgrammList();
    }

    /**
     * Create an instance of {@link PortList }
     * 
     */
    public PortList createPortList() {
        return new PortList();
    }

    /**
     * Create an instance of {@link InterfaceList }
     * 
     */
    public InterfaceList createInterfaceList() {
        return new InterfaceList();
    }

    /**
     * Create an instance of {@link StakeholderList }
     * 
     */
    public StakeholderList createStakeholderList() {
        return new StakeholderList();
    }

    /**
     * Create an instance of {@link ApplicationList }
     * 
     */
    public ApplicationList createApplicationList() {
        return new ApplicationList();
    }

    /**
     * Create an instance of {@link ArtifactList }
     * 
     */
    public ArtifactList createArtifactList() {
        return new ArtifactList();
    }

    /**
     * Create an instance of {@link TableList }
     * 
     */
    public TableList createTableList() {
        return new TableList();
    }

    /**
     * Create an instance of {@link InformationObjectList }
     * 
     */
    public InformationObjectList createInformationObjectList() {
        return new InformationObjectList();
    }

    /**
     * Create an instance of {@link UserInterfaceComponentList }
     * 
     */
    public UserInterfaceComponentList createUserInterfaceComponentList() {
        return new UserInterfaceComponentList();
    }

    /**
     * Create an instance of {@link BusinesDomainList }
     * 
     */
    public BusinesDomainList createBusinesDomainList() {
        return new BusinesDomainList();
    }

    /**
     * Create an instance of {@link WebServiceInterfaceList }
     * 
     */
    public WebServiceInterfaceList createWebServiceInterfaceList() {
        return new WebServiceInterfaceList();
    }

    /**
     * Create an instance of {@link PersonList }
     * 
     */
    public PersonList createPersonList() {
        return new PersonList();
    }

    /**
     * Create an instance of {@link ServiceInterfaceList }
     * 
     */
    public ServiceInterfaceList createServiceInterfaceList() {
        return new ServiceInterfaceList();
    }

    /**
     * Create an instance of {@link ParameterListList }
     * 
     */
    public ParameterListList createParameterListList() {
        return new ParameterListList();
    }

    /**
     * Create an instance of {@link SoftwareArtifactList }
     * 
     */
    public SoftwareArtifactList createSoftwareArtifactList() {
        return new SoftwareArtifactList();
    }

    /**
     * Create an instance of {@link EndpointList }
     * 
     */
    public EndpointList createEndpointList() {
        return new EndpointList();
    }

    /**
     * Create an instance of {@link ServiceList }
     * 
     */
    public ServiceList createServiceList() {
        return new ServiceList();
    }

    /**
     * Create an instance of {@link ModuleList }
     * 
     */
    public ModuleList createModuleList() {
        return new ModuleList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Document }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "document")
    public JAXBElement<Document> createDocument(Document value) {
        return new JAXBElement<Document>(_Document_QNAME, Document.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "serviceInterface")
    public JAXBElement<ServiceInterface> createServiceInterface(ServiceInterface value) {
        return new JAXBElement<ServiceInterface>(_ServiceInterface_QNAME, ServiceInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParameterList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "parameterList")
    public JAXBElement<ParameterList> createParameterList(ParameterList value) {
        return new JAXBElement<ParameterList>(_ParameterList_QNAME, ParameterList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Port }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "port")
    public JAXBElement<Port> createPort(Port value) {
        return new JAXBElement<Port>(_Port_QNAME, Port.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserInterfaceComponent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "userInterfaceComponent")
    public JAXBElement<UserInterfaceComponent> createUserInterfaceComponent(UserInterfaceComponent value) {
        return new JAXBElement<UserInterfaceComponent>(_UserInterfaceComponent_QNAME, UserInterfaceComponent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HostInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "hostInterface")
    public JAXBElement<HostInterface> createHostInterface(HostInterface value) {
        return new JAXBElement<HostInterface>(_HostInterface_QNAME, HostInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformationObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "informationObject")
    public JAXBElement<InformationObject> createInformationObject(InformationObject value) {
        return new JAXBElement<InformationObject>(_InformationObject_QNAME, InformationObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Service }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "service")
    public JAXBElement<Service> createService(Service value) {
        return new JAXBElement<Service>(_Service_QNAME, Service.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoftwareArtifact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "softwareArtifact")
    public JAXBElement<SoftwareArtifact> createSoftwareArtifact(SoftwareArtifact value) {
        return new JAXBElement<SoftwareArtifact>(_SoftwareArtifact_QNAME, SoftwareArtifact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Artifact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "artifact")
    public JAXBElement<Artifact> createArtifact(Artifact value) {
        return new JAXBElement<Artifact>(_Artifact_QNAME, Artifact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NativeServiceInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "nativeServiceInterface")
    public JAXBElement<NativeServiceInterface> createNativeServiceInterface(NativeServiceInterface value) {
        return new JAXBElement<NativeServiceInterface>(_NativeServiceInterface_QNAME, NativeServiceInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PortalEventInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "portalEventInterface")
    public JAXBElement<PortalEventInterface> createPortalEventInterface(PortalEventInterface value) {
        return new JAXBElement<PortalEventInterface>(_PortalEventInterface_QNAME, PortalEventInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContainedElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "containedElement")
    public JAXBElement<ContainedElement> createContainedElement(ContainedElement value) {
        return new JAXBElement<ContainedElement>(_ContainedElement_QNAME, ContainedElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Table }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "table")
    public JAXBElement<Table> createTable(Table value) {
        return new JAXBElement<Table>(_Table_QNAME, Table.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Interface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "interface")
    public JAXBElement<Interface> createInterface(Interface value) {
        return new JAXBElement<Interface>(_Interface_QNAME, Interface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Endpoint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "endpoint")
    public JAXBElement<Endpoint> createEndpoint(Endpoint value) {
        return new JAXBElement<Endpoint>(_Endpoint_QNAME, Endpoint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Stakeholder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "stakeholder")
    public JAXBElement<Stakeholder> createStakeholder(Stakeholder value) {
        return new JAXBElement<Stakeholder>(_Stakeholder_QNAME, Stakeholder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HostProgramm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "hostProgramm")
    public JAXBElement<HostProgramm> createHostProgramm(HostProgramm value) {
        return new JAXBElement<HostProgramm>(_HostProgramm_QNAME, HostProgramm.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebServiceInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "webServiceInterface")
    public JAXBElement<WebServiceInterface> createWebServiceInterface(WebServiceInterface value) {
        return new JAXBElement<WebServiceInterface>(_WebServiceInterface_QNAME, WebServiceInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Module }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "module")
    public JAXBElement<Module> createModule(Module value) {
        return new JAXBElement<Module>(_Module_QNAME, Module.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Application }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "application")
    public JAXBElement<Application> createApplication(Application value) {
        return new JAXBElement<Application>(_Application_QNAME, Application.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinesDomain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "businesDomain")
    public JAXBElement<BusinesDomain> createBusinesDomain(BusinesDomain value) {
        return new JAXBElement<BusinesDomain>(_BusinesDomain_QNAME, BusinesDomain.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.racon.at/eamp/core/core-types-2.0", name = "predecessor", scope = Artifact.class)
    public JAXBElement<String> createArtifactPredecessor(String value) {
        return new JAXBElement<String>(_ArtifactPredecessor_QNAME, String.class, Artifact.class, value);
    }

}
