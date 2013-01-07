
package at.racon.eamp.dependencyqueryservice_1_0;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.5.3
 * 2012-12-18T13:59:58.924+01:00
 * Generated source version: 2.5.3
 */

@WebFault(name = "ServiceRegistryException", targetNamespace = "http://www.racon.at/eamp/eamp-parameters-1.0")
public class ServiceRegistryExceptionMsg extends Exception {
    
    private at.racon.eamp.eamp_parameters_1.ServiceRegistryException serviceRegistryException;

    public ServiceRegistryExceptionMsg() {
        super();
    }
    
    public ServiceRegistryExceptionMsg(String message) {
        super(message);
    }
    
    public ServiceRegistryExceptionMsg(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRegistryExceptionMsg(String message, at.racon.eamp.eamp_parameters_1.ServiceRegistryException serviceRegistryException) {
        super(message);
        this.serviceRegistryException = serviceRegistryException;
    }

    public ServiceRegistryExceptionMsg(String message, at.racon.eamp.eamp_parameters_1.ServiceRegistryException serviceRegistryException, Throwable cause) {
        super(message, cause);
        this.serviceRegistryException = serviceRegistryException;
    }

    public at.racon.eamp.eamp_parameters_1.ServiceRegistryException getFaultInfo() {
        return this.serviceRegistryException;
    }
}
