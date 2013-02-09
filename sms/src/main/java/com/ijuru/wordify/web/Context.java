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

import com.ijuru.wordify.Wordifier;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Application context
 */
public class Context {

	private static final String ENVVAR_NAME = "WORDIFY_OPTIONS";
	private static final String JVMPROP_NAME = "wordify.options";

	private static Options options;

	private static Wordifier wordifier;

	/**
	 * Starts the application
	 * @throws Exception 
	 */
	public static void startApplication() throws Exception {
		// Look for environment variable or system property
		String optionsJSON = System.getenv(ENVVAR_NAME);
		if (optionsJSON == null) {
			optionsJSON = System.getProperty(JVMPROP_NAME);
			if (optionsJSON == null) {
				throw new Exception("Could not find environment variable (" + ENVVAR_NAME + ") or JVM property (" + JVMPROP_NAME + ")");
			}
		}
			
		ObjectMapper mapper = new ObjectMapper();
		options = mapper.readValue(optionsJSON, Options.class);

		String wordListPath = "words." + options.getLang() + ".lst";

		try {
			InputStream stream = Context.class.getClassLoader().getResourceAsStream(wordListPath);

			wordifier = Wordifier.fromWordlist(new InputStreamReader(stream));
		}
		catch (IOException ex) {
			throw new Exception("Could not load word list resource (" + wordListPath + ")", ex);
		}
	}
	
	public static void destroyApplication() {
		
	}
	
	public static Options getOptions() {
		return options;
	}

	public static Wordifier getWordifier() {
		return wordifier;
	}
}