public class LaplaceModel extends BasicModel {
	Long allPossibleWordTagTypes;
	Long allPossibleTagTagTypes;
	public static final double DISCOUNT = 0.5;

	public LaplaceModel(Language language) {
		super(language);
	}
	
	private Long getAllPossibleTagTagTypes() {
		if (allPossibleTagTagTypes == null) {
			allPossibleTagTagTypes = (long) Math.pow(this.language.getTagTypeCount(), 2);
		}
		return allPossibleTagTagTypes;
	}
	
	private Long getAllPossibleWordTagTypes() {
		if (allPossibleWordTagTypes == null) {
			allPossibleWordTagTypes = (long) this.language.getTagTypeCount() * this.language.getWordTypeCount();
		}
		return allPossibleWordTagTypes;
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		return (double) this.language.getWordTagCount(w, t) + DISCOUNT /
			(this.language.getTagCount(t) + getAllPossibleWordTagTypes());
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		return (double) this.language.getTagTagCount(t1, t2) + DISCOUNT /
			(this.language.getTagCount(t1) + getAllPossibleTagTagTypes());
	}
}
