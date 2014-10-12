
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;


public class run_tagger {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Verify that the correct arguments are passed in.
		if (args.length < 3) {
			System.out.println("Expected 3 arguments <sents.test> <model_file> <sents.out>");
			System.out.println(String.format("Got: %s", Arrays.toString(args)));
			return;
		}

		// Extract the various arguments to the program.
		String testSentencesFilePath = args[0];
		String modelFilePath = args[1];
		String sentencesOutFilePath = args[2];

		// Read tagger from the model file.
		FileInputStream fileIn = new FileInputStream(modelFilePath);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		PosTagger tagger = (PosTagger) objIn.readObject();
		objIn.close();
		fileIn.close();
		
		// Read test sentences.
		String[] testSentences = Utils.readLines(testSentencesFilePath);

		// Write tagged sentences to out file.
		File file = new File(sentencesOutFilePath);
		FileOutputStream fileOut = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOut));
		for (int i = 0; i < testSentences.length; i++) {
			bw.write(tagger.tagSentence(testSentences[i]));
			bw.newLine();
		}
		bw.close();
		fileOut.close();
	}
}
