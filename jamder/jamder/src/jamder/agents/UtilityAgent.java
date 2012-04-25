/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.roles.AgentRole;
import jamder.roles.ProactiveAgentRole;

/**
 * @author Administrator
 *
 */
public abstract class UtilityAgent extends GoalAgent {
	
	protected UtilityAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
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
