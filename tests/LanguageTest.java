import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class LanguageTest {
	Language language;

	@Before
	public void setUp() {
		language = new Language();
	}

	@Test
	public void testAddTrainingSentence() {
		language.addTrainingSentence("This/AA is/AA a/BB word/CC");
	}

	@Test
	public void testGetAllTags() {
		language.addTrainingSentence("This/AA is/AA a/BB word/CC");
		assertArrayEquals(new String[]{"AA",  "BB", "CC"}, language.getAllTags());
		language.addTrainingSentence("This/DD is/AA a/BB word/CC");
		assertArrayEquals(new String[]{"AA",  "BB", "CC", "DD"}, language.getAllTags());
	}
}
