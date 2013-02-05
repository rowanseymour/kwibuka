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

	public WordSequence() {
		super();
	}

	private WordSequence(ArrayList<String> existing) {
		super(existing);
	}

	public WordSequence clone() {
		return new WordSequence(this);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(WordSequence sequence) {
		return sequence.score - score;
	}
}