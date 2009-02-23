package com.googlecode.jsendnsca.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class LevelTest {
	
	@Test
	public void shouldReturnIntegerValueForLevel() throws Exception {
		assertEquals(0, Level.OK.ordinal());
	}
}
