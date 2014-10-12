public class LaplaceModel extends BasicModel {
	public static final double DISCOUNT = 0.5;

	private int totalTagTagPairs;
	private int totalWordTagPairs;

	public LaplaceModel(Language language) {
		super(language);
		this.totalTagTagPairs = this.language.getTagCount() * this.language.getTagCount();
		this.totalWordTagPairs = this.language.getWordCount() * this.language.getTagCount();
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		return (double) this.language.getWordTagCount(w, t) + DISCOUNT /
			(this.language.getTagCount(t) + this.totalWordTagPairs);
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		return (double) this.language.getTagTagCount(t1, t2) + DISCOUNT /
			(this.language.getTagCount(t1) + this.totalTagTagPairs);
	}
}
