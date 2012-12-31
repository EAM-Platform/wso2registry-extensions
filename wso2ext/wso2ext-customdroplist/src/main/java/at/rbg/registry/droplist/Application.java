package at.rbg.registry.droplist;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifactImpl;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.governance.generic.ui.utils.DropDownDataPopulator;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.ws.client.registry.WSRegistryServiceClient;
import org.wso2.carbon.ui.CarbonUIUtil;
import org.wso2.carbon.utils.ServerConstants;

public class Application implements DropDownDataPopulator {
    private static final Log log = LogFactory.getLog(Application.class); 
    
	private static final String sqlFindRegName = "SELECT REG_PATH_ID, REG_NAME FROM REG_RESOURCE "
			+ " where reg_media_type = ? " ;
	private static final String MEDIATYPE_APPLICATION = "application/vnd.wso2-application+xml";
	
	@SuppressWarnings("unchecked")
	@Override
	public String[] getList(HttpServletRequest request, ServletConfig config) {
		
	    String cookie = (String) request.getSession().getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
	    String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), request.getSession());
	    try {
			WSRegistryServiceClient ws = new WSRegistryServiceClient(backendServerURL, cookie) ;
			Registry registry = ws;
			@SuppressWarnings("rawtypes")
			Map parameters = new HashMap();
			parameters.put("1", MEDIATYPE_APPLICATION); // mediaType
			parameters.put("query", sqlFindRegName);
			Resource result = registry.executeQuery(null,parameters);
			String[] paths = (String[]) result.getContent();
			Set<String> appNames = getGenericArtifactsforPaths(registry,paths);
            List<String> output = new ArrayList<String>(appNames);
            output.add(0, "Keine");
            return output.toArray(new String[output.size()]);

		} catch (RegistryException e) {
			log.error(e.getMessage());
		}
	    return new String[] {"Keine"};
	}
	
	private Set<String> getGenericArtifactsforPaths(Registry registry,
			String[] paths) {
		Set<String> retval = new TreeSet<String>();
		
		if (paths != null) {
			for (String path : paths) {
				try {
					Resource r = registry.get(path);				
					String content = new String((byte[]) r.getContent());
					XMLStreamReader reader;
					reader = XMLInputFactory.newInstance()
							.createXMLStreamReader(new StringReader(content));
					GovernanceArtifact ga = GovernanceArtifactImpl.create(registry,
							r.getUUID(), 
							new StAXOMBuilder(reader).getDocumentElement());
					retval.add(ga.getAttribute("details_name"));
				} catch (XMLStreamException e) {
				} catch (FactoryConfigurationError e) {
				} catch (GovernanceException e) {
				} catch (RegistryException e1) {
				}
			}
		}
		return retval;
	}
	
	
}
