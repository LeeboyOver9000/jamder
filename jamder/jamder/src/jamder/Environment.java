package jamder;

import jamder.behavioural.Action;
import jamder.roles.AgentRole;

import java.util.Enumeration;
import java.util.Hashtable;

import jamder.agents.GenericAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.PlatformID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.mtp.TransportAddress;
import jade.mtp.http.MessageTransportProtocol;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.State;

public class Environment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	protected Hashtable<String, GenericAgent> agents = new Hashtable<String, GenericAgent>();
	private AgentContainer container;
	private PlatformID ID;
	private Profile profile;
	private Object initialResource;
	private EnvironmentConfiguration configuration;
	
	protected Hashtable<String, Organization> organizations = new Hashtable<String, Organization>();
	protected Hashtable<String, Object> objects = new Hashtable<String, Object>();
	
	public Environment(String name, String host, String port){
		try {
			Runtime rt = Runtime.instance();
			Profile profile = new ProfileImpl(host, Integer.valueOf(port), name);
			this.profile = profile;
			this.container = rt.createMainContainer(profile);
		    AgentController rma = container.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);			    
		    rma.start(); 
		    
		    // TODO: Associar o PlatformID
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	    
	    /*
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
		}*/
	}
	
	// Objects
	protected Object getObject(String key) {
		return objects.get(key);
	}
	protected void addObject(String key, Object object) {
		objects.put(key, object);
	}
	protected Object removeObject(String key) {
		return objects.remove(key);
	}
	protected void removeAllObjects() {
		objects.clear();
	}
	protected Hashtable<String, Object> getAllObjects() {
		return objects;
	}
	
	// Agents
	public GenericAgent getAgent(String key) {
		return agents.get(key);
	}
	// TODO: See the duplicated method
	public void addAgent(String key, GenericAgent agent) {
		try {
			if (agent instanceof Organization) {
				return;
			}
			
			AgentRole role = agent.getAllAgentRoles().values().iterator().next();
			
			/*AgentController aControl = role.getOwner().getContainerController()
				.createNewAgent(agent.getName(), agent.getClass().toString(), null);*/
			AgentController aControl = role.getOwner().getAgentContainer()
			.acceptNewAgent(key, agent);
			if (!aControl.getState().getName().equals("Active")) {
				aControl.start();
			}
			
			agents.put(key, agent);
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
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
		Runtime rt = Runtime.instance();
		try {
			String host = this.profile.getSpecifiers(Profile.MAIN_HOST).get(0).toString();
			String port = this.profile.getSpecifiers(Profile.MAIN_PORT).get(0).toString();
			Profile profile = new ProfileImpl(host, Integer.valueOf(port), key);
			profile.setParameter(Profile.MAIN, "false");
			AgentContainer ac = rt.createAgentContainer(profile);
			ContainerID cId = new ContainerID(key, null);
			cId.setAddress("localhost");
			cId.setMain(false);
			cId.setPort("8888");
			
			organization.setContainerID(cId);
			organization.setAgentContainer(ac);
			/*(MessageTransportProtocol)profile.getSpecifiers(Profile.MTPS).get(0);
			TransportAddress ta = new*/  
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*AgentController ac = container.acceptNewAgent(key, this);
		ac.start();*/
		//ac.putO2AObject(resource, false);
	}
	
	public void removeOrganization(String key) {
		try {
			Organization org = organizations.get(key);
			
			// Delete all subOrganizations
			if (!org.getAllSubOrganizations().isEmpty() && org.getAllSubOrganizations().size() > 0) {
				Enumeration<String> subOrgs = org.getAllSubOrganizations().keys();
				while (subOrgs.hasMoreElements()) {
					String subOrg = (String) subOrgs.nextElement();
					removeOrganization(subOrg);
				}
			}
			
			Enumeration<String> orgRoleNames = org.getAllAgentRoles().keys();
			AgentRole orgRole = null;
			while (orgRoleNames.hasMoreElements()) {
				String orgRoleName = (String) orgRoleNames.nextElement();
				orgRole = org.getAgentRole(orgRoleName);
				GenericAgent agent = orgRole.getPlayer();
				
				Enumeration<String> aRoleNames = agent.getAllAgentRoles().keys();
				AgentRole aRole = null;
				boolean hasOtherOrgs = false;
				
				// Cancel the role with agents 
				while (aRoleNames.hasMoreElements()) {
					String aRoleName = (String) aRoleNames.nextElement();
					aRole = agent.getAgentRole(aRoleName);
					if (aRole.getOwner() != org) {
						aRole.getOwner().getContainerController().acceptNewAgent(agent.getName(), agent);
						hasOtherOrgs = true;
						break;
					}
				}
				
				// When agent have not other organizations, it will be deleted
				if (!hasOtherOrgs) {
					removeAgent(agent.getName());
				}
				
				// Removes the instance of AgentRole
				org.removeAgentRole(orgRoleName);
			}
			
			// Delete the container on JADE and JAMDER
			org.getContainerController().kill();
			organizations.remove(key);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	
	public PlatformID getID() {
		return ID;
	}
	
	public AgentContainer getContainer() {
		return container;
	}

	public Action getAction(String action) {
		return this.actions.get(action);
	}

	public void sendPercept(AID agent, String percept, String value) {
		
	}

	/*public boolean addAgent(String name, IAgent agent) {
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
	}*/
	
	/*public boolean addAgent(AgentConfiguration agent) {
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
	}*/

	public void removeAgent(String name) {
		try {
			GenericAgent agent = agents.get(name);
			Enumeration<String> roles = agent.getAllAgentRoles().keys();
			AgentRole role = null;
			while (roles.hasMoreElements()) {
				String roleName = (String) roles.nextElement();
				role = (AgentRole) agent.getAgentRole(roleName);
				role.getOwner().removeAgentRole(roleName);
			}
			
			AgentContainer ac = agent.getContainerController();
			ac.getAgent(name).kill();
			// agents.get(name).doDelete();
			agents.remove(name);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAction(String id, Action action) {
		this.actions.put(id, action);
	}

	public Action removeAction(String id) {
		return this.actions.remove(id);
	}

	/*public void makeEnvironment(EnvironmentConfiguration config, boolean isMainContainer, Object resource) {
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
	}*/

	/*protected void setup() {
		super.setup();
		addBehaviour(new ExecuteEnvironmentAction(this, 100));
		initialize();
	}*/
	
	
	
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



