package at.rbg.registry.integration.service.model;


public class Subscription {
	private ModelComposite consumer;
	private ModelComposite provider;
	
	public ModelComposite getConsumer() {
		return consumer;
	}
	public void setConsumer(ModelComposite consumer) {
		this.consumer = consumer;
	}
	public ModelComposite getProvider() {
		return provider;
	}
	public void setProvider(ModelComposite provider) {
		this.provider = provider;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((consumer == null) ? 0 : consumer.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscription other = (Subscription) obj;
		if (consumer == null) {
			if (other.consumer != null)
				return false;
		} else if (!consumer.equals(other.consumer))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	
}
