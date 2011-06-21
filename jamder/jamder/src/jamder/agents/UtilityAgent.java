/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Plan;
import jamder.roles.AgentRole;
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
public abstract class UtilityAgent extends GoalAgent {
	
	protected UtilityAgent(String name, Environment environment, AgentRole agentRole, Organization organization) {
		super(name, environment, agentRole, organization);
	}
	
	// Integer é o grau de prioridade da ação
	protected abstract Integer utilityFunction(Action action) ;
	
	// Put agent initializations here
	  protected void setup() {
		  // Papel de agente
		  
	  }
	
	  // Put agent clean-up operations here
	  protected void takeDown() {
		  
	  }
}
