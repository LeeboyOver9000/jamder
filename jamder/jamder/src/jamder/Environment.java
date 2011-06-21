package jamder;

import jamder.behavioural.Action;

import java.util.Hashtable;

import masml2jade.agents.GenericAgent;
import jade.core.Agent;
import jade.core.PlatformID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.SimpleBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Environment extends PlatformID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	protected Hashtable<String, GenericAgent> agents = new Hashtable<String, GenericAgent>();
	private AgentContainer container;
	private String ID;
	private Profile profile;
	private Object initialResource;
	private EnvironmentConfiguration configuration;
	
	protected Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();
	protected Hashtable<String, Object> objects = new Hashtable<String, Object>();
	
	// Agents
	public GenericAgent getAgent(String key) {
		return agents.get(key);
	}
	// TODO: See the duplicated method
	public void addAgent(String key, GenericAgent agent) {
		agents.put(key, agent);
	}
	// TODO: See the duplicated method
	/*public GenericAgent removeAgent(String key) {
		return agents.remove(key);
	}*/
	public void removeAllAgents() {
		agents.clear();
	}
	public Hashtable<String, GenericAgent> getAllAgents() {
		return agents;
	}
	
	// Organizations
	public Organization getOrganization(String key) {
		return organizations.get(key);
	}
	public void addOrganization(String key, Organization organization) {
		organizations.put(key, organization);
	}
	public Organization removeOrganization(String key) {
		return organizations.remove(key);
	}
	public void removeAllOrganizations() {
		organizations.clear();
	}
	public Hashtable<String, Organization> getAllOrganizations() {
		return organizations;
	}
	
	// name is override

	public EnvironmentConfiguration getConfiguration() {
		return configuration;
	}
	
	public void setInitialResource(Object resource) {
		this.initialResource = resource;
	}
	
	public Object getInitialResource() {
		return initialResource;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public String getID() {
		return ID;
	}
	
	public AgentContainer getContainer() {
		return container;
	}

	public Action getAction(String action) {
		return this.actions.get(action);
	}

	public void sendPercept(String agent, String percept) {
		
	}

	public boolean addAgent(String name, IAgent agent) {
		if (container == null) {
			throw new IllegalStateException("Environment not initialized!");
		}
			
		try {
			AgentController ac = this.container.acceptNewAgent(name, agent);
			AgentConfiguration config = new AgentConfiguration();
			config.setAgentController(ac);
			config.setName(agent.getLocalName());
			
			ac.start();
			
			config.setAID(agent.getAID());
			
			config.setType(agent.getClass().toString());
			
			agents.put(config.getName(), config);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean addAgent(AgentConfiguration agent) {
		if (container == null) {
			throw new IllegalStateException("Environment not initialized!");
		}
			
		try {
			AgentController ac = this.container.createNewAgent(agent.getName(), agent.getType(), agent.getArguments());
			agent.setAgentController(ac);
			ac.start();
			
			agents.put(agent.getName(), agent);
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeAgent(String name) {
		AgentController ac = agents.get(name).getAgentController();
		try {
			ac.kill();
			agents.remove(name);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void addAction(String id, Action action) {
		this.actions.put(id, action);
	}

	public Action removeAction(String id) {
		return this.actions.remove(id);
	}

	public void makeEnvironment(EnvironmentConfiguration config, boolean isMainContainer, Object resource) {
		Runtime rt = Runtime.instance();
		try {
			Profile profile = null;
			this.setEnabledO2ACommunication(true, 0);
			this.setO2AManager(new ReceiveResourceBehavior(this));
			this.profile = profile;
			this.ID = config.getID();
			profile = new ProfileImpl(config.getHost(), config.getPort(), config.getID());
			
			if (!isMainContainer){
				this.container = rt.createAgentContainer(profile);
				AgentController environment = container.acceptNewAgent(this.ID, this);
				environment.start();
				environment.putO2AObject(resource, false);
			} else {
				this.container = rt.createMainContainer(profile);
			    AgentController rma = container.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);			    
			    rma.start();   
			}
			
			configuration = config;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new ExecuteEnvironmentAction(this, 100));
		initialize();
	}
	
	public void initialize() {
		//implements in children classes
	}
	
	  // Simple class behaving as a Condition Variable
	public static class CondVar {
		private boolean value = false;

		synchronized void waitOn() throws InterruptedException {
			while (!value) {
				wait();
			}
		}

		synchronized void signal() {
			value = true;
			notifyAll();
		}

	} // End of CondVar class
	
}


class ReceiveResourceBehavior extends SimpleBehaviour {
	private Agent agent;
	private Object resource;

	public ReceiveResourceBehavior(Agent a) {
		this.agent = a;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5662378089326169535L;

	@Override
	public void action() {
		resource = this.agent.getO2AObject();
		((Environment)this.agent).setInitialResource(resource);
	}

	@Override
	public boolean done() {
		if (resource != null) {
			return true;
		} else {
			return false;
		}
	}
}

