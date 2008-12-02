package com.googlecode.jsendnsca.core;

import org.junit.Test;

import com.googlecode.jsendnsca.core.utils.StringUtils;

public class NagiosSettingsTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSettingHostnameToEmptyString() throws Exception {
        final NagiosSettings nagiosSettings = new NagiosSettings();

        nagiosSettings.setNagiosHost(StringUtils.EMPTY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSettingPasswordToEmptyString() throws Exception {
        final NagiosSettings nagiosSettings = new NagiosSettings();

        nagiosSettings.setPassword(StringUtils.EMPTY);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void shouldOnlySupportXORorNoEncryption() {
    	final NagiosSettings nagiosSettings = new NagiosSettings();
    	
    	nagiosSettings.setEncryptionMethod(2);
    }
    
    @Test
    public void shouldSupportXORorNoEncryption() {
    	final NagiosSettings nagiosSettings = new NagiosSettings();
    	
    	nagiosSettings.setEncryptionMethod(NagiosSettings.NO_ENCRYPTION);
    	nagiosSettings.setEncryptionMethod(NagiosSettings.XOR_ENCRYPTION);
    }
}
