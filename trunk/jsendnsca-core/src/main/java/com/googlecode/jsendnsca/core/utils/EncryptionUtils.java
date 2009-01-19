package com.googlecode.jsendnsca.core.utils;

import com.googlecode.jsendnsca.core.NagiosSettings;

/**
 * Provides utility methods for encryption of the passive check
 * 
 * @author Raj.Patel
 * @since 1.1.1
 */
public class EncryptionUtils {

	private static final int INITIALISATION_VECTOR_SIZE = 128;

	/**
	 * Encrypt the Passive Check
	 * 
	 * @param passiveCheckBytes
	 *            the passive check bytes to encrypt
	 * @param initVector
	 *            the initialization vector provided on connection to NSCA
	 * @param nagiosSettings
	 *            the {@link NagiosSettings}
	 */
	public static void encrypt(byte[] passiveCheckBytes, byte[] initVector, NagiosSettings nagiosSettings) {
		switch (nagiosSettings.getEncryptionMethod()) {
		case NagiosSettings.XOR_ENCRYPTION:
			encryptUsingXOR(passiveCheckBytes, initVector, nagiosSettings);
			break;
		default:
			break;
		}
	}

	/**
	 * Used to check if an encryption method is supported
	 * 
	 * @param encryptionMethod
	 *            the encryption method, currently
	 *            {@link NagiosSettings#NO_ENCRYPTION} or
	 *            {@link NagiosSettings#XOR_ENCRYPTION}
	 * @return true if supported
	 */
	public static boolean isEncryptionMethodSupported(int encryptionMethod) {
		if (encryptionMethod == NagiosSettings.NO_ENCRYPTION || encryptionMethod == NagiosSettings.XOR_ENCRYPTION) {
			return true;
		}
		return false;
	}

	private static void encryptUsingXOR(byte[] passiveCheckBytes, byte[] initVector, NagiosSettings nagiosSettings) {
		for (int y = 0, x = 0; y < passiveCheckBytes.length; y++, x++) {
			if (x >= INITIALISATION_VECTOR_SIZE) {
				x = 0;
			}
			passiveCheckBytes[y] ^= initVector[x];
		}

		if (StringUtils.isNotBlank(nagiosSettings.getPassword())) {
			final byte[] passwordBytes = nagiosSettings.getPassword().getBytes();

			for (int y = 0, x = 0; y < passiveCheckBytes.length; y++, x++) {
				if (x >= passwordBytes.length) {
					x = 0;
				}
				passiveCheckBytes[y] ^= passwordBytes[x];
			}
		}
	}
}
