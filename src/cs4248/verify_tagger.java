package cs4248;

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
		String[] trainingSentences = Utils.readLines(trainingSentencesFilePath);
		String[] developmentSentences = Utils.readLines(developmentSentencesFilePath);
		String[] trainingData = new String[trainingSentences.length + developmentSentences.length];
		for (int i = 0; i < trainingData.length; i++) {
			if (i < trainingSentences.length) {
				trainingData[i] = trainingSentences[i];
			} else {
				trainingData[i] = developmentSentences[i - trainingSentences.length];
			}
		}
		
		// Perform 10-Fold verification.
		int folds = 10;
		double[] precisions = new double[folds];
		for (int i = 0; i < folds; i++) {
			ArrayList<String> trainingSet = new ArrayList<String>();
			ArrayList<String> testSet = new ArrayList<String>();
			for (int j = 0; j < trainingData.length; j++) {
				if (j % folds == i) {
					testSet.add(trainingData[j]);
				} else {
					trainingSet.add(trainingData[j]);
				}
			}
			System.out.println(String.format("Fold %s", i));
			System.out.println(
				String.format("Training: %s, Test: %s", trainingSet.size(), testSet.size()));
			PosTagger tagger = new PosTagger(
				trainingSet.toArray(new String[trainingSet.size()]),
				testSet.toArray(new String[testSet.size()]));
			tagger.train();
			precisions[i] = tagger.verify();
		}
		System.out.println(Arrays.toString(precisions));
		
		// Average precision.
		double sum = 0;
		for (int i = 0; i < precisions.length; i++) {
			sum += precisions[i];
		}
		System.out.println(String.format("Average precision: %s", sum/folds));
	}
}
