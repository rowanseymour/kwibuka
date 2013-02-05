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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Map of number sequences to words
 */
public class WordMap {

	/**
	 * Map of number sequences to list of words
	 */
	private Map<String, List<String>> words = new HashMap<String, List<String>>();

	private Random rand = new Random();

	/**
	 * Loads a word list from a reader
	 * @param reader the reader
	 * @throws IOException if reader couldn't be read
	 */
	public WordMap(Reader reader) throws IOException {
		BufferedReader buffered = new BufferedReader(reader);

		String line = null;
		while((line = buffered.readLine()) != null) {
			String word = line.toLowerCase();

			if (word.length() == 0 || !StringUtils.isAlpha(word)) {
				continue;
			}

			String numbers = numberify(word);

			List<String> wordsForSequence = words.get(numbers);
			if (wordsForSequence == null) {
				wordsForSequence = new ArrayList<String>();
				words.put(numbers, wordsForSequence);
			}

			wordsForSequence.add(word);
		}

		buffered.close();
	}

	/**
	 * Converts the given number sequence to a sequence of words
	 * @param numbers the number sequence
	 * @return the words
	 */
	public List<String> wordify(String numbers) {
		List<String> tokens = new ArrayList<String>();

		while (numbers.length() > 0) {
			String token = null;

			// No words for zero so its always a token
			if (numbers.startsWith("0")) {
				token = "0";
			}
			else {
				String sequence = numbers;

				while (sequence.length() > 0) {
					List<String> wordsForSequence = words.get(sequence);

					if (wordsForSequence != null) {
						token = wordsForSequence.get(rand.nextInt(wordsForSequence.size()));
						break;
					}

					sequence = sequence.substring(0, sequence.length() - 1);
				}

				if (token == null) {
					token = numbers.substring(1);
				}
			}

			// Munch token from input number
			tokens.add(token);
			numbers = numbers.substring(token.length(), numbers.length());
		}

		return tokens;
	}

	/**
	 * Converts a word to its keypad numbers
	 * @param word the input word, e.g. test
	 * @return the numbers, e.g. 8378
	 */
	protected static String numberify(String word) {
		StringBuilder numbers = new StringBuilder(word.length());

		for (int c = 0; c < word.length(); ++c) {
			char ch = word.charAt(c);
			if (ch <= 'c') {
				numbers.append(2);
			}
			else if (ch <= 'f') {
				numbers.append(3);
			}
			else if (ch <= 'i') {
				numbers.append(4);
			}
			else if (ch <= 'l') {
				numbers.append(5);
			}
			else if (ch <= 'o') {
				numbers.append(6);
			}
			else if (ch <= 's') {
				numbers.append(7);
			}
			else if (ch <= 'v') {
				numbers.append(8);
			}
			else {
				numbers.append(9);
			}
		}

		return numbers.toString();
	}
}
