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

package com.ijuru.kwibuka;

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
public class WordListWordifier extends AbstractWordifier {

	/**
	 * Map of number sequences to list of words
	 */
	private Map<String, Set<String>> words = new HashMap<String, Set<String>>();

	/**
	 * Constructs wordifier with only digits
	 */
	private WordListWordifier(Locale locale) {
		super(locale);

		// Add digits 0...9
		for (int n = 0; n <= 9; ++n) {
			addWord(n + "");
		}
	}

	/**
	 * Creates a wordifier from a word list
	 * @param words the word list
	 */
	public static WordListWordifier fromWordlist(Locale locale, Collection<String> words) {
		WordListWordifier wordifier = new WordListWordifier(locale);

		for (String word : words) {
			wordifier.addWord(word);
		}

		return wordifier;
	}

	/**
	 * Creates a wordifier from a word list
	 * @param reader the reader for the word list
	 * @throws IOException if reader couldn't be read
	 */
	public static WordListWordifier fromWordlist(Locale locale, Reader reader) throws IOException {
		WordListWordifier wordifier = new WordListWordifier(locale);
		BufferedReader buffered = new BufferedReader(reader);

		String line;
		while((line = buffered.readLine()) != null) {
			String word = line.toLowerCase();

			if (word.length() == 0 || !StringUtils.isAlpha(word)) {
				continue;
			}
			wordifier.addWord(word);
		}

		buffered.close();

		return wordifier;
	}

	/**
	 * Adds a word to the list
	 * @param word the word
	 */
	protected void addWord(String word) {
		String numbers = numberify(word);

		Set<String> wordsForSequence = words.get(numbers);
		if (wordsForSequence == null) {
			wordsForSequence = new HashSet<String>();
			words.put(numbers, wordsForSequence);
		}

		wordsForSequence.add(word);
	}

	/**
	 * @see Wordifier#wordify(String)
	 */
	@Override
	public List<WordSequence> wordify(String numbers) {
		List<WordSequence> results = new ArrayList<WordSequence>();

		// Generate all sequences
		trySequence(results, new WordSequence(), numbers);

		// Score each sequence
		for (WordSequence sequence : results) {
			sequence.score();
		}

		// Sort by score descending
		Collections.sort(results);

		return results;
	}

	/**
	 * Recursively tries sequences of words
	 * @param completed the completed sequences
	 * @param current the current sequence
	 * @param remainder the input remainder
	 */
	protected void trySequence(List<WordSequence> completed, WordSequence current, String remainder) {

		if (remainder.length() == 0) {
			completed.add(current);
			return;
		}

		List<Pair<Set<String>, String>> splits = splitInput(remainder);

		for (Pair<Set<String>, String> split : splits) {

			for (String token : split.getLeft()) {
				WordSequence tokensCopy = current.clone();
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
	protected List<Pair<Set<String>, String>> splitInput(String input) {
		List<Pair<Set<String>, String>> splits = new ArrayList<Pair<Set<String>, String>>();

		for (int s = 0; s < input.length(); s++) {
			String left = input.substring(0, s + 1);
			String right = input.substring(s + 1, input.length());

			Set<String> wordsForLeft = words.get(left);

			if (wordsForLeft != null) {
				splits.add(new ImmutablePair<Set<String>, String>(wordsForLeft, right));
			}
		}

		return splits;
	}
}
