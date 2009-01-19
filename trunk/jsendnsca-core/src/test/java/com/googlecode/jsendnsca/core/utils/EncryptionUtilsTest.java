package com.googlecode.jsendnsca.core.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.jsendnsca.core.NagiosSettings;


public class EncryptionUtilsTest {

	@Test
	public void shouldReturnTrueForNoOrXorEncryptionOnly() throws Exception {
		assertTrue(EncryptionUtils.isEncryptionMethodSupported(NagiosSettings.NO_ENCRYPTION));
		assertTrue(EncryptionUtils.isEncryptionMethodSupported(NagiosSettings.XOR_ENCRYPTION));
		assertFalse(EncryptionUtils.isEncryptionMethodSupported(2));
	}
}
