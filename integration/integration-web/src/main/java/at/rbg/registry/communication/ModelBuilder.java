package at.rbg.registry.communication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.governance.api.common.dataobjects.GovernanceArtifact;
import org.wso2.carbon.governance.api.exception.GovernanceException;
import org.wso2.carbon.registry.api.Association;

import at.rbg.registry.model.Application;
import at.rbg.registry.model.ClientApi;
import at.rbg.registry.model.ClientComponent;
import at.rbg.registry.model.HostProgram;
import at.rbg.registry.model.HostTraco;
import at.rbg.registry.model.ModelConstants;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.Person;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.Table;
import at.rbg.registry.model.WebComponent;
import at.rbg.registry.model.WebService;
import at.rbg.registry.model.validation.KeyTypes;
import at.rbg.registry.persistance.dao.KeyCodeDao;

/**
 * we create the RBGArtifacts from the WSO2 GenericArtifacts here. 
 *
 */
public class ModelBuilder { 
	private static final Logger log = LoggerFactory.getLogger(ModelBuilder.class);
	private static KeyCodeDao keyCodeDao;
	
	public void setKeyCodeDao(KeyCodeDao keyCodeDao) {
		ModelBuilder.keyCodeDao = keyCodeDao;
	}

	public static List<Association> createAssociations(GovernanceArtifact ga, List<RelationAttribute> ras, String path) {
		List<Association> retval = new ArrayList<Association>();
		for (RelationAttribute ra : ras) {
			if ("destination".equals(ra.getType().getName())) {
				try {
					String[] attrs = ga.getAttributes(ra.getAttributeName());
					if (attrs!=null) {
						for (String attr : attrs) {
							String[] spl= attr.split(":");
							if (spl.length==2) {
								Association sa = new Association();
								sa.setAssociationType(ra.getRelationName());
								sa.setSourcePath(path);
								sa.setDestinationPath(spl[1]);
								retval.add(sa);
							}
						}
					}
				} catch (GovernanceException e) {
					log.error("crap");
				}
			}
		}
		return retval;	
	}
	
	
	/**
	 *  create the correct, filled artifact in the RBG-Model 
	 * @param ga		GenericArtifact
	 * @param mediaKey  key of GenericArtifact
	 * @return
	 */
	public static RBGGovernanceArtifact createArtifact(GovernanceArtifact ga, String mediaKey, String path,
			Date created, Date lastUpdated) 
		throws IllegalStateException {

		RBGGovernanceArtifact rga = null;
		String sectionPrefix = "details_";
		
		if (ModelConstants.APPLICATION_ARTIFACT.equals(mediaKey)) {
			rga = new Application();
			try {
				String dom = keyCodeDao.resolveKeyValue(KeyTypes.DOMAIN_KEYTYPE, ga.getAttribute(sectionPrefix + "domain"));
				((Application) rga).setDomain(dom);
				((Application) rga).setApplCode(ga.getAttribute(sectionPrefix + "name"));
				((Application) rga).setLongname(ga.getAttribute(sectionPrefix + "longname"));
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		} 
		else if (ModelConstants.MODULE_ARTIFACT.equals(mediaKey)) {
			rga = new Module();
			try {
				String tier = keyCodeDao.resolveKeyValue(KeyTypes.TIER_KEYTYPE, ga.getAttribute(sectionPrefix + "tier"));
				((Module) rga).setTier(tier);
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		}
		else if (ModelConstants.CLIENTAPI_ARTIFACT.equals(mediaKey)) {
			rga = new ClientApi();
			try {
				String iftype = keyCodeDao.resolveKeyValue(KeyTypes.INTERFACE_KEYTYPE, ga.getAttribute(sectionPrefix + "interfaceType"));
				((ClientApi) rga).setInterfaceType(iftype);
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		}
		else if (ModelConstants.CLIENTCOMPONENT_ARTIFACT.equals(mediaKey)) {
			rga = new ClientComponent();
		}
		else if (ModelConstants.WEBCOMPONENT_ARTIFACT.equals(mediaKey)) {
			rga = new WebComponent();
		}
		else if (ModelConstants.WEBSERVICE_ARTIFACT.equals(mediaKey)) {
			sectionPrefix = "overview_";
			rga = new WebService();
			try {
				String iftype = keyCodeDao.resolveKeyValue(KeyTypes.INTERFACE_KEYTYPE, ga.getAttribute(sectionPrefix + "interfaceType"));
				((WebService) rga).setInterfaceType(iftype);
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		}
		else if (ModelConstants.HOSTTRACO_ARTIFACT.equals(mediaKey)) {
			rga = new HostTraco();
			try {
				String dataformat = keyCodeDao.resolveKeyValue(KeyTypes.HOSTTRACO_DATAFORMAT_KEYTYPE, ga.getAttribute(sectionPrefix + "dataformat"));
				((HostTraco) rga).setDataformat(dataformat);
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		}
		else if (ModelConstants.HOSTPROGRAM_ARTIFACT.equals(mediaKey)) {
			rga = new HostProgram();
			try {
				String iftype = keyCodeDao.resolveKeyValue(KeyTypes.INTERFACE_KEYTYPE, ga.getAttribute(sectionPrefix + "interfaceType"));
				((HostProgram) rga).setInterfaceType(iftype);
				String technology = keyCodeDao.resolveKeyValue(KeyTypes.HOSTPROGRAM_TECHNOLOGY_KEYTYPE, ga.getAttribute(sectionPrefix + "technology"));
				((HostProgram) rga).setTechnology(technology);
				String accessType = keyCodeDao.resolveKeyValue(KeyTypes.HOSTPROGRAM_ACCESS_KEYTYPE, ga.getAttribute(sectionPrefix + "accesstype"));
				((HostProgram) rga).setAccesstype(accessType);
				// you might want to add this attribute to more artifacts
				String release = ga.getAttribute(sectionPrefix + "release");
				((HostProgram) rga).setRelease(release);
			} catch (GovernanceException e) {
				log.error(e.getMessage());
			}
		}
		else if (ModelConstants.TABLE_ARTIFACT.equals(mediaKey)) {
			rga = new Table();
		}
		else if (ModelConstants.PERSON_ARTIFACT.equals(mediaKey)) {
				rga = new Person();
				try {
					((Person) rga).setUserId(ga.getAttribute(sectionPrefix + "userID"));
				} catch (GovernanceException e) {
					log.error(e.getMessage());
				}	
		}
		else {
			log.error("Unbekannter Artifact Typ: " + mediaKey);
			throw new IllegalStateException("Unbekannter Artifact Typ: " + mediaKey);
		}
		
		rga.setIdentifier(ga.getId());
		Calendar cre=Calendar.getInstance();
		cre.setTime(created);
		rga.setCreated(cre);
		Calendar upd=Calendar.getInstance();
		upd.setTime(lastUpdated);
		rga.setCreated(upd);
		
		rga.setPath(path);		
		try {
			rga.setName(ga.getAttribute(sectionPrefix + "name"));
			if (!(rga instanceof Person)) {
				rga.setVersion(ga.getAttribute(sectionPrefix + "version"));
				rga.setDescription(ga.getAttribute(sectionPrefix + "description"));	
				if (!(rga instanceof Application)) {
					rga.setApplCode(ga.getAttribute(sectionPrefix + "application"));
				}
			}
		} catch (GovernanceException e) {
			log.error(e.getMessage());
		}
		rga.setPath(path);
		return rga;
	}

}
