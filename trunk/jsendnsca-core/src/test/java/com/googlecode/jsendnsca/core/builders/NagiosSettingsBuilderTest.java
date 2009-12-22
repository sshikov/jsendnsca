package com.googlecode.jsendnsca.core.builders;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.jsendnsca.core.NagiosSettings;
import com.googlecode.jsendnsca.core.builders.NagiosSettingsBuilder;

@SuppressWarnings("static-access")
public class NagiosSettingsBuilderTest {

	@Test
	public void shouldCreateDefault() throws Exception {
		NagiosSettings defaultNagiosSettings = new NagiosSettings();
		
		NagiosSettings nagiosSettings = NagiosSettingsBuilder.createDefault();
		assertEquals(defaultNagiosSettings, nagiosSettings);
	}
	
	@Ignore
	@Test
	public void shouldCreateWithEverythingOverriden() throws Exception {
		String host = "nagioshost";
		int port = 9999;
		String password = "s3cr3t";
		int connectionTimeout = 1;
		int responseTimeout = 1;

		NagiosSettings nagiosSettings = NagiosSettingsBuilder
			.withNagiosHost(host)
			.withPort(port)
			.withPassword(password)
			.withConnectionTimeout(connectionTimeout)
			.withResponseTimeout(responseTimeout)
			.withNoEncryption()
			.create();

		assertEquals(host, nagiosSettings.getNagiosHost());
		assertEquals(port, nagiosSettings.getPort());
		assertEquals(password, nagiosSettings.getPassword());
		assertEquals(connectionTimeout, nagiosSettings.getConnectTimeout());
		assertEquals(responseTimeout, nagiosSettings.getTimeout());
		assertEquals(NagiosSettings.NO_ENCRYPTION, nagiosSettings.getEncryptionMethod());
		
		// re-set all the defaults so the other tests aren't broken
		nagiosSettings = NagiosSettingsBuilder.createDefault();
		NagiosSettingsBuilder
		    .withNagiosHost(nagiosSettings.getNagiosHost())
		    .withPort(nagiosSettings.getPort())
		    .withPassword(nagiosSettings.getPassword())
		    .withConnectionTimeout(nagiosSettings.getConnectTimeout())
		    .withEncryption(1)
		    .create();
	}
}
