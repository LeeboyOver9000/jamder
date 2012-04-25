/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Plan;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.Hashtable;


/**
 * @author Administrator
 *
 */
public abstract class MASMLAgent extends GenericAgent {
	
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	
	protected MASMLAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
		if (agentRole != null) {
			// 4.2.3.1
			this.getAllBeliefs().putAll(((ProactiveAgentRole)agentRole).getAllBeliefs());
			// 3.1.5
			this.getAllGoals().putAll(((ProactiveAgentRole)agentRole).getAllGoals());
		}
	}
	
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
	
	// Goals
	protected Goal getGoal(String key) {
		return goals.get(key);
	}
	protected void addGoal(String key, Goal goal) {
		goals.put(key, goal);
	}
	protected Goal removeGoal(String key) {
		return goals.remove(key);
	}
	protected void removeAllGoals() {
		goals.clear();
	}
	protected Hashtable<String, Goal> getAllGoals() {
		return goals;
	}
	
	// Plans
	public Plan getPlan(String key) {
		return plans.get(key);
	}
	public void addPlan(String key, Plan plan) {
		plans.put(key, plan);
	}
	public Plan removePlan(String key) {
		return plans.remove(key);
	}
	public void removeAllPlans() {
		plans.clear();
	}
	public Hashtable<String, Plan> getAllPlans() {
		return plans;
	}
	
	public void addAgentRole(String name, ProactiveAgentRole role) {
		super.addAgentRole(name, role);
		
		// 4.2.3.1
		this.getAllBeliefs().putAll(role.getAllBeliefs());
		// 3.1.5
		this.getAllGoals().putAll(role.getAllGoals());
	}
	
	// Put agent initializations here
	  protected void setup() {
		  // Papel de agente
		  
	  }
	
	  // Put agent clean-up operations here
	  protected void takeDown() {
		  
	  }
	  
	  final public void percept(String perception) {
			// This method only will be used by ReflexAgents, ModelAgent, GoalAgent and UtilityAgent 
	  }
  
}
