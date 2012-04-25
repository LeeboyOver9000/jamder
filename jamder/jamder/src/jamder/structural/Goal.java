package jamder.structural;

import jamder.behavioural.Plan;

import java.util.Hashtable;


public abstract class Goal extends Property {
	private boolean achieved;
	private Hashtable<String, Plan> plans = new Hashtable<String, Plan>();
	
	public boolean isAchieved() {
		return achieved;
	}
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	
	// Plans
	public Plan getPlan(String key) {
		return plans.get(key);
	}
	public void addPlan(String key, Plan plan) {
		plans.put(key, plan);
	}
	public Plan removePlan(String key) {
		return plans.remove(key);
	}
	public void removeAllPlans() {
		plans.clear();
	}
	public Hashtable<String, Plan> getAllPlans() {
		return plans;
	}
	
	// Design Pattern - Composite
  
}
