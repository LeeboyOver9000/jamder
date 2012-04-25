package jamder;

import jade.core.ContainerID;
import jade.core.Location;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jamder.agents.MASMLAgent;
import jamder.roles.ObjectRole;
import jamder.roles.ProactiveAgentRole;
import jamder.structural.Axiom;

import java.util.Hashtable;


// 3.1.4 - An organization extends Agent
public class Organization extends MASMLAgent {

	private Hashtable<String, Axiom> axioms = new Hashtable<String, Axiom>();
	private Hashtable<String, Organization> subOrganizations = new Hashtable<String, Organization>();
	private Hashtable<String, ObjectRole> objectRoles = new Hashtable<String, ObjectRole>();
	// agentRoles are inherited by GenericAgent class
	// enviroment is inherited by GenericAgent class
	// goals are inherited by MASMLAgent class
	// beliefs are inherited by MASMLAgent class
	// plans are inherited by MASMLAgent class
	private Organization superOrganization ;
	private AgentContainer agentContainer;


	// This is the indentifier for enviroment;
	private ContainerID containerID ;
	
	// TODO: organizacoes em que a instancia de organizacao está exercendo os papéis
	// TODO: mensagens recebidas e enviadas
	/*
	 * Mensagens recebidas ja existe uma lista
	 * */

	public Organization(String name, Environment environment, ProactiveAgentRole agentRole) {
		// The agentRole for Organization can be null initially, if it is 
		// a Super Organization, if not is mandatory
		super(name, environment, agentRole);
		// TODO: associar um agentContainer para cada instancia de organization
		
		//Runtime rt = Runtime.instance();
		
		
		// If there is some agentRole , then takes the organization
		/*if (agentRole != null) {
			superOrganization = agentRole.getOwner();
			superOrganization.addSubOrganization(name, this);
			//superOrganization.addAgentRole(agentRole.getName(), agentRole);
		}*/
	}
	
	// AgentContainer
	public AgentContainer getAgentContainer() {
		return agentContainer;
	}
	public void setAgentContainer(AgentContainer agentContainer) {
		this.agentContainer = agentContainer;
	}
	
	// ContainerID
	public ContainerID getContainerID() {
		return containerID;
	}
	public void setContainerID(ContainerID containerID) {
		this.containerID = containerID;
	}
	
	// Informs if this organization is a childOrganization
	public Organization getSuperOrganization() {
		return superOrganization;
	}
	public void setSuperOrganization(Organization superOrganization) {
		this.superOrganization = superOrganization;
	}
	
	// Axioms
	public Axiom getAxiom(String key) {
		return axioms.get(key);
	}
	public void addAxiom(String key, Axiom axiom) {
		axioms.put(key, axiom);
	}
	public Axiom removeAxiom(String key) {
		return axioms.remove(key);
	}
	public void removeAllAxioms() {
		axioms.clear();
	}
	public Hashtable<String, Axiom> getAllAxioms() {
		return axioms;
	}
	
	// SubOrganizations
	public Organization getSubOrganization(String key) {
		return subOrganizations.get(key);
	}
	public void addSubOrganization(String key, Organization subOrganization) {
		subOrganizations.put(key, subOrganization);
	}
	public Organization removeSubOrganization(String key) {
		return subOrganizations.remove(key);
	}
	public void removeAllSubOrganizations() {
		subOrganizations.clear();
	}
	public Hashtable<String, Organization> getAllSubOrganizations() {
		return subOrganizations;
	}
	
	// objectRoles
	public ObjectRole getObjectRole(String key) {
		return objectRoles.get(key);
	}
	public void addObjectRole(String key, ObjectRole objectRole) {
		objectRoles.put(key, objectRole);
	}
	public ObjectRole removeObjectRole(String key) {
		return objectRoles.remove(key);
	}
	public void removeAllObjectRoles() {
		objectRoles.clear();
	}
	public Hashtable<String, ObjectRole> getAllObjectRoles() {
		return objectRoles;
	}
	
	public void sendM(ACLMessage msg) {
		// TODO Auto-generated method stub

	}
}
