package jamder.behavioural;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.agents.GenericAgent;
import jamder.agents.ReflexAgent;

public class Sensor extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998516906340376582L;

	public Sensor(GenericAgent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		try {
			ACLMessage message = this.myAgent.receive(MessageTemplate.MatchConversationId("perception"));
			ReflexAgent a = (ReflexAgent) this.myAgent;
		
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
