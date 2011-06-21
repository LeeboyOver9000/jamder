package jamder.roles;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jamder.Organization;
import jamder.behavioural.Duty;
import jamder.behavioural.Right;
import jamder.structural.Belief;
import jamder.structural.Goal;

import java.util.Hashtable;

import masml2jade.agents.GenericAgent;
import masml2jade.agents.ModelAgent;
import masml2jade.agents.ReflexAgent;

public class AgentRole extends ParallelBehaviour {

	private static final long serialVersionUID = 4509875660997626414L;
	private Organization owner;
	private String name;
	//private MAS_Agent entity; It nests the agent (entity)
	private Hashtable<String, Goal> goals = new Hashtable<String, Goal>();
	private Hashtable<String, Belief> beliefs = new Hashtable<String, Belief>();
	private Hashtable<String, Duty> duties = new Hashtable<String, Duty>();
	private Hashtable<String, Right> rights = new Hashtable<String, Right>();
	private Hashtable<String, Behaviour> protocols = new Hashtable<String, Behaviour>();
	
	// Organization
	public Organization getOwner() {
		return owner;
	}
	
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// Goals
	public Goal getGoal(String key) {
		// Reflex and Model agents do not have goals
		if (myAgent instanceof ModelAgent) {
			return null;
		}
		Goal goal = goals.get(key);
		// As AgentRole's Goal will never have plans, it is clean.
		goal.removeAllPlans();
		return goal;
	}
	public void addGoal(String key, Goal goal) {
		// Reflex and Model agents do not have goals
		if (myAgent instanceof ModelAgent) {
			return ;
		}
		
		// As AgentRole's Goal will never have plans, it is clean.
		goal.removeAllPlans();
		goals.put(key, goal);
	}
	public Goal removeGoal(String key) {
		return goals.remove(key);
	}
	public void removeAllGoals() {
		goals.clear();
	}
	public Hashtable<String, Goal> getAllGoals() {
		// Reflex and Model agents do not have goals
		if (myAgent instanceof ModelAgent) {
			return null;
		}
		
		// As AgentRole's Goal will never have plans, it is clean.
		for (Goal goal : goals.values()) {
			goal.removeAllPlans();
		}
		return goals;
	}
	
	// Beliefs
	public Belief getBelief(String key) {
		// Reflex agents do not have beliefs
		if (myAgent instanceof ReflexAgent) {
			return null;
		}
		return beliefs.get(key);
	}
	public void addBelief(String key, Belief belief) {
		// Reflex agents do not have beliefs
		if (myAgent instanceof ReflexAgent) {
			return;
		}
		beliefs.put(key, belief);
	}
	public Belief removeBelief(String key) {
		return beliefs.remove(key);
	}
	public void removeAllBeliefs() {
		beliefs.clear();
	}
	public Hashtable<String, Belief> getAllBeliefs() {
		// Reflex agents do not have beliefs
		if (myAgent instanceof ReflexAgent) {
			return null;
		}
		return beliefs;
	}
	
	// Duties
	public Duty getDuty(String key) {
		return duties.get(key);
	}
	public void addDuty(String key, Duty duty) {
		duties.put(key, duty);
	}
	public Duty removeDuty(String key) {
		return duties.remove(key);
	}
	public void removeAllDuties() {
		duties.clear();
	}
	public Hashtable<String, Duty> getAllDuties() {
		return duties;
	}
	
	// Rights
	public Right getRight(String key) {
		return rights.get(key);
	}
	public void addRight(String key, Right right) {
		rights.put(key, right);
	}
	public Right removeRight(String key) {
		return rights.remove(key);
	}
	public void removeAllRights() {
		rights.clear();
	}
	public Hashtable<String, Right> getAllRights() {
		return rights;
	}
	
	// Protocols
	public Behaviour getProtocol(String key) {
		return protocols.get(key);
	}
	public void addRight(String key, Behaviour protocol) {
		protocols.put(key, protocol);
	}
	public Behaviour removeProtocol(String key) {
		return protocols.remove(key);
	}
	public void removeAllProtocols() {
		protocols.clear();
	}
	public Hashtable<String, Behaviour> getAllProtocols() {
		return protocols;
	}
	
	public AgentRole(String name, Organization owner, GenericAgent entity) {
		super();
		this.owner = owner;
		this.name = name;
		setAgent(entity);
		// Ownership the role to the owner
		this.owner.addAgentRole(name, this);
		
		entity.addAgentRole(name, this);
	}
	
	@Override
	public void onStart() {
		// Executes all duties
		addSubBehaviour(new ParallelBehaviour(WHEN_ALL) {
			{	
				for (Duty duty : duties.values()) {
					this.addSubBehaviour(duty.getAction());
				}
			}
		} );
		
		// Executes all rights
		addSubBehaviour(new ParallelBehaviour(WHEN_ANY) {
			{
				for (Right right : rights.values()) {
					// if it is permitted
					this.addSubBehaviour(right.getAction());
				}
			}
		} );
	}
}
