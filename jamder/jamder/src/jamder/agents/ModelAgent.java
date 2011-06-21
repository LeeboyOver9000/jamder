/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.roles.AgentRole;
import jamder.structural.Belief;

import java.util.Hashtable;

/**
 * @author Administrator
 *
 */
public abstract class ModelAgent extends ReflexAgent {
	
	// Belief List
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	
	protected ModelAgent(String name, Environment environment, AgentRole agentRole, Organization organization) {
		super(name, environment, agentRole, organization);
	}
	
	@Override
	public void addAgentRole(String name, AgentRole role) {
		super.addAgentRole(name, role);
		
		// 4.2.3.1
		this.getAllBeliefs().putAll(role.getAllBeliefs());
	}
	
	// Next Function Method
	protected abstract void nextFunction() ;
	
	// Beliefs
	protected Belief getBelief(String key) {
		return beliefs.get(key);
	}
	protected void addBelief(String key, Belief belief) {
		beliefs.put(key, belief);
	}
	protected Belief removeBelief(String key) {
		return beliefs.remove(key);
	}
	protected void removeAllBeliefs() {
		beliefs.clear();
	}
	protected Hashtable<String, Belief> getAllBeliefs() {
		return beliefs;
	}
	
	// Put agent initializations here
	  protected void setup() {
		  super.setup();
		  
	  }
	
	  // Put agent clean-up operations here
	  protected void takeDown() {
		  
	  }
	  
}
