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

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Binds {@link LocalJndiServer} as eager {@link Singleton} and
 * binds {@link Context} to {@link LocalJndiContextProvider}.
 * 
 * @author Tobias Sarnowski
 */
public final class LocalJndiServerModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(LocalJndiServer.class).asEagerSingleton();
    }
    
    /**
     * Provides a {@link Context}.
     * 
     * @return the initial local jndi context
     */
    @Provides
    @Singleton
    Context provideContext() {
        try {
            final Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
            return new InitialContext(props);
        } catch (NamingException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
