public class WittenBellModel extends BasicModel {
	public WittenBellModel(Language language) {
		super(language);
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String tag) {
		long c = this.language.getWordTagCount(w, tag);
		long t = this.language.getTagWordTypesStartingWithTag(tag);
		long v = this.language.getTagCount() * this.language.getWordCount();
		long z = v - t;
		return c > 0 ? (double) c / (c + t): (double) t / (z * (c + t));
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		long c = this.language.getTagTagCount(t1, t2);
		long t = this.language.getTagTagTypesStartingWithTag(t1);
		long v = (int) Math.pow(this.language.getTagCount(), 2);
		long z = v - t;
		return c > 0 ? (double) c / (c + t): (double) t / (z * (c + t));
	}
}
