package cs4248;


public class Viterbi {
	private IModel model;
	private String[] allTags;
	private String[] tokens;
	
	private double[][] memo;
	private int[][] memoTags ;
	
	private Double maxProbability;
	private String taggedSentence;

	public Viterbi(IModel model, String[] allTags, String sentenceToTag) {
		this.model = model;
		this.tokens = sentenceToTag.split(" ");
		this.allTags = allTags;
		this.memo = new double[this.tokens.length][this.allTags.length];
		this.memoTags = new int[this.tokens.length][this.allTags.length];
	}

	public double getMaxProbability() {
		if (this.maxProbability == null) {
			this.runIter();
		}
		return this.maxProbability;
	}

	public String getTaggedSentence() {
		if (this.taggedSentence == null) {
			this.runIter();
		}
		return this.taggedSentence;
	}

	/**
	 * Run the viterbi algorithm. (Iterative version).
	 */
	private void runIter() {
		for (int i = 0; i < tokens.length; i++) {
			for (int j = 0; j < allTags.length; j++) {
				memo[i][j] = Math.log(this.model.getProbabilityOfWordGivenTag(tokens[i], allTags[j]));
				if (i == 0) {
					memo[i][j] += Math.log(this.model.getProbablityOfTagGivenStart(allTags[j]));
				}
				if (i != 0) {
					double max = Double.NEGATIVE_INFINITY;
					for (int k = 0; k < allTags.length; k++) {
						double prob = memo[i - 1][k] +
							Math.log(this.model.getProbabilityOfNextTagGivenTag(allTags[j], allTags[k]));
						if (max < prob) {
							max = prob;
							memoTags[i][j] = k;
						}
					}
					memo[i][j] += max;
				}
				if (i == tokens.length - 1) {
					memo[i][j] += Math.log(this.model.getProbabilityOfEndGivenTag(allTags[j]));
				}
			}
		}

		int finalTagIndex = 0;
		double max = Double.NEGATIVE_INFINITY;
		for (int k = 0; k < allTags.length; k++) {
			if (max < memo[tokens.length - 1][k]) {
				max = memo[tokens.length - 1][k];
				finalTagIndex = k;
			}		
		}
		this.maxProbability = memo[tokens.length - 1][finalTagIndex];

		// Get tagged sentence.
		String[] bestTags = new String[this.tokens.length];
		int tokenIndex = tokens.length - 1;
		int tagIndex = finalTagIndex;
		while (tokenIndex >= 0) {
			bestTags[tokenIndex] = allTags[tagIndex];
			tagIndex = memoTags[tokenIndex][tagIndex];
			tokenIndex--;
		}
		TaggedToken[] taggedTokens = new TaggedToken[this.tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			taggedTokens[i] = new TaggedToken(this.tokens[i], bestTags[i]);
		}
		this.taggedSentence = Utils.getTaggedSentence(taggedTokens);
	}
}
