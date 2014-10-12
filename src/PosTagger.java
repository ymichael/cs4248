/**
 * Pos Tagger class.
 * @author michael
 *
 */
public class PosTagger {
    private String[] trainingSentences;
    private String[] developmentSentences;
    private ILanguageModel model;


    public PosTagger(String[] trainingSentences, String[] developmentSentences) {
        this.trainingSentences = trainingSentences;
        this.developmentSentences = developmentSentences;
        this.model = new SmoothedLanguageModel();
    }

    /**
     * Trains tagger using training and development sentences.
     */
    public void train() {
        for (int i = 0; i < this.trainingSentences.length; i++) {
            this.model.addTrainingSentence(this.trainingSentences[i]);
        }

        System.out.println("Done Training.");
        this.tune();
     
//        String s = "The/DT decision/NN to/TO make/VB the/DT bid/NN for/IN Nekoosa/NNP ,/, for/IN example/NN ,/, was/VBD made/VBN only/RB after/IN all/DT six/CD members/NNS of/IN Georgia-Pacific/NNP 's/POS management/NN committee/NN signed/VBD onto/IN the/DT deal/NN --/: even/RB though/IN Mr./NNP Hahn/NNP knew/VBD he/PRP wanted/VBD to/TO go/VB after/IN the/DT company/NN early/RB on/IN ,/, says/VBZ Mr./NNP Correll/NNP ./.";
//        
//        TaggedToken[] expectedTaggedTokens = Utils.parseSentence(s);
//        String untaggedSentence = Utils.stripTags(s);
//        
//        // Tag using language model.
//        String taggedSentence = new Viterbi(this.model, untaggedSentence).getTaggedSentence();
//        TaggedToken[] actualTaggedTokens = Utils.parseSentence(taggedSentence);
//        System.out.println(s);
//        System.out.println(taggedSentence);
        
    }

    private void tune() {
    	int totalTagged = 0;
    	int correct = 0;
    	
    	for (int i = 0; i < this.developmentSentences.length; i++) {
    		TaggedToken[] expectedTaggedTokens = Utils.parseSentence(this.developmentSentences[i]);
            String untaggedSentence = Utils.stripTags(this.developmentSentences[i]);
            
            int t = 0;
        	int c = 0;

            // Tag using language model.
            String taggedSentence = new Viterbi(this.model, untaggedSentence).getTaggedSentence();
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
            
            if ((double) c/t < .5) {
            	System.out.println(this.developmentSentences[i]);
            	System.out.println(taggedSentence);
            	System.out.println((double) c/t);            	
            }
        }

    	System.out.println((double) correct/totalTagged);
    }

    public String serialize() {
        return "";
    }
}
