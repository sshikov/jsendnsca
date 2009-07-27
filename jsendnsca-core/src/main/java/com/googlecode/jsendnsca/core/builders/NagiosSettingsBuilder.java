/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.jsendnsca.core.builders;

import com.googlecode.jsendnsca.core.NagiosSettings;

/**
 * Used to construct a {@link NagiosSettings} instance using a builder pattern
 * 
 * @author Raj.Patel
 * @since 1.2
 */
public class NagiosSettingsBuilder {

    private static NagiosSettingsBuilder instance = new NagiosSettingsBuilder();

    private NagiosSettings nagiosSettings = new NagiosSettings();

    /**
     * Return an instance of {@link NagiosSettings} with default values
     * 
     * @return default instance
     */
    public static NagiosSettings createDefault() {
        return new NagiosSettings();
    }

    /**
     * Return the built instance of {@link NagiosSettings}
     * 
     * @return the built instance
     */
    public static NagiosSettings create() {
        return instance.nagiosSettings;
    }

    /**
     * The next {@link NagiosSettings} created will use the supplied nagiosHost
     * 
     * @param nagiosHost
     *            the NSCA hostname or IP address
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withNagiosHost(String nagiosHost) {
        instance.nagiosSettings.setNagiosHost(nagiosHost);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will use the supplied port
     * 
     * @param port
     *            the port NSCA is listening on
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withPort(int port) {
        instance.nagiosSettings.setPort(port);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will use the supplied password
     * 
     * @param password
     *            the NSCA password
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withPassword(String password) {
        instance.nagiosSettings.setPassword(password);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will use the supplied connection
     * timeout
     * 
     * @param connectionTimeout
     *            the connection timeout
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withConnectionTimeout(int connectionTimeout) {
        instance.nagiosSettings.setConnectTimeout(connectionTimeout);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will use the supplied response
     * timeout
     * 
     * @param responseTimeout
     *            the NSCA response timeout
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withResponseTimeout(int responseTimeout) {
        instance.nagiosSettings.setTimeout(responseTimeout);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will not use any encryption
     * 
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public static NagiosSettingsBuilder withNoEncryption() {
        instance.nagiosSettings.setEncryptionMethod(NagiosSettings.NO_ENCRYPTION);
        return instance;
    }

    /**
     * The next {@link NagiosSettings} created will use the specified encryption
     * method
     * 
     * @param encryptionMethod
     *            the encryption method to use when sending the passive check
     * @return the {@link NagiosSettingsBuilder} instance
     */
    public NagiosSettingsBuilder withEncryption(int encryptionMethod) {
        instance.nagiosSettings.setEncryptionMethod(encryptionMethod);
        return instance;
    }
}
