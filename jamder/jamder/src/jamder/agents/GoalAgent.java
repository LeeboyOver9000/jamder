/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.*;

import java.util.Hashtable;
import java.util.List;

/**
 * @author Administrator
 *
 */
public abstract class GoalAgent extends GenericAgent {
	
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	// Perceive List
	protected Hashtable<String, Action> perceives = new Hashtable<String, Action>();
	Sensor perception = new Sensor(this, 1000L);
	
	protected GoalAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
	}
	
	public void addAgentRole(String name, ProactiveAgentRole role) {
		super.addAgentRole(name, role);
		
		// 4.2.3.1
		if (role.getAllBeliefs() != null)
		this.getAllBeliefs().putAll(role.getAllBeliefs());
		
		// 3.1.5
		if (role.getAllGoals() != null)
		this.getAllGoals().putAll(role.getAllGoals());
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
	
	// Perceives
	protected Action getPerceive(String perceive) {
		return perceives.get(perceive);
	}
	protected void addPerceive(String perceive, Action action) {
		perceives.put(perceive, action);
	}
	protected Action removePerceive(String perceive) {
		return perceives.remove(perceive);
	}
	protected void removeAllPerceives() {
		perceives.clear();
	}
	protected Hashtable<String, Action> getAllPerceives() {
		return perceives;
	}
	
	// Put agent initializations here
	  protected void setup() {
		  // Papel de agente
		 
	  }
	
	  // Put agent clean-up operations here
	  protected void takeDown() {
		  
	  }
	  // recebe o estado (crenca) e retorna o objetivo formulado
	  protected abstract Goal formulateGoalFunction(Belief belief) ;
	  
	  // recebe o estado (crenca) e o objetivo e retorna o problema
	  protected abstract List<Action> formulateProblemFunction(Belief belief, Goal goal);
	  
	  protected abstract Plan planning(List<Action> actions);
	  
	// Next Function Method
	protected abstract Belief nextFunction(Belief belief, String perception) ;
	
	protected void setSensor(Sensor perception) {
		this.perception = perception;
	}
	
	public void percept(String perception) {
		Action action = perceives.get(perception);
		/*if (action != null && action.length() >= 1) {
			
		}*/
	}
	  
}
