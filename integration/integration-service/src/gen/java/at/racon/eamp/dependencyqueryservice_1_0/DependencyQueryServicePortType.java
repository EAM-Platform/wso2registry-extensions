package at.racon.eamp.dependencyqueryservice_1_0;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.5.3
 * 2012-12-18T13:59:58.940+01:00
 * Generated source version: 2.5.3
 * 
 */
@WebService(targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", name = "DependencyQueryServicePortType")
@XmlSeeAlso({at.racon.eamp.eamp_types_1.ObjectFactory.class, at.racon.eamp.dependencyqueryservice_1.ObjectFactory.class, at.wsi4j.rci.securitytypes._3_0.ObjectFactory.class, at.grz.jp.datatypes_2.ObjectFactory.class, at.racon.eamp.eamp_parameters_1.ObjectFactory.class, at.grz.jp.servicelayertypes_2.ObjectFactory.class})
public interface DependencyQueryServicePortType {

    @WebResult(name = "value", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
    @RequestWrapper(localName = "queryServiceDependecies", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", className = "at.racon.eamp.dependencyqueryservice_1.QueryServiceDependeciesRequest")
    @WebMethod(action = "http://www.racon.at/eamp/DependencyQueryService-1.0/queryServiceDependecies")
    @ResponseWrapper(localName = "queryServiceDependeciesResponse", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0", className = "at.racon.eamp.dependencyqueryservice_1.QueryServiceDependeciesResponse")
    public at.racon.eamp.eamp_types_1.SubscriptionList queryServiceDependecies(
        @WebParam(name = "domain", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.grz.jp.datatypes_2.KeyList domain,
        @WebParam(name = "role", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.grz.jp.datatypes_2.Key role,
        @WebParam(name = "application", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.racon.eamp.eamp_types_1.Application application,
        @WebParam(name = "module", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.racon.eamp.eamp_types_1.Module module,
        @WebParam(name = "service", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.racon.eamp.eamp_types_1.ApplicationService service,
        @WebParam(name = "tier", targetNamespace = "http://www.racon.at/eamp/DependencyQueryService-1.0")
        at.grz.jp.datatypes_2.Key tier
    ) throws RCIFaultMsg, SystemExceptionMsg, ServiceRegistryExceptionMsg;
}
