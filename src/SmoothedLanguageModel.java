public class SmoothedLanguageModel extends BasicLanguageModel {
	public static final double DISCOUNT = 0.5;
	
	private int totalTagTagPairs;
	private int totalWordTagPairs;
	
	public SmoothedLanguageModel() {
		super();
		this.totalTagTagPairs = this.getTagCount() * this.getTagCount();
		this.totalWordTagPairs = this.getWordCount() * this.getTagCount();
		
	}
//	@Override
//	public double getProbablityOfTagGivenStart(String t) {
//		HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(Utils.START_OF_SENTENCE);
//		int starts = 0;
//        for (int v : nextTagToCount.values()) {
//        	starts += v;
//        }
//		return (double) this.getTagTagCount(Utils.START_OF_SENTENCE, t) / starts;
//	}
//
	@Override
	public double getProbabilityOfEndGivenTag(String t) {
		return (double) this.getProbabilityOfNextTagGivenTag(Utils.END_OF_SENTENCE, t);
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		return (double)this.getWordTagCount(w, t) + DISCOUNT /
			(this.getTagCount(t) + this.totalWordTagPairs);
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {	
		return (double)this.getTagTagCount(t1, t2) + DISCOUNT /
			(this.getTagCount(t1) + this.totalTagTagPairs);
	}
}
