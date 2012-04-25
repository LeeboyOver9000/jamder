/**
 * 
 */
package jamder.agents;

import jamder.Environment;
import jamder.behavioural.Action;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;

import java.util.Hashtable;


/**
 * @author Yrleyjander Salmito
 * 
 */
public abstract class ReflexAgent extends GenericAgent {
	
	Sensor perception = new Sensor(this, 1000L);

	// Perceive List
	protected Hashtable<String, Action> perceives = new Hashtable<String, Action>();
	//chamar método do ambiente para pegar o que ele quer.
	// hashtable de name e value 
	//criar um comportamento que chama o ambiente de em tempo em tempo

	// Precondition List
	// na verdade é uma lista de açoes que tem pre e pos condicoes
	
	protected ReflexAgent(String name, Environment environment, AgentRole agentRole) {
		super(name, environment, agentRole);
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
		super.setup();
		
		// Papel de agente
		// Agente Reativo simples, possui somente comportamento

	}

	// Put agent clean-up operations here
	protected void takeDown() {

	}

	public void percept(String perception) {
		Action action = perceives.get(perception);
		/*if (action != null && action.length() >= 1) {
			
		}*/
	}
}
