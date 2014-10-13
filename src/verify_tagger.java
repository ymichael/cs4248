import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;


public class verify_tagger {
	public static void main(String[] args) throws IOException {
		// Verify that the correct arguments are passed in.
		if (args.length < 2) {
			System.out.println("Expected 2 arguments <sents.train> <sents.devt>");
			System.out.println(String.format("Got: %s", Arrays.toString(args)));
			return;
		}

		// Extract the various arguments to the program.
		String trainingSentencesFilePath = args[0];
		String developmentSentencesFilePath = args[1];

		// Create tagger and train it.
		String[] trainingSentences = Utils.readLines(trainingSentencesFilePath);
		String[] developmentSentences = Utils.readLines(developmentSentencesFilePath);
		PosTagger tagger = new PosTagger(trainingSentences, developmentSentences);

		// Train on the given sentences.
		tagger.train();
		tagger.verify();
	}
}
