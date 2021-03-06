/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.security.internal;

import java.util.List;
import javax.annotation.Nonnull;

import org.apache.jackrabbit.oak.spi.security.CompositeConfiguration;
import org.apache.jackrabbit.oak.spi.security.ConfigurationBase;
import org.apache.jackrabbit.oak.spi.security.ConfigurationParameters;
import org.apache.jackrabbit.oak.spi.security.SecurityConfiguration;
import org.apache.jackrabbit.oak.spi.security.SecurityProvider;

class ConfigurationInitializer {

    private ConfigurationInitializer() {}

    @Nonnull
    static <T extends SecurityConfiguration> T initializeConfiguration(@Nonnull SecurityProvider securityProvider, @Nonnull T configuration) {
        return initializeConfiguration(securityProvider, configuration, ConfigurationParameters.EMPTY);
    }

    @Nonnull
    static <T extends SecurityConfiguration> T initializeConfiguration(@Nonnull SecurityProvider securityProvider, @Nonnull T configuration, @Nonnull ConfigurationParameters parameters) {
        if (configuration instanceof ConfigurationBase) {
            ConfigurationBase base = (ConfigurationBase) configuration;
            base.setSecurityProvider(securityProvider);
            base.setParameters(ConfigurationParameters.of(base.getParameters(), parameters));
        }
        return configuration;
    }

    static void initializeConfigurations(@Nonnull SecurityProvider securityProvider,
                                         @Nonnull CompositeConfiguration configuration,
                                         @Nonnull ConfigurationParameters parameters) {
        configuration.setSecurityProvider(securityProvider);
        List<? extends SecurityConfiguration> configs = configuration.getConfigurations();
        for (SecurityConfiguration config : configs) {
            initializeConfiguration(securityProvider, config, parameters);
        }
    }
}