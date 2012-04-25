package jamder;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jamder.agents.ReflexAgent;

public class PerceptionFunction extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998516906340376582L;

	public PerceptionFunction(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		try {
			ACLMessage message = this.myAgent.receive(MessageTemplate.MatchConversationId("perception"));
			ReflexAgent a = (ReflexAgent) this.myAgent;
		
			if (message != null && message.hasByteSequenceContent()) {
				Object[] percept = (Object[]) message.getContentObject();
				if (percept.length >= 1) {
					//a.percept(percept);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
