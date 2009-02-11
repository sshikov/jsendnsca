package com.googlecode.jsendnsca.core;

public class NagiosSettingsBuilder {

	private static NagiosSettingsBuilder instance = new NagiosSettingsBuilder();

	private NagiosSettings nagiosSettings = new NagiosSettings();

	public static NagiosSettings createDefault() {
		return new NagiosSettings();
	}

	public static NagiosSettings create() {
		return instance.nagiosSettings;
	}

	public static NagiosSettingsBuilder withNagiosHost(String nagiosHost) {
		instance.nagiosSettings.setNagiosHost(nagiosHost);
		return instance;
	}

	public static NagiosSettingsBuilder withPort(int port) {
		instance.nagiosSettings.setPort(port);
		return instance;
	}

	public static NagiosSettingsBuilder withPassword(String password) {
		instance.nagiosSettings.setPassword(password);
		return instance;
	}

	public static NagiosSettingsBuilder withConnectionTimeout(
			int connectionTimeout) {
		instance.nagiosSettings.setConnectTimeout(connectionTimeout);
		return instance;
	}

	public static NagiosSettingsBuilder withResponseTimeout(int responseTimeout) {
		instance.nagiosSettings.setTimeout(responseTimeout);
		return instance;
	}

	public static NagiosSettingsBuilder withNoEncryption() {
		instance.nagiosSettings
				.setEncryptionMethod(NagiosSettings.NO_ENCRYPTION);
		return instance;
	}
}
