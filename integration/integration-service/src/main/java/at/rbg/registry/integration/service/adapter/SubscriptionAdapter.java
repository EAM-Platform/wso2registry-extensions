package at.rbg.registry.integration.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.racon.eamp.eamp_types_1.Application;
import at.racon.eamp.eamp_types_1.ApplicationService;
import at.racon.eamp.eamp_types_1.Module;
import at.racon.eamp.eamp_types_1.Subscription;
import at.rbg.registry.integration.service.model.ModelComposite;

public class SubscriptionAdapter {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ApplicationAdapter.class);

	public static Subscription adapt(at.rbg.registry.integration.service.model.Subscription sub) {
		Subscription retval = new Subscription();
		ApplicationService cons = convertModelComposite(sub.getConsumer());
		retval.setConsumer(cons);
		ApplicationService prov = convertModelComposite(sub.getProvider());
		retval.setProvider(prov);		
		return retval;
	}

	private static ApplicationService convertModelComposite(ModelComposite m) {
		Module mod = ModuleAdapter.adapt(m.getModule());
		Application app = ApplicationAdapter.adapt(m.getApplication());		
		mod.setApplication(app);	
		ApplicationService as = new ApplicationService();
		if (m.getApplicationService()!=null) {
			as = ApplicationServiceAdapter.adapt(m.getApplicationService());
		}
		else if (m.getTable()!=null) {
			as = TableAdapter.adapt(m.getTable());
		}
		as.setModule(mod);	
		return as;
	}
}
