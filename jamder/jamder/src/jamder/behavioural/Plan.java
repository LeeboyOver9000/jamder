package jamder.behavioural;

import jade.core.behaviours.SequentialBehaviour;
import jamder.structural.Goal;

import java.util.Hashtable;

import jamder.agents.GenericAgent;

public class Plan extends SequentialBehaviour{
	private static final long serialVersionUID = 1L;
	
	private String name;

	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	// TODO: Pq precisa ter goal, ja que goal tem plan
	private Goal goal;
	
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// Actions
	public Action getAction(String key) {
		return actions.get(key);
	}
	public void addAction(String key, Action action) {
		actions.put(key, action);
	}
	public Action removeAction(String key) {
		return actions.remove(key);
	}
	public void removeAllActions() {
		actions.clear();
	}
	public Hashtable<String, Action> getAllActions() {
		return actions;
	}
	
	public Goal getGoal() {
		return goal;
	}
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	
	// Constructors
	public Plan(String name, Goal goal) {
		super();
		this.name = name;
		this.setBehaviourName(name);
		this.goal = goal;
	}
	
	public Plan(String name, Goal goal, GenericAgent agent) {
		super(agent);
		this.name = name;
		this.setBehaviourName(name);
		this.goal = goal;
	}
	
	protected void execute(){
		for (Action action : actions.values()) {
			if (action != null) {
				addSubBehaviour(action);
			}
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		execute();
	}
	
}
