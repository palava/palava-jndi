/**
 * Copyright 2010 CosmoCode GmbH
 *
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

package de.cosmocode.palava.jndi;

import javax.naming.Context;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A {@link Context} {@link Provider} which requires the presence of
 * {@link JndiContextBinderUtility}.
 * 
 * @author Tobias Sarnowski
 */
class JndiBinder implements Provider<Context> {
    
    private final JndiContextProvider provider;

    @Inject
    public JndiBinder(JndiContextProvider provider, JndiContextBinderUtility utility) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
        // require the availability
        Preconditions.checkNotNull(utility, "Utility");
    }

    @Override
    public Context get() {
        return provider.get();
    }
}
