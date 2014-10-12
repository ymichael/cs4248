import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BasicLanguageModelTest {
	ILanguageModel model;
	
	@Before
	public void setUp() {
		model = new BasicLanguageModel();
	}

	@Test
	public void testAddTrainingSentence() {
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
	}
	
	@Test
	public void testGetAllTags() {
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
		assertArrayEquals(new String[]{"AA",  "BB", "CC"}, model.getAllTags());
		model.addTrainingSentence("This/DD is/AA a/BB word/CC");
		assertArrayEquals(new String[]{"AA",  "BB", "CC", "DD"}, model.getAllTags());
	}
	
	@Test
	public void testGetProbabilityOfTagGivenStart() {
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");
		
		assertEquals((double) 1/3, model.getProbablityOfTagGivenStart("AA"), 0.01);
		assertEquals((double) 2/3, model.getProbablityOfTagGivenStart("CC"), 0.01);
	}
	
	
	@Test
	public void testGetProbabilityOfNextTagGiveTag() {
		/**
		 * AA -> AA (1)
		 * AA -> BB (3)
		 * BB -> CC (3)
		 * CC -> AA (2)
		 */
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");		
		
		assertEquals((double) 1/4, model.getProbabilityOfNextTagGivenTag("AA", "AA"), 0.01);
		assertEquals((double) 3/4, model.getProbabilityOfNextTagGivenTag("BB", "AA"), 0.01);
		assertEquals((double) 1, model.getProbabilityOfNextTagGivenTag("CC", "BB"), 0.01);
		assertEquals((double) 2/5, model.getProbabilityOfNextTagGivenTag("AA", "CC"), 0.01);
	}
	
	@Test
	public void testGetProbabilityOfEndGivenTag() {
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/BB");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");
		
		assertEquals((double) 2/4, model.getProbabilityOfEndGivenTag("CC"), 0.01);
		assertEquals((double) 1/4, model.getProbabilityOfEndGivenTag("BB"), 0.01);
	}

	@Test
	public void testGetProbabilityOfWordGivenTag() {
		model.addTrainingSentence("This/AA is/AA a/BB word/CC");
		model.addTrainingSentence("This/CC is/AA a/BB word/BB");
		model.addTrainingSentence("This/CC is/AA a/BB word/CC");
		
		assertEquals((double) 1/4, model.getProbabilityOfWordGivenTag("This", "AA"), 0.01);
		assertEquals((double) 3/4, model.getProbabilityOfWordGivenTag("a", "BB"), 0.01);
	}

}
