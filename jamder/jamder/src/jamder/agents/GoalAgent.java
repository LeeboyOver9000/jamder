/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Plan;
import jamder.roles.AgentRole;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import masml2jade.behaviour.ReativoBehaviour;

import jade.core.Agent;

/**
 * @author Administrator
 *
 */
public abstract class GoalAgent extends ModelAgent {
	
	// Goal List
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	
	protected GoalAgent(String name, Environment environment, AgentRole agentRole, Organization organization) {
		super(name, environment, agentRole, organization);
		
	}
	
	@Override
	public void addAgentRole(String name, AgentRole role) {
		super.addAgentRole(name, role);
		
		// 3.1.5
		this.getAllGoals().putAll(role.getAllGoals());
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
	  protected abstract void formulateProblemFunction(Belief belief, Goal goal);
	  
	  protected abstract Plan planning();
	  
}
