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

import junit.framework.Assert;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class WordifierTest {

	private static Wordifier wordifier = null;

	@BeforeClass
	public static void setup() throws IOException {
		InputStreamReader reader = new InputStreamReader(WordifierTest.class.getClassLoader().getResourceAsStream("words.lst"));
		wordifier = Wordifier.fromWordlist(reader);
	}

	@Test
	public void wordify() {
		List<WordSequence> sequences = wordifier.wordify("2234");
		Assert.assertNotNull(sequences);
		Assert.assertEquals(5, sequences.size());

		for (WordSequence sequence : sequences) {
			System.out.println(sequence.format());
		}
	}

	@Test
	public void numberify() {
		Assert.assertEquals("8378", Wordifier.numberify("test"));
		Assert.assertEquals("8378", Wordifier.numberify("t3st"));
	}

	@Test
	public void splitInput() {
		List<Pair<Set<String>, String>> splits = wordifier.splitInput("2234");

		Assert.assertNotNull(splits);
	}
}