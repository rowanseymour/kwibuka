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
import com.ijuru.wordify.Wordifier;
import com.ijuru.wordify.WordSequence;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Wordifies the input number
 */
public class WordifyServlet extends HttpServlet {
	
	protected static final Logger log = LogManager.getLogger(WordifyServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String ERROR_MESSAGE = "Something went wrong... sorry";

	private static final int MAX_OUTPUT_CHARS = 160;
	
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String text = request.getParameter("text");
		String number = request.getParameter("number");

		if (text != null) {
			// Strip keyword from incoming message and trim
			text = StringUtils.stripStart(text.toLowerCase(), "digits").trim();

			// Number specified in message overrides source number
			if (StringUtils.isNumeric(text)) {
				number = text;
			}
		}

		// Can't do anything without a number
		if (number == null) {
			out.write(ERROR_MESSAGE);
			return;
		}

		// Strip the number prefix
		if (Context.getOptions().getStripNumberPrefix() != null) {
			number = StringUtils.removeStart(number, Context.getOptions().getStripNumberPrefix());
		}

		Wordifier wordifier = Context.getWordifier();

		List<WordSequence> sequences = wordifier.wordify(number);

		StringBuilder message = new StringBuilder();

		// Make the longest list of sequences that fits in the max allowed characters
		for (int s = 0; s < sequences.size(); ++s) {
			WordSequence sequence = sequences.get(s);

			String item = (s > 0) ? ", " : "";
			item += StringUtils.join(sequence, "-");

			if (message.length() + item.length() <= MAX_OUTPUT_CHARS) {
				message.append(item);
			}
			else {
				break;
			}
		}

		out.write(message.toString());
	}
}