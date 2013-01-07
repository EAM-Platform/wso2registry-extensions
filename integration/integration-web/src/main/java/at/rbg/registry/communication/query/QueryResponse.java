package at.rbg.registry.communication.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.rbg.registry.integration.service.model.Subscription;

public class QueryResponse {
	private Set<Subscription> subscriptions = new HashSet<Subscription>();
	
	public void addSubscription(Subscription sub) {
		this.subscriptions.add(sub);
	}
	
	public void addSubscriptions(List<Subscription> sub) {
		this.subscriptions.addAll(sub);
	}

	public List<Subscription> getSubscriptions() {
		return new ArrayList<Subscription>(subscriptions);
	}
}
