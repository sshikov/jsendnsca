package com.googlecode.jsendnsca.core.utils;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
		case NagiosSettings.TRIPLE_DES_ENCRYPTION:
			encryptUsingTripleDES(passiveCheckBytes, initVector, nagiosSettings);
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
	 *            {@link NagiosSettings#XOR_ENCRYPTION} or
	 *            {@link NagiosSettings#TRIPLE_DES_ENCRYPTION}
	 * @return true if supported
	 */
	public static boolean isEncryptionMethodSupported(int encryptionMethod) {
		if (
			encryptionMethod == NagiosSettings.NO_ENCRYPTION
			||
			encryptionMethod == NagiosSettings.XOR_ENCRYPTION
			||
			encryptionMethod == NagiosSettings.TRIPLE_DES_ENCRYPTION
		) {
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

	private static void encryptUsingTripleDES(byte[] passiveCheckBytes,	byte[] initVector, NagiosSettings nagiosSettings) {
		// Copy the settings into byte arrays of the required length.
		final byte[] keyBytes = toLength(nagiosSettings.getPassword().getBytes(), 24);
		final byte[] ivBytes = toLength(initVector, 8);

		// Create the key & IV spec.
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(ivBytes);

		try {
			// Create the cipher and do the encryption.
			final Cipher cipher = Cipher.getInstance("DESede/CFB8/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			final byte[] cipherText = cipher.doFinal(passiveCheckBytes);

			// Copy the encrypted version back into the supplied array.
			for (int i = 0; i < passiveCheckBytes.length; i++) {
				passiveCheckBytes[i] = cipherText[i];
			}
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static byte[] toLength(byte[] source, int length) {
		byte[] result = new byte[length];

		for (int i = 0; i < length && i < source.length; i++) {
			if (i < source.length) {
				result[i] = source[i];
			} else {
				result[i] = 0;
			}
		}

		return result;
	}
}
