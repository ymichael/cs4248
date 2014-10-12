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
        this.model = new BasicLanguageModel();
    }

    /**
     * Trains tagger using training and development sentences.
     */
    public void train() {
        for (int i = 0; i < this.trainingSentences.length; i++) {
            this.model.addTrainingSentence(this.trainingSentences[i]);
        }

        System.out.println("Done Training.");
        Viterbi v = new Viterbi(this.model, "The training wage covers only workers who are 16 to 19 years old .");
        System.out.println(v.getTaggedSentence());
    }

    public String serialize() {
        return "";
    }
}
