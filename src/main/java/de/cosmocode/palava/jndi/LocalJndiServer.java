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

import javax.naming.NamingException;

import org.jnp.interfaces.NamingContext;
import org.jnp.server.Main;
import org.jnp.server.NamingServer;

import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * Installs a local jndi server provided by org.jnp.
 * 
 * @author Tobias Sarnowski
 * @author Willi Schoenborn
 */
final class LocalJndiServer implements Initializable, Disposable {
    
    private final Main main;

    public LocalJndiServer() {
        final NamingServer namingServer;
        
        try {
            namingServer = new NamingServer();
        } catch (NamingException e) {
            throw new LifecycleException(e);
        }
        
        NamingContext.setLocal(namingServer);

        main = new Main();
        main.setInstallGlobalService(true);
        main.setPort(-1);
    }
    
    @Override
    public void initialize() throws LifecycleException {
        try {
            main.start();
        /* CHECKSTYLE:OFF */
        } catch (Exception e) {
        /* CHECKSTYLE:ON */
            throw new LifecycleException(e);
        }
    }

    @Override
    public void dispose() throws LifecycleException {
        main.stop();
    }
    
}