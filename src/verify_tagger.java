
import java.io.IOException;
import java.util.ArrayList;
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
		
		// Perform 10-Fold verification.
		int folds = 10;
		double[] precisions = new double[folds];
		String[] trainingSentences = Utils.readLines(trainingSentencesFilePath);
		int testSize = trainingSentences.length / folds;
		for (int i = 0; i < folds; i++) {
			ArrayList<String> trainingSet = new ArrayList<String>();
			ArrayList<String> testSet = new ArrayList<String>();
			int minTest = i * testSize;
			int maxTest = minTest + testSize;
			for (int j = 0; j < trainingSentences.length; j++) {
				if (minTest <= j && j < maxTest) {
					testSet.add(trainingSentences[j]);
				} else {
					trainingSet.add(trainingSentences[j]);
				}
			}
			System.out.println(String.format("Fold %s, [%s:%s]", i, minTest, maxTest));
			System.out.println(
				String.format("Training: %s, Test: %s", trainingSet.size(), testSet.size()));
			PosTagger tagger = new PosTagger(
				trainingSet.toArray(new String[trainingSet.size()]),
				testSet.toArray(new String[testSet.size()]));
			tagger.train();
			precisions[i] = tagger.verify();
		}
		System.out.println(Arrays.toString(precisions));
	}
}
