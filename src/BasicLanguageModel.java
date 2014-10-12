import java.util.HashMap;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class BasicLanguageModel implements ILanguageModel {
	/**
     * A mapping of tags to counts of tags that come after it.
     * Eg.
     *      Tag Sequence: AA BB AA CC
     *      AA -> {BB -> 1, CC -> 1}
     *      BB -> {AA -> 1}
     */
    private HashMap<String, HashMap<String, Integer>> tagToNextTags;

    /**
     * A mapping of tags to counts of words that are tagged with it.
     * Eg.
     *      Sentence: This/AA is/BB a/AA good/CC good/CC day/BB
     *      AA -> {This -> 1,  a -> 1}
     *      BB -> {is -> 1, day -> 1}
     *      cc -> {good -> 2}
     */
    private HashMap<String, HashMap<String, Integer>> tagToWords;

    /**
     * A mapping of the number of times a tag appears.
     */
    private HashMap<String, Integer> tagToCount;
    
    /**
     * A mapping of the number of times a word appears.
     */
    private HashMap<String, Integer> wordToCount;

	public BasicLanguageModel() {
        this.tagToNextTags = new HashMap<String, HashMap<String, Integer>>();
        this.tagToWords = new HashMap<String, HashMap<String, Integer>>();
        this.tagToCount = new HashMap<String, Integer>();
        this.wordToCount = new HashMap<String, Integer>();
	}

	@Override
	public void addTrainingSentence(String sentence) {
		TaggedToken[] taggedTokens = Utils.parseSentence(sentence);
        for (int j = 0; j < taggedTokens.length; j++) {
            TaggedToken currentTaggedToken = taggedTokens[j];
            this.incrementTagCount(currentTaggedToken.getPosTag());
            this.incrementWordCount(currentTaggedToken.getToken());
            this.addWordTagPair(currentTaggedToken.getToken(), currentTaggedToken.getPosTag());

            if (j != taggedTokens.length - 1) {
                TaggedToken nextTaggedToken = taggedTokens[j + 1];
                this.addTagTagPair(currentTaggedToken.getPosTag(), nextTaggedToken.getPosTag());
            }

            // For <s> and </s> markers, we treat them as tags when counting.
            // This allows us to use P(TT) when counting probabilities later on.
            if (j == 0) {
            	this.addTagTagPair(Utils.START_OF_SENTENCE, currentTaggedToken.getPosTag());
            }
            if (j == taggedTokens.length - 1) {
            	this.addTagTagPair(currentTaggedToken.getPosTag(), Utils.END_OF_SENTENCE);
            }
        }
	}

	@Override
	public String[] getAllTags() {
		Set<String> tagSet = this.tagToCount.keySet();
		return tagSet.toArray(new String[tagSet.size()]);
	}

	@Override
	public double getProbablityOfTagGivenStart(String t) {
		HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(Utils.START_OF_SENTENCE);
		int starts = 0;
        for (int v : nextTagToCount.values()) {
        	starts += v;
        }
		return (double) this.getTagTagCount(Utils.START_OF_SENTENCE, t) / starts;
	}

	@Override
	public double getProbabilityOfEndGivenTag(String t) {
		return (double) this.getProbabilityOfNextTagGivenTag(Utils.END_OF_SENTENCE, t);
	}

	@Override
	public double getProbabilityOfWordGivenTag(String w, String t) {
		w = this.normalizeWord(w);
		return (double)this.getWordTagCount(w, t) / this.getTagCount(t);
	}

	@Override
	public double getProbabilityOfNextTagGivenTag(String t2, String t1) {
		return (double)this.getTagTagCount(t1, t2) / this.getTagCount(t1);
	}

	protected void incrementTagCount(String tag) {
		this.tagToCount.put(tag, this.getTagCount(tag) + 1);
    }
	
	protected void incrementWordCount(String w) {
		w = this.normalizeWord(w);
		this.wordToCount.put(w, this.getWordCount(w) + 1);
    }

	protected void addTagTagPair(String t1, String t2) {
    	HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(t1);
        if (nextTagToCount == null) {
        	nextTagToCount = new HashMap<String, Integer>();
            this.tagToNextTags.put(t1, nextTagToCount);
        }
        int existingCount = nextTagToCount.containsKey(t2) ? nextTagToCount.get(t2) : 0;
        nextTagToCount.put(t2, existingCount + 1);
    }

	protected void addWordTagPair(String w, String t) {
    	HashMap<String, Integer> wordToCount = this.tagToWords.get(t);
        if (wordToCount == null) {
        	wordToCount = new HashMap<String, Integer>();
            this.tagToWords.put(t, wordToCount);
        }
        w = this.normalizeWord(w);
        int existingCount = wordToCount.containsKey(w) ? wordToCount.get(w) : 0;
        wordToCount.put(w, existingCount + 1);
    }

	protected int getTagTagCount(String t1, String t2) {
    	HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(t1);
        if (nextTagToCount == null) {
        	nextTagToCount = new HashMap<String, Integer>();
            this.tagToNextTags.put(t1, nextTagToCount);
        }
        return nextTagToCount.containsKey(t2) ? nextTagToCount.get(t2) : 0;
	}

	protected int getWordTagCount(String w, String t) {
    	HashMap<String, Integer> wordToCount = this.tagToWords.get(t);
        if (wordToCount == null) {
        	wordToCount = new HashMap<String, Integer>();
            this.tagToWords.put(t, wordToCount);
        }
        w = this.normalizeWord(w);
        return wordToCount.containsKey(w) ? wordToCount.get(w) : 0;
	}
	
	protected String normalizeWord(String w) {
		return w.toLowerCase();
	}

	protected int getTagCount(String t) {
		return this.tagToCount.containsKey(t) ? this.tagToCount.get(t) : 0;
	}
	
	protected int getWordCount(String w) {
		w = this.normalizeWord(w);
		return this.wordToCount.containsKey(w) ? this.wordToCount.get(w) : 0;
	}
	
	protected int getTagCount() {
		return this.tagToCount.size();
	}
	
	protected int getWordCount() {
		return this.wordToCount.size();
	}
}
