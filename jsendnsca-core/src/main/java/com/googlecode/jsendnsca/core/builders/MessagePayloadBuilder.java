package com.googlecode.jsendnsca.core.builders;

import java.net.UnknownHostException;

import com.googlecode.jsendnsca.core.Level;
import com.googlecode.jsendnsca.core.MessagePayload;

/**
 * Used to construct a {@link MessagePayload} using a builder pattern e.g.
 * 
 * <pre>
 * MessagePayload payload = MessagePayloadBuilder
 *			.withHostname("localhost")
 *			.withLevel(Level.CRITICAL)
 *			.withServiceName("Test Service Name")
 *			.withMessage("Test Message")
 *			.create();
 * </pre>
 * 
 * @author Raj.Patel
 * @since 1.2
 */
public class MessagePayloadBuilder {

	private static MessagePayloadBuilder instance = new MessagePayloadBuilder();

	private MessagePayload payload = new MessagePayload();

	/**
	 * Return a default {@link MessagePayload}
	 * 
	 * @return a newly constructed default {@link MessagePayload}
	 */
	public static MessagePayload createDefault() {
		return new MessagePayload();
	}

	/**
	 * Return the built {@link MessagePayload}
	 * 
	 * @return the built {@link MessagePayload}
	 */
	public static MessagePayload create() {
        MessagePayload answer = instance.payload;
        // create a new instance for builder to use next time otherwise we end up
        // changing the instance we just created
        instance = new MessagePayloadBuilder();
        return answer;
	}

	/**
	 * Use the short hostname of the local machine in the passive check
	 * 
	 * @return the {@link MessagePayloadBuilder}
	 * @throws UnknownHostException
	 *             error while determining local machine name
	 */
	public static MessagePayloadBuilder withLocalHostname()
			throws UnknownHostException {
		instance.payload.useLocalHostname();
		return instance;
	}

	/**
	 * Use the fully qualified domain name of the local machine in the passive
	 * check
	 * 
	 * @return the {@link MessagePayloadBuilder}
	 * @throws UnknownHostException
	 *             error while determining local machine name
	 */
	public static MessagePayloadBuilder withCanonicalHostname()
			throws UnknownHostException {
		instance.payload.setHostname(true);
		return instance;
	}

	/**
	 * Use the supplied hostname in the passive check
	 * 
	 * @param hostname
	 *            the hostname
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withHostname(String hostname) {
		instance.payload.setHostname(hostname);
		return instance;
	}

	/**
	 * Set the level of the passive check
	 * 
	 * @param level
	 *            the integer level, use the constants defined in
	 *            {@link MessagePayload}
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withLevel(int level) {
		instance.payload.setLevel(level);
		return instance;
	}

	/**
	 * Set the level of the passive check
	 * 
	 * @param level
	 *            the String level, case insensitive values are either "ok",
	 *            "warning", "critical" or "unknown"
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withLevel(String level) {
		instance.payload.setLevel(level);
		return instance;
	}

	/**
	 * Set the level of the passive check
	 * 
	 * @param level
	 *            the {@link Level}
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withLevel(Level level) {
		instance.payload.setLevel(level);
		return instance;
	}

	/**
	 * Set the service name of the passive check
	 * 
	 * @param serviceName
	 *            the service name
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withServiceName(String serviceName) {
		instance.payload.setServiceName(serviceName);
		return instance;
	}

	/**
	 * Set the message of the passive check
	 * 
	 * @param message
	 *            the message
	 * @return the {@link MessagePayloadBuilder}
	 */
	public static MessagePayloadBuilder withMessage(String message) {
		instance.payload.setMessage(message);
		return instance;
	}
}
