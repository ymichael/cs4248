package cs4248;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WittenBellModel extends BasicModel {
	Long allPossibleWordTagTypes;
	Long allPossibleTagTagTypes;
	
	// Cache probablities for tag/tag since there are a at most tagtypes*tagtypes of them.
	double[][] memoTagTag;
	ArrayList<String> allTags;
	HashMap<String, Integer> tagToIndex;

	public WittenBellModel(Language language) {
		super(language);
		
		// Stuff to make witten-bell fast.
		this.allTags = new ArrayList<String>();
		this.allTags.addAll(Arrays.asList(language.getAllTags()));
		this.allTags.add(Utils.START_OF_SENTENCE);
		this.allTags.add(Utils.END_OF_SENTENCE);
		this.tagToIndex = new HashMap<String, Integer>();
		for (int i = 0; i < allTags.size(); i++) {
			this.tagToIndex.put(allTags.get(i), i);
		}
		this.memoTagTag = new double[this.allTags.size()][this.allTags.size()];
		for (int i = 0; i < memoTagTag.length; i++) {
			Arrays.fill(this.memoTagTag[i], 0);
		}
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
		double memo = this.memoTagTag[this.tagToIndex.get(t2)][this.tagToIndex.get(t1)];
		if (memo != 0) {
			return memo;
		}
		
		long ctt = this.language.getTagTagCount(t1, t2);
		long ct = this.language.getTagCount(t1);
		long t = this.language.getTagTagTypesStartingWithTag(t1);
		long v = getAllPossibleTagTagTypes();
		long z = v - t;

		double retval = ctt > 0 ? (double) ctt / (ct + t) : (double) t / (z * (ct + t));
		this.memoTagTag[this.tagToIndex.get(t2)][this.tagToIndex.get(t1)] = retval;
		return retval;
	}
}
