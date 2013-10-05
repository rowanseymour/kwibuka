package com.ijuru.kwibuka;

import java.util.Locale;

/**
 * Abstract base class for wordifiers
 */
public abstract class AbstractWordifier implements Wordifier {

	protected Locale locale;

	/**
	 * Constructs a wordifier
	 * @param locale the locale
	 */
	public AbstractWordifier(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @see Wordifier#getLocale()
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Convenience method to get the name of the language
	 * @return the language name
	 */
	public String getLanguageName() {
		return getLocale().getDisplayLanguage();
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