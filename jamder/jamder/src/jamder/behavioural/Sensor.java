package jamder.behavioural;

import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.agents.GenericAgent;

public class Sensor extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998516906340376582L;
	private ServiceDescription service = new ServiceDescription();

	private DFAgentDescription dfd = new DFAgentDescription();
	
	public Sensor(GenericAgent a, long period) {
		super(a, period);
		service.setType("perception");
		dfd.addServices(service);
		a.getDefaultDF();
		
		
		DFAgentDescription dfd = new DFAgentDescription();
		//dfd.setName("Perception");
		
	}

	@Override
	protected void onTick() {
		try {
			ACLMessage message = this.myAgent.receive(MessageTemplate.MatchConversationId("perception"));
			GenericAgent a = (GenericAgent) this.myAgent;
		
			if (message != null && message.hasByteSequenceContent()) {
				String percept = (String) message.getContent();
				if (percept.length() >= 1) {
					a.percept(percept);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
