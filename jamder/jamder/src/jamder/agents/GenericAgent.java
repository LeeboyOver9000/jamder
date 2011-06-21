package jamder.agents;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import jamder.Environment;
import jamder.Organization;
import jamder.behavioural.Action;
import jamder.behavioural.Duty;
import jamder.behavioural.Sensor;
import jamder.roles.AgentRole;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


public abstract class GenericAgent extends Agent {
	
	private Hashtable<String, AgentRole> agentRoles = new Hashtable<String, AgentRole>();
	private Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();
	private Environment environment ;
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	
	protected GenericAgent(String name, Environment environment, AgentRole agentRole, Organization organization) {
		this.environment = environment;
		this.environment.addAgent(name, this);
		
		organizations.put(organization.getName(), organization);
		this.doMove(organization.getContainerID());
		
		agentRoles.put(agentRole.getName(), agentRole);
		
		// All agents must perceive something
		Sensor perception = new Sensor(this, 1000L);
		addBehaviour(perception);
	}
	
	// Environment
	public Environment getEnvironment() {
		return environment;
	}
	/*public void setEnvironment(Environment environment) {
		this.environment = environment;
	}*/
	
	// AgentRoles
	public AgentRole getAgentRole(String name) {
		return agentRoles.get(name);
	}
	public void addAgentRole(String name, AgentRole role) {
		boolean found = false;
		for (Duty duty : role.getAllDuties().values()) {
			String actionDuty = duty.getAction().getClass().getName();
			for (Action actionAgent : getAllActions().values()) {
				if (actionAgent.getClass().getName().equals(actionDuty)) {
					found = true;
					break;
				}
			}
			if (!found){
				System.out.println("This agent " + getName() 
						+ " does not contain any action defined for the role " 
						+ name);
				break;
			}
		}
		if (found) {
			agentRoles.put(name, role);
		}
	}
	public AgentRole removeAgentRole(String name) {
		// When the agent don't have the role, then this behaviour is deleted from
		removeBehaviour(getAgentRole(name));
		return agentRoles.remove(name);
	}
	public void removeAllAgentRoles() {
		// When the agent don't have the role, then this behaviour is deleted from
		for (AgentRole role : agentRoles.values()) {
			removeBehaviour(role);
		}
		agentRoles.clear();
	}
	public Hashtable<String, AgentRole> getAllAgentRoles() {
		return agentRoles;
	}
	
	// Organizations
	protected Organization getOrganization(String key) {
		return organizations.get(key);
	}
	protected void addOrganization(String key, Organization organization) {
		organizations.put(key, organization);
	}
	protected Organization removeOrganization(String key) {
		return organizations.remove(key);
	}
	protected void removeAllOrganizations() {
		organizations.clear();
	}
	protected Hashtable<String, Organization> getAllOrganizations() {
		return organizations;
	}
	
	// Actions
	protected Action getAction(String key) {
		return actions.get(key);
	}
	protected void addAction(String key, Action action) {
		actions.put(key, action);
	}
	protected Action removeAction(String key) {
		return actions.remove(key);
	}
	protected void removeAllActions() {
		actions.clear();
	}
	protected Hashtable<String, Action> getAllActions() {
		return actions;
	}

	// Put agent initializations here
	protected void setup() {
		super.setup();
		this.setEnabledO2ACommunication(true, 0);

		// Ambiente
		this.getAID().getName(); // PlatformID

		// Organizaï¿½ï¿½o
		this.getAID().getName(); // ContainerID

		// Papel de Agente (muda crenï¿½as e objetivos dos agentes)
		// Algum behaviour

		// Agente Reativo simples, possui somente comportamento

	}

	// Put agent clean-up operations here
	protected void takeDown() {

	}
	
	@Override
	protected void beforeMove() {
		super.beforeMove();
		// When the agent is moved, its behaviours for this organization are blocked.
		Set<AgentRole> set = this.getBehaviours();
		Iterator<AgentRole> iterator = set.iterator(); 
		while(iterator.hasNext()) {
			iterator.next().block();
		}
	}
	
	@Override
	public void doMove(Location location) {
		// Checks if agent contains this location to move
		if (location instanceof ContainerID) {
			boolean containsOrg = false;
			for (Organization organization : organizations.values()) {
				if (organization.getContainerID().getID() == location.getID()) {
					containsOrg = true;
					break;
				}
			}
			
			if (containsOrg) {
				super.doMove(location);	
			} else {
				System.out.println("This Agent cannot move to this organization.");
				return;
			}
		}
		
		// If location is PlatformID, then it can move
		this.removeAllOrganizations();
		super.doMove(location);	
		// TODO: adicionar os papéis da organização principal e a própria
	}
	
	@Override
	protected void afterMove() {
		super.afterMove();
		
		// If the agent is in the same organization of role, the agent will be reseted
		// but this role must be blocked before.
		Set<AgentRole> set = this.getBehaviours();
		Iterator<AgentRole> iterator = set.iterator(); 
		while(iterator.hasNext()) {
			AgentRole role = iterator.next();
			if (here().getID().equals(role.getOwner().getContainerID().getID())) {
				role.reset();
			}
		}
		
		/*String containerName = null;
		try {
			containerName = this.getContainerController().getContainerName();
		} catch (ControllerException e) {
			e.printStackTrace();
		}*/
	}
	
	public abstract void percept(String perception) ;
	
}
