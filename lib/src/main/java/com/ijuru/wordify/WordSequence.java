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

package com.ijuru.wordify;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * A word sequence with associated score
 */
public class WordSequence extends ArrayList<String> implements Comparable<WordSequence> {

	private int score;

	/**
	 * Constructs an empty word sequence
	 */
	public WordSequence() {
		super();
	}

	/**
	 * Constructs a word sequence by copying an existing one
	 * @param existing
	 */
	private WordSequence(WordSequence existing) {
		super(existing);
		this.score = existing.score;
	}

	/**
	 * Formats this sequence as a string
	 * @return the string
	 */
	public String format() {
		StringBuilder sb = new StringBuilder();
		boolean previousWasNumeric = false;

		for (String token : this) {
			boolean isNumeric = StringUtils.isNumeric(token);

			if (sb.length() > 0 && !(isNumeric && previousWasNumeric)) {
				sb.append("-");
			}

			sb.append(token);
			previousWasNumeric = isNumeric;
		}

		return sb.toString();
	}

	/**
	 * Scores this sequence
	 */
	public void score() {
		int numericTokens = 0;
		for (String token : this) {
			if (StringUtils.isNumeric(token)) {
				++numericTokens;
			}
		}

		this.score = 100 / size() - numericTokens;
	}

	/**
	 * Clones this word sequence
	 * @return a clone
	 */
	public WordSequence clone() {
		return new WordSequence(this);
	}

	/**
	 * Gets the score
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @see Comparable
	 */
	@Override
	public int compareTo(WordSequence sequence) {
		return sequence.score - score;
	}
}