package jamder.roles;

import java.util.Hashtable;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.behavioural.Duty;
import jamder.behavioural.Right;

public class AgentRole {

	private static final long serialVersionUID = 4509875660997626414L;
	protected Organization owner;
	private String name;
	protected GenericAgent player;
	private AgentRoleStatus status;
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
	
	// Player
	public GenericAgent getPlayer() {
		return player;
	}
	
	// Agent Role Status
	public AgentRoleStatus getAgentRoleStatus() {
		return status;
	}
	
	public AgentRole(String name, Organization owner, GenericAgent player) {
		this.owner = owner;
		this.name = name;
		this.player = player;
		// Ownership the role to the owner
		this.owner.addAgentRole(name, this);
		
		if (player != null) {
			if (player instanceof Organization) {
				Organization suborg = (Organization) player;
				suborg.setSuperOrganization(owner);
				suborg.getSuperOrganization().addSubOrganization(suborg.getName(), suborg);
			}
			player.addAgentRole(name, this);
			status.ACTIVATE.setStatus(true);
		}
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
	public void addProtocol(String key, Behaviour protocol) {
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
	
	public void initialize() {
		// Executes all duties
		ParallelBehaviour dutyActions = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
		for (Duty duty : this.duties.values()) {
			if (player.containAction(duty.getAction().getName())) {
				dutyActions.addSubBehaviour(duty.getAction());
			} else {
				System.out.println("This agent does not contain all mandatory duties: " + player.getName());
				return;
			}
		}

		// Executes all rights
		ParallelBehaviour rightActions = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		for (Right right : rights.values()) {
			if (player.containAction(right.getAction().getName())) {
				rightActions.addSubBehaviour(right.getAction());
			}
		}
		
		// Associate all actions to the agent or sub-organization to be played
		player.addBehaviour(dutyActions);
		player.addBehaviour(rightActions);
	}
	
	public void activeRole() {
		this.status = AgentRoleStatus.ACTIVATE;
		for (Duty duty : this.duties.values()) {
			duty.getAction().reset();
		}
		for (Right right : this.rights.values()) {
			right.getAction().reset();
		}
	}
	
	public void changeDeactiveRole(AgentRoleStatus status) {
		this.status = status;
		for (Duty duty : this.duties.values()) {
			duty.getAction().block();
		}
		for (Right right : this.rights.values()) {
			right.getAction().block();
		}
	}
	
	/*
	public void onStart() {
		// Executes all duties
		addSubBehaviour(new ParallelBehaviour(Behaviour.WHEN_ALL) {
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
	}*/
}
