package at.rbg.registry.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.wso2.carbon.registry.api.Association;

import at.rbg.registry.communication.RegistryFactory;
import at.rbg.registry.communication.RegistryInteraction;
import at.rbg.registry.communication.query.LateFilteringSupport;
import at.rbg.registry.communication.query.ArtifactDTO;
import at.rbg.registry.communication.query.QueryBuilder;
import at.rbg.registry.communication.query.QueryRequest;
import at.rbg.registry.communication.query.QueryResponse;
import at.rbg.registry.integration.service.model.ModelComposite;
import at.rbg.registry.integration.service.model.Subscription;
import at.rbg.registry.model.Application;
import at.rbg.registry.model.ApplicationService;
import at.rbg.registry.model.Key;
import at.rbg.registry.model.Module;
import at.rbg.registry.model.RBGGovernanceArtifact;
import at.rbg.registry.model.Table;
 
public class RegistryReadingProcessor implements Processor {
	private static final Logger log = LoggerFactory
			.getLogger(RegistryWritingProcessor.class);
	
	private RegistryInteraction registryInteraction;
	private RegistryFactory registryFactory;

	public void setRegistryInteraction(RegistryInteraction registryInteraction) {
		this.registryInteraction = registryInteraction;
	}

	public void setRegistryFactory(RegistryFactory registryFactory) {
		this.registryFactory = registryFactory;
	}

	@Override
	public void process(Exchange ex) throws Exception {
		registryFactory.checkAlive();

		StopWatch stopWatch = new StopWatch("RegistryReadingProcessor");
		if (log.isDebugEnabled()) {
			stopWatch.start("retrieve");
		}

		QueryRequest qreq = (QueryRequest) ex.getIn().getBody();
		QueryResponse qresp = new QueryResponse();
		List<Subscription> consuming = new ArrayList<Subscription>();
		List<Subscription> providing = new ArrayList<Subscription>();
		
		if (qreq.getRole()!=null) {
			Key role = qreq.getRole();
			// get consumer side  svz EAMP003
			if ("0".equals(role.getCode())) {
                List<String> assocs = new ArrayList<String>();
                assocs.add("calls");
                assocs.add("uses");
                consuming = buildSubscriptions(qreq, assocs);
			}
			// get provider side
			if ("1".equals(role.getCode())) {
	            List<String> assocs = new ArrayList<String>();
                assocs.add("isCalled");
                assocs.add("isUsed");
                providing = buildSubscriptions(qreq, assocs);
			}
		}
		// get both sides (consumer/provider)
		else {
            List<String> assocs = new ArrayList<String>();
            assocs.add("calls");
            assocs.add("uses");
            List<String> revAssoc = new ArrayList<String>();
            revAssoc.add("isCalled");
            revAssoc.add("isUsed");
            consuming = buildSubscriptions(qreq, assocs);
            providing = buildSubscriptions(qreq, revAssoc);
		}
		
		qresp.addSubscriptions(consuming);
		qresp.addSubscriptions(providing);
		
		if (log.isDebugEnabled()) {
			stopWatch.stop();
			log.info(stopWatch.prettyPrint());
		}
		ex.getOut().setBody(qresp);
		
	}

