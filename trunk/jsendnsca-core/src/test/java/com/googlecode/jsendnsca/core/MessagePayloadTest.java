package com.googlecode.jsendnsca.core;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.googlecode.jsendnsca.core.utils.StringUtils;

public class MessagePayloadTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnEmptyServiceName() throws Exception {
		final MessagePayload payload = new MessagePayload();

		payload.setHostname("localhost");
		payload.setLevel(MessagePayload.LEVEL_CRITICAL);
		payload.setServiceName(StringUtils.EMPTY);
	}

	@Test
	public void shouldConstructValidObjectWhenUsingNoArgConstructor() throws Exception {
		final MessagePayload messagePayload = new MessagePayload();

		assertEquals("localhost", messagePayload.getHostname());
		assertEquals(MessagePayload.LEVEL_UNKNOWN, messagePayload.getLevel());
		assertEquals("UNDEFINED", messagePayload.getServiceName());
		assertEquals(StringUtils.EMPTY, messagePayload.getMessage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionOnEmptyHostName() throws Exception {
		final MessagePayload payload = new MessagePayload();

		payload.setHostname(StringUtils.EMPTY);
	}

	@Test
	public void shouldAllowEmptyMessageToBeSet() throws Exception {
		final MessagePayload payload = new MessagePayload();

		payload.setMessage(StringUtils.EMPTY);
	}

	@Test
	public void shouldConstructNewMessagePayload() throws Exception {
		final MessagePayload messagePayload = new MessagePayload("localhost", 0, "test service", "test message");

		assertEquals("localhost", messagePayload.getHostname());
		assertEquals(MessagePayload.LEVEL_OK, messagePayload.getLevel());
		assertEquals("test service", messagePayload.getServiceName());
		assertEquals("test message", messagePayload.getMessage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnConstructingNewMessagePayloadWithNullHostname() throws Exception {
		new MessagePayload(null, 1, "test service", "test message");
	}

	@Test
	public void shouldConvertStringLevelsToIntegerWhileIgnoringCase() throws Exception {
		final MessagePayload messagePayload = new MessagePayload();

		messagePayload.setLevel("Ok");
		assertEquals(MessagePayload.LEVEL_OK, messagePayload.getLevel());
		messagePayload.setLevel("Warning");
		assertEquals(MessagePayload.LEVEL_WARNING, messagePayload.getLevel());
		messagePayload.setLevel("Critical");
		assertEquals(MessagePayload.LEVEL_CRITICAL, messagePayload.getLevel());
		messagePayload.setLevel("Unknown");
		assertEquals(MessagePayload.LEVEL_UNKNOWN, messagePayload.getLevel());
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionIfStringLevelIsNotRecognised() throws Exception {
		final MessagePayload messagePayload = new MessagePayload();

		try {
			messagePayload.setLevel("foobar");
			fail("Should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("[foobar] is not valid level", e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionIfStringLevelIsNull() throws Exception {
		final MessagePayload messagePayload = new MessagePayload();

		messagePayload.setLevel(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionForInvalidLevel() throws Exception {
		final MessagePayload messagePayload = new MessagePayload();

		messagePayload.setLevel(4);
	}
}
