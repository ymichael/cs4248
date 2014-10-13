
public class WittenBellModel extends BasicModel {
	Long allPossibleWordTagTypes;
	Long allPossibleTagTagTypes;
	
	public WittenBellModel(Language language) {
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
	public double getProbabilityOfWordGivenTag(String w, String tag) {
		long ctw = this.language.getWordTagCount(w, tag);
		long ct = this.language.getTagCount(tag);
		long t = this.language.getTagWordTypesStartingWithTag(tag);
		long v = getAllPossibleWordTagTypes();
		long z = v - t;
		
		if (ctw > 0) {
			return (double) ctw / (ct + t);
		} else {
			return (double) t / (z * (ct + t));
		}
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		long ctt = this.language.getTagTagCount(t1, t2);
		long ct = this.language.getTagCount(t1);
		long t = this.language.getTagTagTypesStartingWithTag(t1);
		long v = getAllPossibleTagTagTypes();
		long z = v - t;
		
		if (ctt > 0) {
			return (double) ctt / (ct + t);
		} else {
			return (double) t / (z * (ct + t));
		}
	}
}