	private List<Subscription> buildSubscriptions(QueryRequest qreq, Collection<String> role) {
		List<Subscription> retval = new ArrayList<Subscription>();
		
		String query = QueryBuilder.getQueryString(qreq, role);
		List<String> paths = registryInteraction.findArtifactPaths(query);

		List<ArtifactDTO> consumers = registryInteraction
				.getGenericArtifactsforPaths(paths);
		// apply late filtering for application-service version
		consumers = LateFilteringSupport.applyApplicationServiceFilter(consumers,qreq);
		
		List<Association> consAssocs = filterAssocs(consumers, "isContained");
				// registryInteraction.getAssociationsforPaths(paths, "isContained", true);

		List<Association> currResp = filterAssocs(consumers, "isResponsible");
		Set<Association> allResp = new HashSet<Association>(currResp);

		// these are the outgoing calls/isCalled
		List<Association> callees = registryInteraction
				.getAssociationsforPaths(paths, role, true);
		List<String> otherPaths = new ArrayList<String>();
		for (Association aa : callees) {
			otherPaths.add(aa.getDestinationPath());
		}
		// dedupe
		otherPaths = new ArrayList<String>(new HashSet<String>(otherPaths));
		List<ArtifactDTO> consumees = registryInteraction.getGenericArtifactsforPaths(otherPaths);
		List<Association> consumeesAssocs = filterAssocs(consumees, "isContained");
				//registryInteraction.getAssociationsforPaths(otherPaths, "isContained", true);

		currResp = filterAssocs(consumers, "isResponsible");
		allResp.addAll(currResp);
		
		// concat all known
		List<RBGGovernanceArtifact> allArtifacts = new ArrayList<RBGGovernanceArtifact>();
		for (ArtifactDTO ad : consumers) {
			allArtifacts.add(ad.getArtifact());
		}
		for (ArtifactDTO ad : consumees) {
			allArtifacts.add(ad.getArtifact());
		}
		List<Association> allAssocs = new ArrayList<Association>(consAssocs);
		allAssocs.addAll(consumeesAssocs);
		
		// collect all applcodes
		Set<String> applCodes = new HashSet<String>();
		for (RBGGovernanceArtifact r : allArtifacts) {
			if (r.getApplCode()!=null) {
				applCodes.add(r.getApplCode());
			}
		}
		
		// collect unique artifacts from assocs
		Set<String> unifiedAssocs = unifyLists(allAssocs, allArtifacts);
		// get module layer
		List<ArtifactDTO> moduleContainers = registryInteraction.getGenericArtifactsforPaths(unifiedAssocs);
		// get application layer
		List<Association> applAssocs =  filterAssocs(moduleContainers, "isContained");
				//registryInteraction.getAssociationsforPaths(unifiedAssocs,"isContained", true);

		currResp = filterAssocs(consumees, "isResponsible");
		allResp.addAll(currResp);
		
		List<String> applPaths = new ArrayList<String>();
		for (Association aa : applAssocs) {
			applPaths.add(aa.getDestinationPath());
		}
		List<ArtifactDTO> applContainers =  registryInteraction.getGenericArtifactsforPaths(applPaths);
		currResp = filterAssocs(consumers, "isResponsible");
		allResp.addAll(currResp);

		// find missing applications (bec. module layer is not assigned correctly!)		
		Set<String> haveApplCodes = new HashSet<String>();
		for (ArtifactDTO ad : applContainers) {
			if (ad.getArtifact().getApplCode()!=null) {
				haveApplCodes.add(ad.getArtifact().getApplCode());
			}
		}
		applCodes.removeAll(haveApplCodes);
		if (applCodes.size()>0) {
			String queryAppl = QueryBuilder.getApplicationsQueryForCodes(applCodes);
			List<String> missingApplPaths = registryInteraction.findArtifactPaths(queryAppl);
			List<ArtifactDTO> missingApplArtifacts = registryInteraction.getGenericArtifactsforPaths(missingApplPaths);
			applContainers.addAll(missingApplArtifacts);
		}
		// handle responsible relations
		Set<String> respPaths = new HashSet<String>();
		for (Association a : allResp) {
			respPaths.add(a.getDestinationPath());
		}
		// TODO: process responsibles into "owner" field
		@SuppressWarnings("unused")
		List<ArtifactDTO> responsibles = registryInteraction.getGenericArtifactsforPaths(respPaths);	
		
		for (ArtifactDTO ad : consumers) {
			RBGGovernanceArtifact a = ad.getArtifact(); 
			// prepare the list of outgoing assocs for this artifact
			Iterator<Association> it = callees.iterator();
			List<Association> forAssocs = new ArrayList<Association>();
			while( it.hasNext() ) {
				Association ass = it.next();
				// we want to process these in this for iteration
			  if( ass.getSourcePath().equals(a.getPath()) ) {
				  forAssocs.add(ass);
				  it.remove();
			  }
			}
			for (Association as : forAssocs) {
				Subscription s = new Subscription();
				
				ModelComposite mcons = new ModelComposite();
	            if(a instanceof Table) {
	                mcons.setTable((Table)a);
	            }
	            else {
	                mcons.setApplicationService((ApplicationService)a);
	            }
				RBGGovernanceArtifact mod = findOther(a.getPath(), consAssocs, moduleContainers);
				if (mod != null) {
					mcons.setModule((Module) mod);
					RBGGovernanceArtifact appl = findOther(mod.getPath(), applAssocs, applContainers);			
					mcons.setApplication((Application) appl);
				}
				// try via applCode!
				else {
					RBGGovernanceArtifact appl = findApplicationByCode(a.getApplCode(), applContainers);			
					mcons.setApplication((Application) appl);
				}
				
				// skip this one, if criteria do not macch
				if (!LateFilteringSupport.accept(qreq, mcons)) {
					continue;
				}
	            if(role.contains("calls")) {
					s.setConsumer(mcons);
				}
				else {
					s.setProvider(mcons);
				}
				
	            String otherPath = as.getDestinationPath();
	            if (otherPath.equals(a.getPath())) {
	            	// it is the other side of the assoc we do not know about yet!
	            	otherPath = as.getSourcePath();
	            }
	            
	            RBGGovernanceArtifact otherService = null;
				ModelComposite mprov = new ModelComposite();
				for (ArtifactDTO adt : consumees) {
					if (otherPath.equals(adt.getArtifact().getPath())) {
						otherService = adt.getArtifact();
						break;
					}
				}
	            if(otherService instanceof Table) {
	                mprov.setTable((Table)otherService);
	            }
	            else {
	                mprov.setApplicationService((ApplicationService)otherService);
	            }
				RBGGovernanceArtifact modOther = null;
				if (otherService!=null) {
					modOther = findOther(otherService.getPath(), consumeesAssocs, moduleContainers);
					if (modOther!= null) {
						mprov.setModule((Module) modOther);
						RBGGovernanceArtifact applOther = findOther(modOther.getPath(), applAssocs, applContainers);			
						mprov.setApplication((Application) applOther);
					}
					// try via applCode!
					else {
						RBGGovernanceArtifact applOther = findApplicationByCode(otherService.getApplCode(), applContainers);			
						mprov.setApplication((Application) applOther);
					}
				}
	            if(role.contains("calls")) {
					s.setProvider(mprov);
				}
				else {
					s.setConsumer(mprov);
				}				
				retval.add(s);
			}

		}		
		return retval;
	}

