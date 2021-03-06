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

import javax.naming.NamingException;

/**
 * A utility which simplifies binding.
 * 
 * @author Tobias Sarnowski
 */
public interface JndiContextBinderUtility {

    /**
     * Binds an object under its jndiName to a context, recursivly creating subcontexts.
     *
     * @param jndiName the jndi name
     * @param who the object
     * @throws NamingException if binding failed
     */
    void bind(String jndiName, Object who) throws NamingException;

    /**
     * Binds a non-serializable object under its jndiName to a context, recursivly creating subcontexts.
     * 
     * @param jndiName the jndi name
     * @param who the object
     * @param classType the class type
     * @throws NamingException if binding failed
     */
    void bind(String jndiName, Object who, Class<?> classType) throws NamingException;

}
