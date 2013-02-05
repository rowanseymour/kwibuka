/**
 * Copyright 2011 Rowan Seymour
 * 
 * This file is part of Wordify.
 *
 * Wordify is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Wordify is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Wordify. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ijuru.wordify.web;

import com.ijuru.wordify.Context;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listens for webapp stop/start
 */
public class Listener implements ServletContextListener {

	protected static final Logger log = LogManager.getLogger(Listener.class);
	
	/**
	 * Web application is being deployed or started
	 */
	public void contextInitialized(ServletContextEvent event) {	
		log.info("Initializing Wordify");
		
		try {
			Context.startApplication();
			
		} catch (Exception ex) {
			log.error("Unable to start application", ex);
		}
	}
	
	/**
	 * Web application is being undeployed or stopped
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Destroying Wordify");
		
		Context.destroyApplication();
	}
}
