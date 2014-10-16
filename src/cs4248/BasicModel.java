package cs4248;

public class BasicModel implements IModel {
	protected Language language;

	public BasicModel(Language language) {
		this.language = language;
	}

	@Override
	public double getProbablityOfTagGivenStart(String t) {
		return this.getProbabilityOfNextTagGivenTag(t, Utils.START_OF_SENTENCE);
	}

	@Override
	public double getProbabilityOfEndGivenTag(String t) {
		return (double) this.getProbabilityOfNextTagGivenTag(Utils.END_OF_SENTENCE, t);
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		return (double) this.language.getWordTagCount(w, t) / this.language.getTagCount(t);
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		return (double) this.language.getTagTagCount(t1, t2) / this.language.getTagCount(t1);
	}
}
