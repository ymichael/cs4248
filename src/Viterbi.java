import java.util.HashMap;


public class Viterbi {
	private ILanguageModel model;
	private String[] allTags;
	private String[] tokens;
	private HashMap<Integer, HashMap<String, String>> indexAndCurrentTagToBestNextTag;
	private HashMap<Integer, HashMap<String, Double>> indexAndCurrentTagToMaxProbability;
	
	private Double maxProbability;
	private String bestFirstTag;

	public Viterbi(ILanguageModel model, String sentenceToTag) {
		this.model = model;
		this.tokens = sentenceToTag.split(" ");
		this.allTags = model.getAllTags();
	}
	
	public double getMaxProbability() {
		if (this.maxProbability == null) {
			this.run();
		}
		return this.maxProbability;
	}
	
	public String getTaggedSentence() {
		if (this.bestFirstTag == null) {
			this.run();
		}
		
		String[] bestTags = new String[this.tokens.length];
		bestTags[0] = this.bestFirstTag;
		for (int i = 1; i < this.tokens.length; i++) {
			bestTags[i] = this.indexAndCurrentTagToBestNextTag.get(i - 1).get(bestTags[i - 1]);
		}
		TaggedToken[] taggedTokens = new TaggedToken[this.tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			taggedTokens[i] = new TaggedToken(this.tokens[i], bestTags[i]);
		}
		return Utils.getSentence(taggedTokens);
	}
	
	/**
	 * Run the viterbi algorithm.
	 */
	private void run() {
		this.indexAndCurrentTagToBestNextTag = new HashMap<Integer, HashMap<String,String>>();
		this.indexAndCurrentTagToMaxProbability = new HashMap<Integer, HashMap<String,Double>>();
		for (int i = 0; i < tokens.length; i++) {
			this.indexAndCurrentTagToBestNextTag.put(i, new HashMap<String, String>());
			this.indexAndCurrentTagToMaxProbability.put(i, new HashMap<String, Double>());
		}
	
		double max = Double.NEGATIVE_INFINITY;
		for (String tag : this.allTags) {
			double p = this.model.getProbablityOfTagGivenStart(tag) * this.maxProbablityFrom(0, tag);
			if (p > max) {
				max = p;
				maxProbability = max;
				bestFirstTag = tag;
			}
		}
	}
	
	private double maxProbablityFrom(int index, String currentTag) {
		if (!this.indexAndCurrentTagToMaxProbability.get(index).containsKey(currentTag)) {			
			String currentWord = this.tokens[index];
			double max = Double.NEGATIVE_INFINITY;
			for (String nextTag : this.allTags) {
				double p = this.model.getProbabilityOfWordGivenTag(currentWord, currentTag);
				if (index == this.tokens.length - 1) {
					p *= this.model.getProbabilityOfEndGivenTag(currentTag);
				} else {
					p *= this.model.getProbabilityOfNextTagGivenTag(nextTag, currentTag);
					p *= this.maxProbablityFrom(index + 1, nextTag);
				}
				if (p > max) {
					max = p;
					this.indexAndCurrentTagToBestNextTag.get(index).put(currentTag, nextTag);
					this.indexAndCurrentTagToMaxProbability.get(index).put(currentTag, p);
				}
			}
		}
		return this.indexAndCurrentTagToMaxProbability.get(index).get(currentTag);
	}
//	
//	private double tagHelper(int index, String currentTag, String[] tokens) {
//		Double memo = this.indexAndCurrentTagToMaxProbability.get(index).get(currentTag);
//		if (memo != null) {
//			return memo;
//		}
//
//		String currentWord = tokens[index];
//		double max = Double.NEGATIVE_INFINITY;
//		String maxTag = "";
//		for (String nextTag : this.allTags) {
//			double p = this.model.getProbabilityOfWordGivenTag(currentWord, currentTag);
//			if (index == tokens.length - 1) {
//				p *= this.model.getProbabilityOfEndGivenTag(currentTag);
//			} else {
//				p *= this.model.getProbabilityOfNextTagGivenTag(nextTag, currentTag);
//				p *= this.tagHelper(index + 1, nextTag, tokens);
//			}
//			if (p > max) {
//				max = p;
//				maxTag = nextTag;
//			}
//		}
//		this.indexAndCurrentTagToBestNextTag.get(index).put(currentTag, maxTag);
//		this.indexAndCurrentTagToMaxProbability.get(index).put(currentTag, max);
//		return max;
//	}

}
