Wordify
=======

Wordify is a library for generating mnemonics from telephone numbers. It works by using a word list to find all the
possible sequences of words and digits for a given number sequence. Then it ranks them given highest scores to
sequences with long words, and returns the sequences sorted from best to worst score.

For example, the number 0783835665 can be typed on a phone keypad as *0-steve-look* or *0-queue-look* or *0-st-eve-look* etc.

Usage
-----

First initialize a wordifier with word list. You can find many free word lists online:

```java
Wordifier wordifier = Wordifier.fromWordlist(new FileReader("myword.list"));
```

Then you can wordify a number:

```java
List<WordSequence> sequences = wordifier.wordify("0783835665");
for (WordSequence sequence : sequences) {
	System.out.println(sequence.format());
}
```

Licence
-------
GNU General Public License version 3

Webapp
------

This repository also contains a simple webapp for wordifying numbers. To run it you need an environment variable with
the following JSON:

```json
{
	"lang" : "en",
	"stripNumberPrefix" : "25"
}
```

To specify which word list to use, and what prefix to strip from incoming numbers. Currently it only has an English word
list.. so yes.. that is a bit pointless
