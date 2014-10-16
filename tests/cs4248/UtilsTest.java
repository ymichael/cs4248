package cs4248;
import static org.junit.Assert.*;

import org.junit.Test;

import cs4248.TaggedToken;
import cs4248.Utils;


public class UtilsTest {

	@Test
	public void testStripTags() {
		assertEquals("This is a thing", Utils.stripTags("This/AA is/BB a/CC thing/BB"));
		assertEquals("This/that is a thing", Utils.stripTags("This/that/AA is/BB a/CC thing/BB"));
	}

	@Test
	public void testGetTaggedSentence() {
		TaggedToken[] taggedTokens = new TaggedToken[]{
			new TaggedToken("this", "AA"),
			new TaggedToken("is", "BB"),
			new TaggedToken("a", "CC"),
			new TaggedToken("thing", "DD")
		};

		assertEquals("this/AA is/BB a/CC thing/DD", Utils.getTaggedSentence(taggedTokens));
	}

	@Test
	public void testParseSentence() {
		TaggedToken[] expected = new TaggedToken[]{
				new TaggedToken("this", "AA"),
				new TaggedToken("is", "BB"),
				new TaggedToken("a", "CC"),
				new TaggedToken("thing", "DD")
			};

		assertArrayEquals(expected, Utils.parseSentence("this/AA is/BB a/CC thing/DD"));
	}

	@Test
	public void testRoundTrip() {
		assertEquals(
			"this/AA is/BB a/CC thing/DD",
			Utils.getTaggedSentence(Utils.parseSentence("this/AA is/BB a/CC thing/DD")));
	}
}
