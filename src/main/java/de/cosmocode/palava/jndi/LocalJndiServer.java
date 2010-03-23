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

import de.cosmocode.palava.core.lifecycle.Disposable;
import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;
import org.jnp.interfaces.NamingContext;
import org.jnp.server.Main;
import org.jnp.server.NamingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;

/**
 * @author Tobias Sarnowski
 */
class LocalJndiServer implements Initializable, Disposable {
    private static final Logger LOG = LoggerFactory.getLogger(LocalJndiServer.class);

    private Main jndiServer;

    @Override
    public void initialize() throws LifecycleException {
        NamingServer namingServer = null;
        try {
            namingServer = new NamingServer();
        } catch (NamingException e) {
            throw new LifecycleException(e);
        }
        NamingContext.setLocal(namingServer);

        jndiServer = new Main();
        jndiServer.setInstallGlobalService(true);
        jndiServer.setPort(-1);
        try {
            jndiServer.start();
        } catch (Exception e) {
            throw new LifecycleException(e);
       }
    }

    @Override
    public void dispose() throws LifecycleException {
        jndiServer.stop();
    }
}
