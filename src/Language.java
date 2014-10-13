import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

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

	public Language() {
        this.tagToNextTags = new HashMap<String, HashMap<String, Integer>>();
        this.tagToWords = new HashMap<String, HashMap<String, Integer>>();
        this.tagToCount = new HashMap<String, Integer>();
        this.wordToCount = new HashMap<String, Integer>();
	}

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
            	this.incrementTagCount(Utils.START_OF_SENTENCE);
            	this.addTagTagPair(Utils.START_OF_SENTENCE, currentTaggedToken.getPosTag());
            }
            if (j == taggedTokens.length - 1) {
            	this.addTagTagPair(currentTaggedToken.getPosTag(), Utils.END_OF_SENTENCE);
            }
        }
	}

	public String[] getAllTags() {
		Set<String> tagSet = this.tagToCount.keySet();
		tagSet.remove(Utils.START_OF_SENTENCE);
		return tagSet.toArray(new String[tagSet.size()]);
	}

	public int getTagCount(String t) {
		return this.tagToCount.containsKey(t) ? this.tagToCount.get(t) : 0;
	}

	
	public int getTagTagTypesStartingWithTag(String t) {
		HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(t);
		return nextTagToCount.size();
	}
	
	public int getTagWordTypesStartingWithTag(String t) {
		HashMap<String, Integer> wordToCount = this.tagToWords.get(t);
		return wordToCount.size();
	}

	public int getWordCount(String w) {
		w = this.normalizeWord(w);
		return this.wordToCount.containsKey(w) ? this.wordToCount.get(w) : 0;
	}

	public int getTagTypeCount() {
		return this.tagToCount.size();
	}

	public int getWordTypeCount() {
		return this.wordToCount.size();
	}
	
	private void incrementTagCount(String tag) {
		this.tagToCount.put(tag, this.getTagCount(tag) + 1);
    }

	private void incrementWordCount(String w) {
		w = this.normalizeWord(w);
		this.wordToCount.put(w, this.getWordCount(w) + 1);
    }

	private void addTagTagPair(String t1, String t2) {
    	HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(t1);
        if (nextTagToCount == null) {
        	nextTagToCount = new HashMap<String, Integer>();
            this.tagToNextTags.put(t1, nextTagToCount);
        }
        int existingCount = nextTagToCount.containsKey(t2) ? nextTagToCount.get(t2) : 0;
        nextTagToCount.put(t2, existingCount + 1);
    }

	private void addWordTagPair(String w, String t) {
    	HashMap<String, Integer> wordToCount = this.tagToWords.get(t);
        if (wordToCount == null) {
        	wordToCount = new HashMap<String, Integer>();
            this.tagToWords.put(t, wordToCount);
        }
        w = this.normalizeWord(w);
        int existingCount = wordToCount.containsKey(w) ? wordToCount.get(w) : 0;
        wordToCount.put(w, existingCount + 1);
    }

	public int getTagTagCount(String t1, String t2) {
    	HashMap<String, Integer> nextTagToCount = this.tagToNextTags.get(t1);
        if (nextTagToCount == null) {
        	nextTagToCount = new HashMap<String, Integer>();
            this.tagToNextTags.put(t1, nextTagToCount);
        }
        return nextTagToCount.containsKey(t2) ? nextTagToCount.get(t2) : 0;
	}

	public int getWordTagCount(String w, String t) {
    	HashMap<String, Integer> wordToCount = this.tagToWords.get(t);
        if (wordToCount == null) {
        	wordToCount = new HashMap<String, Integer>();
            this.tagToWords.put(t, wordToCount);
        }
        w = this.normalizeWord(w);
        return wordToCount.containsKey(w) ? wordToCount.get(w) : 0;
	}

	private String normalizeWord(String w) {
        // return w.toLowerCase();
		return w;
	}
}
