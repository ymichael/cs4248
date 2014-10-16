package cs4248;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class Utils {
	public static final String START_OF_SENTENCE = "<s>";
	public static final String END_OF_SENTENCE = "</s>";

	/**
	 * Reads a text file and returns a array of strings.
	 * Each element in the resulting array corresponds to a line in the given file.
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String[] readLines(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		Collection<String> lines = new ArrayList<String>();
	    try {
	        String line = br.readLine();
	        while (line != null) {
	        	lines.add(line);
	            line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	    return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Parses a POS tagged sentence and returns an array of TaggedTokens in order.
	 * @param taggedSentence
	 * @return
	 */
	public static TaggedToken[] parseSentence(String taggedSentence) {
        String[] tokens = taggedSentence.split(" ");
        TaggedToken[] taggedTokens = new TaggedToken[tokens.length];
        for (int j = 0; j < tokens.length; j++) {
            taggedTokens[j] = new TaggedToken(tokens[j]);
        }
        return taggedTokens;
    }

    public static String getTaggedSentence(TaggedToken[] taggedTokens) {
    	StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < taggedTokens.length; j++) {
        	if (j != 0) {
        		stringBuilder.append(" ");
        	}
        	stringBuilder.append(taggedTokens[j].toString());
        }
        return stringBuilder.toString();
    }

    /**
     * Takes in a POS tagged sentence and returns a sentence that is not tagged.
     * @param taggedSentence
     * @return
     */
    public static String stripTags(String taggedSentence) {
        TaggedToken[] taggedTokens = parseSentence(taggedSentence);
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < taggedTokens.length; j++) {
        	if (j != 0) {
        		stringBuilder.append(" ");
        	}
            stringBuilder.append(taggedTokens[j].getToken());
        }
        return stringBuilder.toString();
    }
}
