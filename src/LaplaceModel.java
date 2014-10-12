public class LaplaceModel extends BasicModel {
	public static final double DISCOUNT = 0.5;

	public LaplaceModel(Language language) {
		super(language);
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		return (double) this.language.getWordTagCount(w, t) + DISCOUNT /
			(this.language.getTagCount(t) + (this.language.getTagCount() * this.language.getWordCount()));
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		return (double) this.language.getTagTagCount(t1, t2) + DISCOUNT /
			(this.language.getTagCount(t1) + Math.pow(this.language.getTagCount(), 2));
	}
}
