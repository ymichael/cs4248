import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;


public class build_tagger {
	public static void main(String[] args) throws IOException {
		// Verify that the correct arguments are passed in.
		if (args.length < 3) {
			System.out.println("Expected 3 arguments <sents.train> <sents.devt> <model_file>");
			System.out.println(String.format("Got: %s", Arrays.toString(args)));
			return;
		}

		// Extract the various arguments to the program.
		String trainingSentencesFilePath = args[0];
		String developmentSentencesFilePath = args[1];
		String modelFilePath = args[2];

		// Create tagger and train it.
		String[] trainingSentences = Utils.readLines(trainingSentencesFilePath);
		String[] developmentSentences = Utils.readLines(developmentSentencesFilePath);
		PosTagger tagger = new PosTagger(trainingSentences, developmentSentences);

		// Train on the given sentences.
		tagger.train();

		// Serialize the tagger and save it in the modelFilePath.
		FileOutputStream fileOut = new FileOutputStream(modelFilePath);
		ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		objectOut.writeObject(tagger);
		objectOut.close();
		fileOut.close();
	}
}
