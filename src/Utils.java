import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	static String[] readLines(String filePath) throws IOException {
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
	 * Writes contents to the given file path.
	 * @param filePath
	 * @param contents
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	static void writeToFile(String filePath, String contents)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filePath, "UTF-8");
		writer.print(contents);
		writer.close();
	}

	/**
	 * Parses a POS tagged sentence and returns an array of TaggedTokens in order.
	 * @param taggedSentence
	 * @return
	 */
    static TaggedToken[] parseSentence(String taggedSentence) {
        String[] tokens = taggedSentence.split(" ");
        TaggedToken[] taggedTokens = new TaggedToken[tokens.length];
        for (int j = 0; j < tokens.length; j++) {
            taggedTokens[j] = new TaggedToken(tokens[j]);
        }
        return taggedTokens;
    }

    static String getTaggedSentence(TaggedToken[] taggedTokens) {
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
    static String stripTags(String taggedSentence) {
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
