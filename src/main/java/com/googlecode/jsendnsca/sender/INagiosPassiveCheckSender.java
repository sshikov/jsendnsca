package com.googlecode.jsendnsca.sender;

/**
 * Interface to be implemented by {@link NagiosPassiveCheckSender}'s
 * 
 * @author Raj.Patel
 * @version 1.0
 */
public interface INagiosPassiveCheckSender 
{
	/**
	 * Send Passive Check
	 * 
	 * @param payload the Passive Check message payload
	 * @throws NagiosException thrown if an error occurs while sending the passive check
	 */
	void send(MessagePayload payload) throws NagiosException;
}
