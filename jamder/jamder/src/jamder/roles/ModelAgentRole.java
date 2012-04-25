package jamder.roles;

import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.agents.ReflexAgent;
import jamder.structural.Belief;

import java.util.Hashtable;

public class ModelAgentRole extends ReflexAgentRole {

	private static final long serialVersionUID = 4509875660997626414L;
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	
	// Beliefs
	public Belief getBelief(String key) {
		// Reflex agents do not have beliefs
		if (player instanceof ReflexAgent) {
			return null;
		}
		return beliefs.get(key);
	}
	public void addBelief(String key, Belief belief) {
		// Reflex agents do not have beliefs
		if (player instanceof ReflexAgent) {
			return;
		}
		beliefs.put(key, belief);
	}
	public Belief removeBelief(String key) {
		return beliefs.remove(key);
	}
	public void removeAllBeliefs() {
		beliefs.clear();
	}
	public Hashtable<String, Belief> getAllBeliefs() {
		// Reflex agents do not have beliefs
		if (player instanceof ReflexAgent) {
			return null;
		}
		return beliefs;
	}
	
	public ModelAgentRole(String name, Organization owner, GenericAgent player) {
		super(name, owner, player);
	}

}
