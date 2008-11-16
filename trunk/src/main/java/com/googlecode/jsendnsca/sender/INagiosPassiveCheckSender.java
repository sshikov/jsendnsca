package com.googlecode.jsendnsca.sender;

public interface INagiosPassiveCheckSender 
{
	void send(MessagePayload payload) throws NagiosException;
}
