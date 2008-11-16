package com.code.google.jsendnsca.sender;

public interface INagiosPassiveCheckSender 
{
	void send(MessagePayload payload) throws NagiosException;
}
