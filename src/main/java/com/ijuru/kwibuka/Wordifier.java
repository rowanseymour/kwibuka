package com.ijuru.kwibuka;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Locale;

/**
 * Base interface for wordifiers
 */
@XmlRootElement
public interface Wordifier {

	/**
	 * Gets the locale of this wordifier
	 * @return the locale
	 */
	@XmlElement
	Locale getLocale();

	/**
	 * Converts the given number sequence to a list of different sequences of words
	 * @param numbers the number sequence
	 * @return the list of word sequences
	 */
	List<WordSequence> wordify(String numbers);
}
