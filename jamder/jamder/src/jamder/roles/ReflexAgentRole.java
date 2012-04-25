package jamder.roles;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jamder.Organization;
import jamder.agents.GenericAgent;
import jamder.behavioural.Duty;
import jamder.behavioural.Right;

import java.util.Hashtable;

public class ReflexAgentRole extends AgentRole {

	private static final long serialVersionUID = 4509875660997626414L;
	private String name;
	//private MAS_Agent entity; It nests the agent (entity)

	
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

	
	public ReflexAgentRole(String name, Organization owner, GenericAgent player) {
		super(name, owner, player);
		initialize();
	}
	
	public void initialize() {
		// Executes all duties
		ParallelBehaviour dutyActions = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
		for (Duty duty : getAllDuties().values()) {
			if (player.containAction(duty.getAction().getName())) {
				dutyActions.addSubBehaviour(duty.getAction());
			} else {
				System.out.println("This agent does not contain all mandatory duties: " + player.getName());
				return;
			}
		}

		// Executes all rights
		ParallelBehaviour rightActions = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		for (Right right : getAllRights().values()) {
			if (player.containAction(right.getAction().getName())) {
				rightActions.addSubBehaviour(right.getAction());
			}
		}
		
		// Associate all actions to the agent or sub-organization to be played
		player.addBehaviour(dutyActions);
		player.addBehaviour(rightActions);
	}
}
