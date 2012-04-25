package jamder.behavioural;

import jade.core.behaviours.Behaviour;
import jamder.Environment;

import java.util.Hashtable;

import jamder.agents.GenericAgent;

public class Action extends Behaviour{
	private String name;
	private Hashtable<String, Condition> pre_conditions = new Hashtable<String, Condition>();
	private Hashtable<String, Condition> pos_conditions = new Hashtable<String, Condition>();
	private boolean done = false;
	//private boolean canExecute = false;
	
	// Default constructor
	public Action(String name) { 
		this.name = name;
	}
	
	// Constructor with conditions
	public Action(String name, Condition pre_condition, Condition pos_condition) {
		this.name = name;
		this.setBehaviourName(name);
		if (pre_condition != null) {
			pre_conditions.put(pre_condition.getName(), pre_condition);
		}
		
		if (pos_condition != null) {
			pos_conditions.put(pos_condition.getName(), pos_condition);
		}
	}
	
	// Name
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// Pre-conditions
	public Condition getPreCondition(String key) {
		return pre_conditions.get(key);
	}
	public void addPreCondition(String key, Condition condition) {
		pre_conditions.put(key, condition);
	}
	public Condition removePreCondition(String key) {
		return pre_conditions.remove(key);
	}
	public void removeAllPreConditions() {
		pre_conditions.clear();
	}
	public Hashtable<String, Condition> getAllPreConditions() {
		return pre_conditions;
	}
	
	// Pos-conditions
	public Condition getPosCondition(String key) {
		return pos_conditions.get(key);
	}
	public void addPosCondition(String key, Condition condition) {
		pos_conditions.put(key, condition);
	}
	public Condition removePosCondition(String key) {
		return pos_conditions.remove(key);
	}
	public void removeAllPosConditions() {
		pos_conditions.clear();
	}
	public Hashtable<String, Condition> getAllPosConditions() {
		return pos_conditions;
	}
	
	public void execute(Environment env, Object[] params){
		// Checks if all pre-conditions are true
		// execute();
		// else done
		{
			done = true;
			return;
		}
	}
	
	@Override
	public void action() {
		GenericAgent agent = (GenericAgent)myAgent;
		execute(agent.getEnvironment(), null);
	}
	
	@Override
	public boolean done() {
		if (done) {
			return done;
		} else {
			// Checks if all pos-conditions are true 
			return false;
		}
	}
}
