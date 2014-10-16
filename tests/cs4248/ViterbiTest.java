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
	}

	@Test
	public void testViterbiAlgorithm1() {
		viterbi = new Viterbi(model, new String[]{"N", "V"}, "water plants");
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

		assertEquals((double) 3/5000, viterbi.getMaxProbability(), 0.000001 /* delta */);
		assertEquals("water/V plants/N", viterbi.getTaggedSentence());
	}
	
	@Test
	public void testViterbiAlgorithm2() {
		viterbi = new Viterbi(model, new String[]{"A", "B", "C", "D"}, "he is watering plants");
		stubTagTag("A", "A", (double) 1/5);
		stubTagTag("A", "B", (double) 2/5);
		stubTagTag("A", "C", (double) 4/5);
		stubTagTag("A", "D", (double) 2/5);

		stubTagTag("B", "A", (double) 1/5);
		stubTagTag("B", "B", (double) 2/5);
		stubTagTag("B", "C", (double) 4/5);
		stubTagTag("B", "D", (double) 2/5);

		stubTagTag("C", "A", (double) 1/5);
		stubTagTag("C", "B", (double) 1/5);
		stubTagTag("C", "C", (double) 1/5);
		stubTagTag("C", "D", (double) 1/5);

		stubTagTag("D", "A", (double) 1/5);
		stubTagTag("D", "B", (double) 1/5);
		stubTagTag("D", "C", (double) 1/5);
		stubTagTag("D", "D", (double) 1/5);

		stubStartTag("A", (double) 2/5);
		stubStartTag("B", (double) 3/5);
		stubStartTag("C", (double) 3/5);
		stubStartTag("D", (double) 3/5);

		stubEndTag("A", (double) 1/10);
		stubEndTag("B", (double) 2/10);
		stubEndTag("C", (double) 2/10);
		stubEndTag("D", (double) 1/10);

		stubWordTag("he", "A", (double) 1/20);
		stubWordTag("he", "B", (double) 1/20);
		stubWordTag("he", "C", (double) 1/20);
		stubWordTag("he", "D", (double) 1/20);

		stubWordTag("is", "A", (double) 1/20);
		stubWordTag("is", "B", (double) 1/20);
		stubWordTag("is", "C", (double) 1/20);
		stubWordTag("is", "D", (double) 1/20);

		stubWordTag("watering", "A", (double) 1/20);
		stubWordTag("watering", "B", (double) 1/20);
		stubWordTag("watering", "C", (double) 1/20);
		stubWordTag("watering", "D", (double) 1/20);

		stubWordTag("plants", "A", (double) 1/20);
		stubWordTag("plants", "B", (double) 1/20);
		stubWordTag("plants", "C", (double) 1/20);
		stubWordTag("plants", "D", (double) 1/20);


		assertEquals((double) 16/2000000000, viterbi.getMaxProbability(), 0.000001 /* delta */);
		assertEquals("he/B is/A watering/A plants/B", viterbi.getTaggedSentence());
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