	private List<Association> filterAssocs(List<ArtifactDTO> consumers, String type) {
		List<Association> retval = new ArrayList<Association>();
		for (ArtifactDTO ad: consumers) {
			for (Association a : ad.getAssocs()) {
				if (type.equals(a.getAssociationType())) {
					retval.add(a);					
				}
			}
		}
		return retval;
	}

	private RBGGovernanceArtifact findOther(String path, List<Association> assocs, List<ArtifactDTO> arts) {
		String targetPath = null;
		for (Association a : assocs) {
			if (path.equals(a.getSourcePath())) {
				targetPath = a.getDestinationPath();
				break;
			}
		}
		if (targetPath!=null) {
			for (ArtifactDTO ad : arts) {
				if (targetPath.equals(ad.getArtifact().getPath())) {
					return ad.getArtifact();
				}
			}
		}
		return null;
	}

	private RBGGovernanceArtifact findApplicationByCode(String code, List<ArtifactDTO> arts) {
		for (ArtifactDTO ad : arts) {
			if(ad.getArtifact().getApplCode().equals(code)) {
				return ad.getArtifact();
			}
		}
		return null;
	}
	
	private Set<String> unifyLists(List<Association> assocs,
			 List<RBGGovernanceArtifact> artifacts) {
		
		Set<String> retval = new HashSet<String>();
		// collect existing elements
		Set<String> existing = new HashSet<String>();
		for (RBGGovernanceArtifact artifact: artifacts) {
			existing.add(artifact.getPath());
		}
		// these are to be resolved yet
		for (Association a : assocs) {
			retval.add(a.getSourcePath());
			retval.add(a.getDestinationPath());
		}

		retval.removeAll(existing);		
		return retval;
	}
}
