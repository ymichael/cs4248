import java.io.Serializable;

/**
 * Pos Tagger class.
 * @author michael
 *
 */
public class PosTagger implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient String[] trainingSentences;
    private transient String[] developmentSentences;
    private transient IModel model;

    private Language language;


    public PosTagger(String[] trainingSentences, String[] developmentSentences) {
        this.trainingSentences = trainingSentences;
        this.developmentSentences = developmentSentences;
        this.language = new Language();
        this.model = new WittenBellModel(this.language);
    }
    
    public IModel getModel() {
    	if (this.model == null) {
    		this.model = new WittenBellModel(this.language);
    	}
    	return this.model;
    }

    /**
     * Trains tagger using training and development sentences.
     */
    public void train() {
        for (int i = 0; i < this.trainingSentences.length; i++) {
            this.language.addTrainingSentence(this.trainingSentences[i]);
        }
    }
    
    public String tagSentence(String sentence) {
    	Viterbi v = new Viterbi(getModel(), this.language.getAllTags(), sentence);
    	return v.getTaggedSentence();
    }

    private void tune() {
    	int totalTagged = 0;
    	int correct = 0;

    	for (int i = 0; i < Math.max(this.developmentSentences.length, 100); i++) {
    		TaggedToken[] expectedTaggedTokens = Utils.parseSentence(this.developmentSentences[i]);
            String untaggedSentence = Utils.stripTags(this.developmentSentences[i]);
            int t = 0;
        	int c = 0;
            // Tag using language model.
            String taggedSentence =
            	new Viterbi(this.model, this.language.getAllTags(), untaggedSentence).getTaggedSentence();
            TaggedToken[] actualTaggedTokens = Utils.parseSentence(taggedSentence);

            for (int j = 0; j < actualTaggedTokens.length; j++) {
            	String expectedPosTag = expectedTaggedTokens[j].getPosTag();
            	String actualPosTag = actualTaggedTokens[j].getPosTag();
            	totalTagged += 1;
            	t += 1;
            	if (expectedPosTag.equals(actualPosTag)) {
            		correct += 1;
            		c += 1;
            	}
            }
            if ((double) c/t < .9) {
            	System.out.println(this.developmentSentences[i]);
            	System.out.println(taggedSentence);
            	System.out.println((double) c/t);
            }
            System.out.println((double) correct/totalTagged);
        }

    	System.out.println((double) correct/totalTagged);
    }
}
