package at.rbg.registry.reporting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.api.services.ServiceFilter;
import org.wso2.carbon.governance.api.services.ServiceManager;
import org.wso2.carbon.governance.api.services.dataobjects.Service;
import org.wso2.carbon.governance.api.util.GovernanceUtils;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.session.CurrentSession;
import org.wso2.carbon.registry.core.utils.RegistryUtils;
import org.wso2.carbon.registry.reporting.AbstractReportGenerator;
import org.wso2.carbon.registry.reporting.annotation.Property;
import org.wso2.carbon.reporting.api.ReportingException;
import org.wso2.carbon.reporting.util.JasperPrintProvider;
import org.wso2.carbon.reporting.util.ReportParamMap;
import org.wso2.carbon.reporting.util.ReportStream;

public class DependencyReportGenerator extends AbstractReportGenerator {
	private static final Log log = LogFactory
			.getLog(DependencyReportGenerator.class);

    private String service1;
    private String service2;
  
    @Property(mandatory = true)
    public void setService1(String service1) {
        this.service1 = service1;
    }
  
    @Property(mandatory = true)
    public void setService2(String service2) {
        this.service2 = service2;
    }
    
    @Override
	public ByteArrayOutputStream execute(String template, String type)
			throws IOException {
		try {
	          Registry registry = getRegistry();
	          
	            // Read Template
	            String templateContent = RegistryUtils.decodeBytes(
	                    (byte[]) registry.get(template).getContent());
	  
	            // Create Report Bean Collection
	            ServiceManager manager = new ServiceManager(
	                    GovernanceUtils.getGovernanceUserRegistry(registry, CurrentSession.getUser()));
	            List<ReportBean> beanList = new LinkedList<ReportBean>();
	            Service s1 = findService(manager, service1);
	            Service s2 = findService(manager, service2);
	            String s1_name = s1.getQName().getLocalPart();
	            String s2_name = s2.getQName().getLocalPart();
	            for (String key : s1.getAttributeKeys()) {
	                String[] s1_attributes = s1.getAttributes(key);
	                String[] s2_attributes = s2.getAttributes(key);
	                if (s2_attributes != null) {
	                    if (s1_attributes.length > 1 || s2_attributes.length > 1) {
	                        beanList.add(new ReportBean(s1_name, s2_name, key,
	                                getAttributeString(s1_attributes),
	                                getAttributeString(s2_attributes)));
	                    } else {
	                        beanList.add(new ReportBean(s1_name, s2_name, key,
	                                s1_attributes[0], s2_attributes[0]));
	                    }
	                }
	            }

			// Generate Report Stream

			return new ReportStream().getReportStream(new JasperPrintProvider()
					.createJasperPrint(
							new JRBeanCollectionDataSource(beanList),
							templateContent, new ReportParamMap[0]), type);
		} catch (RegistryException e) {
			log.error(e.getMessage());
		} catch (ReportingException e) {
			log.error(e.getMessage());
		} catch (JRException e) {
			log.error(e.getMessage());
		}
		 return new ByteArrayOutputStream(0);
	}
	
    private String getAttributeString(String[] attributes) {
        StringBuilder s1_attrString = new StringBuilder();
        for (String attribute : attributes) {
            s1_attrString.append(attribute).append(";");
        }
        return s1_attrString.toString();
    }
  
    private Service findService(ServiceManager manager, final String serviceStr)
            throws GovernanceException {
        Service[] temp = manager.findServices(new ServiceFilter() {
            public boolean matches(Service service) throws GovernanceException {
                String[] parts = serviceStr.split(";");
                return parts[0].equals(service.getQName().getLocalPart()) &&
                        (parts.length <= 1 ||
                                parts[1].equals(service.getQName().getNamespaceURI())) &&
                        (parts.length <= 2 ||
                                parts[2].equals(service.getAttribute("overview_version")));
            }
        });
        return (temp == null || temp.length == 0) ? null : temp[0];
    }
	  public static class ReportBean {
	        private String name1;
	        private String name2;
	        private String attr_name;
	        private String attr_value1;
	        private String attr_value2;
	  
	        public ReportBean(String name1, String name2, String attr_name, String attr_value1,
	                          String attr_value2) {
	            this.name1 = name1;
	            this.name2 = name2;
	            this.attr_name = attr_name;
	            this.attr_value1 = attr_value1;
	            this.attr_value2 = attr_value2;
	        }
	  
	        public String getName1() {
	            return name1;
	        }
	  
	        public String getName2() {
	            return name2;
	        }
	  
	        public String getAttr_name() {
	            return attr_name;
	        }
	  
	        public String getAttr_value1() {
	            return attr_value1;
	        }
	  
	        public String getAttr_value2() {
	            return attr_value2;
	        }
	    }
}
