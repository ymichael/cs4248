package cs4248;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import cs4248.IModel;
import cs4248.Viterbi;


public class ViterbiTest {
	IModel model;
	Viterbi viterbi;

	@Before
	public void setUp() {
		model = mock(IModel.class);
		viterbi = new Viterbi(model, new String[]{"N", "V"}, "water plants");
	}

	@Test
	public void testViterbiAlgorithm() {
		stubTagTag("N", "N", (double) 1/5);
		stubTagTag("N", "V", (double) 1/2);
		stubTagTag("V", "N", (double) 3/5);
		stubTagTag("V", "V", (double) 1/4);

		stubStartTag("N", (double) 2/5);
		stubStartTag("V", (double) 3/5);

		stubEndTag("N", (double) 1/5);
		stubEndTag("V", (double) 1/4);

		stubWordTag("water", "N", (double) 1/20);
		stubWordTag("water", "V", (double) 1/10);
		stubWordTag("plants", "N", (double) 1/10);
		stubWordTag("plants", "V", (double) 1/20);

		assertEquals(Math.log((double) 3/5000), viterbi.getMaxProbability(), 0.000001 /* delta */);
		assertEquals("water/V plants/N", viterbi.getTaggedSentence());
	}

	private void stubTagTag(String t2, String t1, double p) {
		when(model.getProbabilityOfNextTagGivenTag(t2, t1)).thenReturn(p);
	}

	private void stubStartTag(String t, double p) {
		when(model.getProbablityOfTagGivenStart(t)).thenReturn(p);
	}

	private void stubEndTag(String t, double p) {
		when(model.getProbabilityOfEndGivenTag(t)).thenReturn(p);
	}

	private void stubWordTag(String w, String t, double p) {
		when(model.getProbabilityOfWordGivenTag(w, t)).thenReturn(p);
	}

}
