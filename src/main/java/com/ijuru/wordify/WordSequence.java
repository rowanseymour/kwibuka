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
	 * Sets the score
	 * @param score the score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @see Comparable
	 */
	@Override
	public int compareTo(WordSequence sequence) {
		return sequence.score - score;
	}
}