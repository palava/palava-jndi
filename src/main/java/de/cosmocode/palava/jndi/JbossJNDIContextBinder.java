/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.jndi;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jboss.util.naming.NonSerializableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.*;

/**
 * @author Tobias Sarnowski
 */
public class JbossJNDIContextBinder implements JNDIContextBinder {
    private static final Logger LOG = LoggerFactory.getLogger(JbossJNDIContextBinder.class);

    private Provider<Context> context;

    @Inject
    public JbossJNDIContextBinder(Provider<Context> context) {
        this.context = context;
    }

    @Override
    public void bind(String jndiName, Object who) throws NamingException {
        createSubcontext(jndiName);
        context.get().bind(jndiName, who);
    }

    @Override
    public void bind(String jndiName, Object who, Class<?> classType) throws Exception {
        createSubcontext(jndiName);

        NonSerializableFactory.bind(jndiName, who);

        // The helper class NonSerializableFactory uses address type nns, we go on to
        // use the helper class to bind the service object in JNDI
        final StringRefAddr addr = new StringRefAddr("nns", jndiName);
        final Reference ref = new Reference(classType.getName(), addr, NonSerializableFactory.class.getName(), null);

        // just register the reference
        context.get().bind(jndiName, ref);
    }

    private void createSubcontext(String jndiName) throws NamingException {
        Context ctx = context.get();
        Name name = ctx.getNameParser("").parse(jndiName);
        while (name.size() > 1) {
            final String ctxName = name.get(0);
            try {
                ctx = (Context)ctx.lookup(ctxName);
                LOG.trace("Subcontext {} already exists", ctxName);
            }catch (NameNotFoundException e) {
                LOG.info("Creating Subcontext {}", ctxName);
                ctx = ctx.createSubcontext(ctxName);
            }
            name = name.getSuffix(1);
        }
    }
}