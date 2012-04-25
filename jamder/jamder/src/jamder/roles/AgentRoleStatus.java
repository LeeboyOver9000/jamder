package jamder.roles;

public enum AgentRoleStatus {
	ACTIVATE	(false),
	DEACTIVATE	(false),
	CHANGE		(false);
	
	private boolean status;
	
	AgentRoleStatus (boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
		
		// if one status is true, the other are false
		if (status) {
			for(AgentRoleStatus other : this.values()) {
				if (!other.name().equals(this.name())) {
					other.setStatus(false);
				}
			}
		}
	}
}
