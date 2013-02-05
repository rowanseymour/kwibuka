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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
			addWord(word);
		}

		buffered.close();

		// Add digits 0...9
		for (int n = 0; n <= 9; ++n) {
			addWord(n + "");
		}
	}

	/**
	 * Adds a word to the list
	 * @param word the word
	 */
	protected void addWord(String word) {
		String numbers = numberify(word);

		List<String> wordsForSequence = words.get(numbers);
		if (wordsForSequence == null) {
			wordsForSequence = new ArrayList<String>();
			words.put(numbers, wordsForSequence);
		}

		wordsForSequence.add(word);
	}

	/**
	 * Converts the given number sequence to a list of different sequences of words
	 * @param numbers the number sequence
	 * @return the list of word sequences
	 */
	public List<List<String>> wordify(String numbers) {
		List<List<String>> results = new ArrayList<List<String>>();

		trySequence(results, new ArrayList<String>(), numbers);

		return results;
	}

	/**
	 * Tries a sequence of words
	 * @param completed
	 * @param currentTokens
	 * @param currentRemainder
	 */
	protected void trySequence(List<List<String>> completed, List<String> currentTokens, String currentRemainder) {

		if (currentRemainder.length() == 0) {
			completed.add(currentTokens);
			return;
		}

		List<Pair<List<String>, String>> splits = splitInput(currentRemainder);

		for (Pair<List<String>, String> split : splits) {

			for (String token : split.getLeft()) {
				List<String> tokensCopy = new ArrayList<String>(currentTokens);
				tokensCopy.add(token);

				trySequence(completed, tokensCopy, split.getRight());
			}
		}
	}

	/**
	 * Splits the input string all possible ways where the left hand side is a valid word or single digit
	 * @param input the input string
	 * @return the splits
	 */
	protected List<Pair<List<String>, String>> splitInput(String input) {
		List<Pair<List<String>, String>> splits = new ArrayList<Pair<List<String>, String>>();

		for (int s = 0; s < input.length(); s++) {
			String left = input.substring(0, s + 1);
			String right = input.substring(s + 1, input.length());

			List<String> wordsForLeft = words.get(left);

			if (wordsForLeft != null) {
				splits.add(new ImmutablePair<List<String>, String>(wordsForLeft, right));
			}
		}

		return splits;
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

			if (Character.isDigit(ch)) {
				numbers.append(ch);
			}
			else if (ch <= 'c') {
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
